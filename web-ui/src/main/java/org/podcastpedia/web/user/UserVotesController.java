package org.podcastpedia.web.user;

import org.apache.log4j.Logger;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;


@Controller
@RequestMapping("users/votes")
public class UserVotesController {

    public static final int VOTE_UP = 1;
    public static final int VOTE_DOWN = -1;
    protected static Logger LOG = Logger.getLogger(UserVotesController.class);

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

    @RequestMapping(value="podcasts/vote-up", method= RequestMethod.POST)
    @RolesAllowed("ROLE_USER")
    public @ResponseBody String voteUpPodcast(@RequestParam("podcastId") Integer podcastId) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.votePodcast(userDetails.getUsername(), podcastId, VOTE_UP);

        return "OK";
    }

    @RequestMapping(value="podcasts/vote-down", method= RequestMethod.POST)
    @RolesAllowed("ROLE_USER")
    public @ResponseBody String voteDownPodcast(@RequestParam("podcastId") Integer podcastId) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.votePodcast(userDetails.getUsername(), podcastId, VOTE_DOWN);

        return "OK";
    }
}
