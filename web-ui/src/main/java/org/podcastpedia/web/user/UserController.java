package org.podcastpedia.web.user;

import org.apache.log4j.Logger;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication keycloakAuth = SecurityContextHolder.getContext().getAuthentication();
        OidcKeycloakAccount keycloakAccount = ((KeycloakAuthenticationToken) keycloakAuth).getAccount();

        String userId = keycloakAccount.getKeycloakSecurityContext().getIdToken().getSubject();

        List<Podcast> subscriptions = userService.getSubscriptions(userId);
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
    public @ResponseBody String subscribeToPodcast(@RequestParam("podcastId") Integer podcastId) {

        /*
        LOG.debug("------ Returns the podcasts the user has subscribed to ------");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        */

        Authentication keycloakAuth = SecurityContextHolder.getContext().getAuthentication();
        OidcKeycloakAccount keycloakAccount = ((KeycloakAuthenticationToken) keycloakAuth).getAccount();

        String userId = keycloakAccount.getKeycloakSecurityContext().getIdToken().getSubject();

        userService.subscribeToPodcast(userId, podcastId);

        return "OK";
    }

    @RequestMapping(value="unsubscribe", method= RequestMethod.POST)
    @RolesAllowed("ROLE_USER")
    public @ResponseBody String unsubscribeFromPodcast(@RequestParam("podcastId") Integer podcastId) {

        LOG.debug("------ Returns the podcasts the user has subscribed to ------");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.unsubscribeFromPodcast(userDetails.getUsername(), podcastId);

        return "OK";
    }
}
