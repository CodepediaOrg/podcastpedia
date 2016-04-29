package org.podcastpedia.web.episodes;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.EpisodeWrapper;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.common.types.ErrorCodeType;
import org.podcastpedia.common.util.config.ConfigService;
import org.podcastpedia.core.episodes.EpisodeService;
import org.podcastpedia.core.podcasts.PodcastService;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.web.podcasts.PodcastController;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * Annotation-driven controller that handles requests to display episodes and
 * episode archive pages.
 *
 * @author ama
 */
@Controller
@RequestMapping("/podcasts")
// it will handle all requests having "podcasts" in it
public class EpisodeController {

	protected static Logger LOG = Logger.getLogger(PodcastController.class);

	@Autowired
	private PodcastService podcastService;

	@Autowired
	private EpisodeService episodeService;

	@Autowired
	private ConfigService configService;

	/**
	 * Add an empty searchData object to the model
	 */
	@ModelAttribute
	public void addDataToModel(ModelMap model) {
		SearchData dataForSearchBar = new SearchData();
		dataForSearchBar.setSearchMode("natural");
		dataForSearchBar.setCurrentPage(1);
		dataForSearchBar.setQueryText(null);
		dataForSearchBar.setNumberResultsPerPage(10);
		model.put("advancedSearchData", dataForSearchBar);
	}

	/**
	 * Controller method for episode page.
	 *
	 * @param podcastId
	 * @param episodeId
	 * @param show_other_episodes
	 * @param model
	 *
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "{podcastId}/*/episodes/{episodeId}/*", method = RequestMethod.GET)
	public String getEpisodeDetails(
			@PathVariable("podcastId") int podcastId,
			@PathVariable("episodeId") int episodeId,
			@RequestParam(value = "show_other_episodes", required = false) Boolean show_other_episodes,
			ModelMap model)
			throws BusinessException {

		LOG.debug("------ getEpisodeDetails : Received request to show details for episode id "
				+ episodeId + " of the podcast id " + podcastId + " ------");

		EpisodeWrapper episodeDetails = episodeService.getEpisodeDetails(
				podcastId, episodeId);

		model.addAttribute("podcast_title_in_url", episodeDetails.getEpisode()
				.getPodcast().getTitleInUrl());
		if (episodeDetails.getEpisode().getRating() == null)
			episodeDetails.getEpisode().setRating(10f);
		model.addAttribute("roundedRatingScore",
				Math.round(episodeDetails.getEpisode().getRating()));
		model.addAttribute("episodeDetails", episodeDetails.getEpisode());

		// set other episodes to be displayed
		List<Episode> otherEpisodes = episodeDetails.getLastEpisodes();
		if (otherEpisodes != null) {
			model.addAttribute("episodes", otherEpisodes);
			model.addAttribute("otherEpisodesSize",
					otherEpisodes.size());
			model.addAttribute("nr_divs_with_ratings", otherEpisodes.size());
		}

		if (null != show_other_episodes && show_other_episodes) {
			model.addAttribute("show_other_episodes", true);
		} else {
			model.addAttribute("show_other_episodes", false);
		}

        //set og_url
        String og_url = "https://www.podcastpedia.org/podcasts/" + episodeDetails.getEpisode().getPodcastId()
                        + "/" + episodeDetails.getEpisode().getPodcast().getTitleInUrl()
                        + "/episodes/" + episodeDetails.getEpisode().getEpisodeId()
                        + "/" + episodeDetails.getEpisode().getTitleInUrl();
        model.addAttribute("og_url", og_url);

		return "m_episodeDetails_def";
	}

	@RequestMapping(value = "{podcastId}/*/episodes/archive/pages/{currentPage}", method = RequestMethod.GET)
	public String getEpisodeArchivePage(
			@PathVariable("podcastId") Integer podcastId,
			@PathVariable("currentPage") Integer currentPage, ModelMap model,
			HttpSession session)
			throws BusinessException {

		LOG.debug("------ getEpisodeDetails : Received request to show all episodes for podcast id "
				+ podcastId + " ------");

		Integer numberOfEpisodes = podcastService
				.getNumberEpisodesForPodcast(podcastId);

		// default is 20 if you want to get rid of the cache for config data
		Integer numberOfEpisodesPerPage = Integer.valueOf(configService
				.getValue("NUMBER_OF_EPISODES_PER_PAGE_IN_ARCHIVE"));
		List<Episode> episodes = episodeService.getEpisodesFromArchive(
				podcastId, currentPage, numberOfEpisodes);
		model.addAttribute("episodes", episodes);
		model.addAttribute("nr_divs_with_ratings", episodes.size());
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("numberOfEpisodes", numberOfEpisodes);
		model.addAttribute("podcastTitleInUrl", episodes.get(0)
				.getPodcast().getTitleInUrl());
		model.addAttribute("podcastId", podcastId);

		Integer numberOfArchivePages = episodeService
				.getNumberOfArchivePages(numberOfEpisodes);
		model.addAttribute("numberOfEpisodePages", numberOfArchivePages);

		model.addAttribute("numberOfEpisodesPerPage", numberOfEpisodesPerPage);

		return "m_allEpisodesForPodcastDefinition";
	}

	@ExceptionHandler({ NoSuchRequestHandlingMethodException.class,
			ConversionNotSupportedException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleResourceNotFound(ModelMap model) {
		model.put("advancedSearchData", new SearchData());
		return "resourceNotFound";
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleBusinessException(BusinessException ex,
			HttpServletRequest request) {
		String tileDefinitionName = null;
		if (ex.getErrorCode() == ErrorCodeType.EPISODE_FOUND_IN_ARCHIVE_ERROR_CODE
				|| ex.getErrorCode() == ErrorCodeType.EPISODE_NOT_FOUND_BUT_PODCAST_AVAILABLE) {
			request.setAttribute("episode", ex.getEpisode());
			tileDefinitionName = "error_ep_found_in_archive";
		} else {
			tileDefinitionName = "resourceNotFound";
		}

		return tileDefinitionName;
	}

}
