package org.podcastpedia.web.user;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


@Controller
@RequestMapping("users/subscriptions")
public class UserController {

	protected static Logger LOG = Logger.getLogger(UserController.class);

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
    public String getPodcastSubscriptions(ModelMap model) {

        LOG.debug("------ Returns the podcasts the user has subscribed to ------");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Podcast> subscriptions = userService.getSubscriptions(userDetails.getUsername());
        model.addAttribute("subscriptions", subscriptions);

        return "podcast_subscriptions_def";
    }

    @RequestMapping(value="latest-episodes", method= RequestMethod.GET)
    public String getLatestEpisodesFromPodcastSubscriptions(ModelMap model) {

        LOG.debug("------ Returns the podcasts the user has subscribed to ------");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Episode> latestEpisodes = userService.getLatestEpisodesFromSubscriptions(userDetails.getUsername());
        model.addAttribute("latestEpisodes", latestEpisodes);

        return "latest_episodes_for_podcast_subscriptions_def";
    }

    @RequestMapping(method= RequestMethod.POST)
    @RolesAllowed("ROLE_USER")
    public @ResponseBody String subscribeToPodcast(@RequestParam("podcastId") Integer podcastId,
                                                   @RequestParam("newSubscriptionCategory")String newSubscriptionCategory,
                                                   @RequestParam("existingSubscriptionCategory")String existingSubscriptionCategory) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String subscriptionCategory = !"".equals(newSubscriptionCategory) ? newSubscriptionCategory : existingSubscriptionCategory;
        userService.subscribeToPodcast(userDetails.getUsername(), podcastId, subscriptionCategory);

        return "OK";
    }

    @RequestMapping(value="unsubscribe", method= RequestMethod.POST)
    @RolesAllowed("ROLE_USER")
    public @ResponseBody String unsubscribeFromPodcast(@RequestParam("podcastId") Integer podcastId) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.unsubscribeFromPodcast(userDetails.getUsername(), podcastId);

        return "OK";
    }

    @RequestMapping(value="remove-from-subscription-category", method= RequestMethod.POST)
    @RolesAllowed("ROLE_USER")
    public @ResponseBody String removeFromSubscriptionCategory(@RequestParam("podcastId") Integer podcastId, @RequestParam("subscriptionCategory") String subscriptionCategory) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.removeFromSubscriptionCategory(userDetails.getUsername(), podcastId, subscriptionCategory);

        return "OK";
    }
}
