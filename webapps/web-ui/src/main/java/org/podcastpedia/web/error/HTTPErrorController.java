package org.podcastpedia.web.error;

import org.podcastpedia.core.searching.SearchData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HTTPErrorController {

	/**
	 * Add an empty searchData object to the model 
	 */
	@ModelAttribute
	public void addDataToModel(ModelMap model){
		SearchData dataForSearchBar = new SearchData();
		dataForSearchBar.setSearchMode("natural");
		dataForSearchBar.setCurrentPage(1);
		dataForSearchBar.setQueryText(null);
		dataForSearchBar.setNumberResultsPerPage(10);
		model.put("advancedSearchData", dataForSearchBar);	
//		DefaultHandlerExceptionResolver
	}	
	
    @RequestMapping(value="/errors/404.html")
    public String handle404() {
        return "resourceNotFound";
    }

}
