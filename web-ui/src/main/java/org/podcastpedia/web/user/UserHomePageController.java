package org.podcastpedia.web.user;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created by ama on 06/12/15.
 */
@Controller
@RequestMapping("users/homepage")
public class UserHomePageController {

    protected static Logger LOG = Logger.getLogger(UserSubscriptionCategoryController.class);

    @Autowired
    UserService userService;
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

    @RequestMapping(method= RequestMethod.GET)
    @RolesAllowed("ROLE_USER")
    public String getUserHomepage(ModelMap model) {

        LOG.debug("------ Returns the homepage for the user ------");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Podcast> subscriptions = userService.getSubscriptions(userDetails.getUsername());
        model.addAttribute("subscriptions", subscriptions);

        List<String> subscriptionCategories = userService.getSubscriptionCategoryNames(userDetails.getUsername());
        model.addAttribute("subscriptionCategories", subscriptionCategories);

        return "user_homepage_def";
    }

}
