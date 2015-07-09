package org.podcastpedia.web.tags;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Tag;
import org.podcastpedia.common.util.config.ConfigService;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.tags.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
 * Annotation-driven controller that handles requests to display podcast
 * categories in different forms.
 * 
 * @author Ama
 * 
 */
@Controller
@RequestMapping("/tags")
public class TagController {

	protected static Logger LOG = Logger.getLogger(TagController.class);

	@Autowired
	private TagService tagService;

	@Autowired
	private ConfigService configService;

	/**
	 * Add an empty searchData object to the model - this is needed just for the
	 * search bar. TBD - Search a way to optimize this ...
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

	@RequestMapping(value = "/get_tag_list", method = RequestMethod.GET)
	public @ResponseBody
	List<Tag> getTagList(@RequestParam("term") String query) {
		List<Tag> tagList = tagService.getTagList(query);

		return tagList;
	}

	/**
	 * For the standard path a page with all categories will be displayed
	 */
	@RequestMapping(value = "all/{page}", method = RequestMethod.GET)
	public String getAllTags(@PathVariable("page") int page, ModelMap model) {
		Integer nrTagsPerPage = Integer.valueOf(configService
				.getValue("NR_TAGS_PER_PAGE"));// default is 300
		List<Tag> tags = tagService.getTagsOrderedByNumberOfPodcasts(page,
				nrTagsPerPage);
		model.addAttribute("tags", tags);
		model.addAttribute("page", page);

		return "m_allTags_def";

	}

	/**
	 * Custom handler for displaying a podcast.
	 * 
	 * @param podcastId
	 * @param episode
	 * @return
	 */
	@RequestMapping(value = "{tagId}/*", method = RequestMethod.GET)
	public ModelAndView getPodcastsWithTag(
			@PathVariable("tagId") Integer tagId, ModelMap model) {

		LOG.debug("------ CategoryController.getPodcastsFromCategory : redirects to search podcasts from a given category -----");

		StringBuilder queryString = new StringBuilder();

		queryString.append("?numberResultsPerPage=10");
		queryString.append("&searchTarget=podcasts");
		queryString.append("&tagId=").append(tagId);
		queryString.append("&searchMode=natural");
		queryString.append("&currentPage=1");

		String url = "/search/advanced_search/results" + queryString.toString();
		RedirectView rv = new RedirectView();
		rv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		rv.setUrl(url);

		ModelAndView mv = new ModelAndView(rv);
		return mv;
	}

}
