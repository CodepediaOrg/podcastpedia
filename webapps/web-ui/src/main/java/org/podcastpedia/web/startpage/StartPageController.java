package org.podcastpedia.web.startpage;

import org.apache.log4j.Logger;
import org.podcastpedia.common.controllers.propertyeditors.MediaTypeEditor;
import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.OrderByOption;
import org.podcastpedia.core.categories.CategoryService;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.startpage.StartPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class StartPageController {

	private static final Integer DEFAULT_NUMBER_OF_RESULTS_PER_PAGE = 10;
	public static Integer NUMBER_OF_PODCASTS_IN_CHART = 5;
	public static Integer NUMBER_OF_CATEGORIES_IN_CHART = 8;

	protected static Logger LOG = Logger.getLogger(StartPageController.class);

	@Autowired
	private StartPageService startPageService;
	
	@Autowired
	private CategoryService categoryService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		binder.registerCustomEditor(MediaType.class, new MediaTypeEditor(
				MediaType.class));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));/* Converts empty strings into null when a form is submitted */
	}

	/**
	 * Add an empty searchData object to the model - this is needed just for the
	 * search bar. TBD - Search a way to optimize this ...
	 */
	@ModelAttribute
	public void addDataToModel(ModelMap model) {

		SearchData advancedSearchData = new SearchData();
		advancedSearchData.setCurrentPage(1);
		// advancedSearchData.setSearchTarget("podcasts");
		advancedSearchData
				.setNumberResultsPerPage(DEFAULT_NUMBER_OF_RESULTS_PER_PAGE);
		advancedSearchData.setSearchMode("natural");
		advancedSearchData.setCurrentPage(1);
		advancedSearchData.setQueryText(null);

		// advancedSearchData is used also by the searchBar, but in the
		// controller
		// search parameters are set for this kind of search
		model.put("advancedSearchData", advancedSearchData);

		model.put("randomPodcasts",
				startPageService.getRandomPodcasts(NUMBER_OF_PODCASTS_IN_CHART));

		model.addAttribute("mediaTypes", MediaType.values());
		model.addAttribute("languageCodes", LanguageCode.values());
		model.addAttribute("orderByOptions", OrderByOption.values());

		// set data in permanent cache
		List<Category> categoriesByNoOfPodcasts = categoryService
				.getCategoriesOrderedByNoOfPodcasts();

		model.put("topCategories", categoriesByNoOfPodcasts.subList(0,
				NUMBER_OF_CATEGORIES_IN_CHART));
		model.put("allCategories", categoriesByNoOfPodcasts);

		List<Podcast> recommendedPodcasts = startPageService
				.getRecommendedPodcasts();
		model.put("recommendedPodcasts", recommendedPodcasts);

		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();
		List<String> preferredLanguagesList = Arrays.asList(preferredLanguages);
		model.put("newEntries", startPageService.getNewEntries());
		if (preferredLanguagesList.contains(language)) {
			model.put("lastUpdatedPodcasts", startPageService
					.getLastUpdatedPodcasts(LanguageCode.get(language)));
//			model.put("topRatedPodcasts", startPageService
//					.getTopRatedPodcastsWithLanguage(
//							LanguageCode.get(language),
//							NUMBER_OF_PODCASTS_IN_CHART));
		} else {
			model.put("lastUpdatedPodcasts", startPageService.getLastUpdatedPodcasts());
//			model.put("topRatedPodcasts", startPageService
//					.getTopRatedPodcasts(NUMBER_OF_PODCASTS_IN_CHART));
		}

	}

	/**
	 * For the standard path a page with all categories will be displayed
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String displayCategories() {
		return "m_startPage_def";
	}

	private static String[] preferredLanguages = { "en", "fr", "de" };
	
}
