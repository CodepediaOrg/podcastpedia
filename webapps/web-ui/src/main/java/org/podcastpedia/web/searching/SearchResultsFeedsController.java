package org.podcastpedia.web.searching;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.util.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class that maps to feeds service generation, for both the start
 * page feeds, and search results generated feeds.
 * 
 * @author amasia
 * 
 */
@Controller
@RequestMapping("/feeds/search")
public class SearchResultsFeedsController implements MessageSourceAware {

	private MessageSource messageSource;

	@Autowired
	private ConfigService configService;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Autowired
	private SearchService searchService;

	/**
	 * Returns list of podcasts for the search criteria to be generated as a rss
	 * feed. Request comes from results page for podcasts.
	 * 
	 * @param searchInput
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("podcasts.rss")
	public String getPodcastsFromSearchRssFeed(
			@ModelAttribute("advancedSearchData") SearchData searchInput,
			Model model) throws UnsupportedEncodingException {

		searchInput.setForFeed(true);
		List<Podcast> foundPodcasts = new ArrayList<Podcast>();

		SearchResult podcastsForSearchCriteria = searchService
				.getResultsForSearchCriteria(searchInput);
		foundPodcasts = podcastsForSearchCriteria.getPodcasts();

		model.addAttribute("list_of_podcasts", foundPodcasts);
		model.addAttribute("feed_title", messageSource.getMessage(
				"podcasts.search.feed_title", null,
				LocaleContextHolder.getLocale()));
		model.addAttribute("feed_description", messageSource.getMessage(
				"podcasts.search.feed_description", null,
				LocaleContextHolder.getLocale()));
		// set link to search results for data - get it through getPath
		model.addAttribute("feed_link",
				configService.getValue("HOST_AND_PORT_URL"));
		model.addAttribute("HOST_AND_PORT_URL",
				configService.getValue("HOST_AND_PORT_URL"));

		return "foundPodcastsPageRssFeedView";
	}

	/**
	 * Returns list of podcasts for the search criteria to be generated as a
	 * atom feed. Request comes from results page for podcasts.
	 * 
	 * @param searchInput
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("podcasts.atom")
	public String getPodcastsFromSearchAtomFeed(
			@ModelAttribute("advancedSearchData") SearchData searchInput,
			Model model) throws UnsupportedEncodingException {

		searchInput.setForFeed(true);
		List<Podcast> foundPodcasts = new ArrayList<Podcast>();

		SearchResult podcastsForSearchCriteria = searchService
				.getResultsForSearchCriteria(searchInput);
		foundPodcasts = podcastsForSearchCriteria.getPodcasts();

		model.addAttribute("list_of_podcasts", foundPodcasts);
		model.addAttribute("feed_id",
				"tags:podcastpedia.org,2013-04-30:found-podcasts");
		model.addAttribute("feed_title", messageSource.getMessage(
				"podcasts.search.feed_title", null,
				LocaleContextHolder.getLocale()));
		model.addAttribute("feed_description", messageSource.getMessage(
				"podcasts.search.feed_description", null,
				LocaleContextHolder.getLocale()));
		// set link to search results for data - get it through getPath
		model.addAttribute("feed_link",
				configService.getValue("HOST_AND_PORT_URL"));
		model.addAttribute("HOST_AND_PORT_URL",
				configService.getValue("HOST_AND_PORT_URL"));

		return "foundPodcastsPageAtomFeedView";
	}

	/**
	 * Returns list of episodes for the search criteria to be generated as a rss
	 * feed. Request comes from results page from searching episodes.
	 * 
	 * @param searchInput
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("episodes.rss")
	public String getEpisodesFromSearchRssFeed(
			@ModelAttribute("advancedSearchData") SearchData searchInput,
			Model model) throws UnsupportedEncodingException {

		searchInput.setForFeed(true);
		List<Episode> foundEpisodes = new ArrayList<Episode>();

		SearchResult episodesFromSearchCriteria = searchService
				.getResultsForSearchCriteria(searchInput);

		foundEpisodes = episodesFromSearchCriteria.getEpisodes();

		model.addAttribute("list_of_episodes", foundEpisodes);
		model.addAttribute("feed_title", messageSource.getMessage(
				"episodes.search.feed_title", null,
				LocaleContextHolder.getLocale()));
		model.addAttribute("feed_description", messageSource.getMessage(
				"episodes.search.feed_description", null,
				LocaleContextHolder.getLocale()));
		// set link to search results for data - get it through getPath
		model.addAttribute("feed_link",
				configService.getValue("HOST_AND_PORT_URL"));
		model.addAttribute("HOST_AND_PORT_URL",
				configService.getValue("HOST_AND_PORT_URL"));

		return "foundEpisodesPageRssFeedView";
	}

	/**
	 * Returns list of episodes for the search criteria to be generated as a
	 * atom feed. Request comes from results page for episodes.
	 * 
	 * @param searchInput
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("episodes.atom")
	public String getEpisodesFromSearchAtomFeed(
			@ModelAttribute("advancedSearchData") SearchData searchInput,
			Model model) throws UnsupportedEncodingException {

		searchInput.setForFeed(true);

		List<Episode> foundEpisodes = new ArrayList<Episode>();

		SearchResult episodesFromSearchCriteria = searchService
				.getResultsForSearchCriteria(searchInput);
		foundEpisodes = episodesFromSearchCriteria.getEpisodes();

		model.addAttribute("list_of_episodes", foundEpisodes);
		model.addAttribute("feed_id",
				"tags:podcastpedia.org,2013-04-30:found-episodes");
		model.addAttribute("feed_title", messageSource.getMessage(
				"episodes.search.feed_title", null,
				LocaleContextHolder.getLocale()));
		model.addAttribute("feed_description", messageSource.getMessage(
				"episodes.search.feed_description", null,
				LocaleContextHolder.getLocale()));
		// set link to search results for data - get it through getPath
		model.addAttribute("feed_link",
				configService.getValue("HOST_AND_PORT_URL"));
		model.addAttribute("HOST_AND_PORT_URL",
				configService.getValue("HOST_AND_PORT_URL"));

		return "foundEpisodesPageAtomFeedView";
	}

}
