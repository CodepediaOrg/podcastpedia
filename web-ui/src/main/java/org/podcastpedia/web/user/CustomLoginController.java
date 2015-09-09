package org.podcastpedia.web.user;

import org.apache.log4j.Logger;
import org.podcastpedia.core.searching.SearchData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/login")
public class CustomLoginController {

	protected static Logger LOG = Logger.getLogger(CustomLoginController.class);

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


    //Spring Security see this :
    @RequestMapping(value = "custom_login", method = RequestMethod.GET)
    public String login(HttpServletRequest request,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "confirmed-email", required = false) boolean isConfirmedEmail) {

        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }

        if (isConfirmedEmail) {
            model.addObject("isConfirmedEmail", isConfirmedEmail);
        }

        return "login_def";
    }
}
