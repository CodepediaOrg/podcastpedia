package org.podcastpedia.web.staticpages;

import org.apache.log4j.Logger;
import org.podcastpedia.core.searching.SearchData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller to route the various static pages in the application. 
 * 
 * @author amasia
 *
 */
@Controller
public class StaticPagesController {
	
	protected static Logger LOG = Logger.getLogger(StaticPagesController.class);
	
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
	}	
	
	@RequestMapping(value="/mission", method=RequestMethod.GET)
	public String getMissionPage(Model model){					
		return "mission_def";
	}	
	
	@RequestMapping(value="/podcasting", method=RequestMethod.GET)
	public String getAboutPodcastingPage(){
		LOG.info("getAboutPodcastingPage was called");
		
		return "about_podcasting_tiles_def";
	}	
	
	@RequestMapping(value="/privacy", method=RequestMethod.GET)
	public String getPrivacyPolicyPage(Model model){					
		return "privacy_def";
	}
	
	@RequestMapping(value="/disclaimer", method=RequestMethod.GET)
	public String getDisclaimerPage(Model model){					
		return "disclaimer_def";
	}	

	@RequestMapping(value="/how_can_i_help", method = RequestMethod.GET)
	public String getHowCanIHelpPage() {
		LOG.info("getHowCanIHelpPage was called");

		return "howcanihelp_def";
	}	
}

