package org.podcastpedia.web.categories;

import org.apache.log4j.Logger;
import org.podcastpedia.core.categories.CategoryService;
import org.podcastpedia.core.searching.SearchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


/**
 * Annotation-driven controller that handles requests to display podcast categories in different forms.
 * 
 * @author Ama 
 *
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {
	
	protected static Logger LOG = Logger.getLogger(CategoryController.class);
 
	@Autowired
	private CategoryService categoryService;

	@ModelAttribute("allCategories")
	public void addAllCategories(ModelMap model){
		LOG.debug("------ addAllCategories : add categories to model <DB hit> -----");	
		model.put("allCategories", categoryService.getCategoriesOrderedByNoOfPodcasts());
	}

	/**
	 * Add an empty searchData object to the model - this is needed just for the search bar.
	 * TBD - Search a way to optimize this ... 
	 */
	@ModelAttribute
	public void addDataToModel(ModelMap model){
		SearchData dataForSearchBar = new SearchData();
		dataForSearchBar.setSearchMode("natural");
		dataForSearchBar.setCurrentPage(1);
		dataForSearchBar.setQueryText(null);
		dataForSearchBar.setNumberResultsPerPage(10);
		model.put("advancedSearchData", dataForSearchBar);
	}		
	
	/**
	 * For the standard path a page with all categories will be displayed
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String displayCategories(){
		return "m_allCategories_def";					
	}
	
	  /**
	   * Custom handler for displaying a podcast.
	   * 
	   * @param podcastId
	   * @param episode
	   * @return
	   */
	  @RequestMapping(value="{categoryId}/*", method=RequestMethod.GET)
	  public ModelAndView getPodcastsFromCategory(@PathVariable("categoryId") int categoryId, ModelMap model){
		  
		  LOG.debug("------ CategoryController.getPodcastsFromCategory : redirects to search podcasts from a given category -----");
		  
		  StringBuffer queryString = new StringBuffer(); 

		  queryString.append("?numberResultsPerPage=10");  
		  queryString.append("&searchTarget=podcasts");
		  queryString.append("&categId=" + categoryId);
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
