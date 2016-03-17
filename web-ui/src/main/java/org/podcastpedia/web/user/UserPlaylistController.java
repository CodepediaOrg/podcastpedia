package org.podcastpedia.web.user;

import org.apache.log4j.Logger;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
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
@RequestMapping("users/playlists")
public class UserPlaylistController {

    protected static Logger LOG = Logger.getLogger(UserPlaylistController.class);

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
    public String getPlaylists(ModelMap model) {

        LOG.debug("------ Returns the podcasts the user has subscribed to ------");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> playlistNames = userService.getPlaylistNames(userDetails.getUsername());
        model.addAttribute("playlists", playlistNames);

        return "user_playlists_def";
    }

    @RequestMapping(value="{playlist}", method= RequestMethod.GET)
    @RolesAllowed("ROLE_USER")
    public String getPodcastsForPlaylist(@PathVariable("playlist") String playlist, ModelMap model) {

        LOG.debug("------ Returns the podcasts the user has subscribed to for this playlist ------");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> playlistNames = userService.getPlaylistNames(userDetails.getUsername());
        model.addAttribute("playlists", playlistNames);

        List<Podcast> subscriptions = userService.getPodcastsForPlaylist(userDetails.getUsername(), playlist);
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("playlist", playlist);

        return "user_playlist_def";
    }
}
