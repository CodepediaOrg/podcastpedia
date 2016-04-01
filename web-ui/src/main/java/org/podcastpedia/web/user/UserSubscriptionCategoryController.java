package org.podcastpedia.web.user;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created by ama on 05/12/15.
 */
@Controller
@RequestMapping("users/subscription-categories")
public class UserSubscriptionCategoryController {

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
    //TODO not used yet - when migrating to angular js move to backend API
    public String getSubscriptionCategories(ModelMap model) {

        LOG.debug("------ Returns the podcasts the user has subscribed to ------");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> subscriptionCategories = userService.getSubscriptionCategoryNames(userDetails.getUsername());
        if(subscriptionCategories.isEmpty()) subscriptionCategories.add("default");
        model.addAttribute("subscriptionCategories", subscriptionCategories);

        return "user_subscription_categories_def";
    }

    @RequestMapping(value="{subscriptionCategory}", method= RequestMethod.GET)
    @RolesAllowed("ROLE_USER")
    public String getPodcastsForSubscriptionCategory(@PathVariable("subscriptionCategory") String subscriptionCategory, ModelMap model) {

        LOG.debug("------ Returns the podcasts the user has subscribed to for this subscription category ------");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> subscriptionCategories = userService.getSubscriptionCategoryNames(userDetails.getUsername());
        model.addAttribute("subscriptionCategories", subscriptionCategories);

        List<Podcast> subscriptions = userService.getPodcastsForSubscriptionCategory(userDetails.getUsername(), subscriptionCategory);
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("subscriptionCategory", subscriptionCategory);

        return "user_subscription_category_def";
    }
}
