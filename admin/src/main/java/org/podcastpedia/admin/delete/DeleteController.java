package org.podcastpedia.admin.delete;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.util.forms.PodcastByFeedUrlForm;
import org.podcastpedia.admin.util.forms.PodcastByIdForm;
import org.podcastpedia.admin.util.forms.UserByEmailForm;
import org.podcastpedia.admin.util.read.ReadService;
import org.podcastpedia.admin.util.restclient.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/delete")
public class DeleteController {
	protected static Logger LOG = Logger.getLogger(DeleteController.class);

	@Autowired
	private DeleteService deleteService;

	@Autowired
	private ReadService readService;

	@Autowired
	private RestClient restClient;

	/** This creates the view for the admin home page */
	@RequestMapping(method = RequestMethod.GET)
	public String prepareDeletionForms(Model model) {
		model.addAttribute("markPodcastByIdForm", new PodcastByIdForm());
		model.addAttribute("markPodcastByUrlForm",
				new PodcastByFeedUrlForm());

		model.addAttribute("deletePodcastByIdForm", new PodcastByIdForm());
		model.addAttribute("deletePodcastByFeedUrlForm",
				new PodcastByFeedUrlForm());

        model.addAttribute("deleteUserByEmailForm", new UserByEmailForm());

		return "delete_podcasts_def";
	}

	@RequestMapping(value = "mark_podcast_by_id", method = RequestMethod.POST)
	public String markPodcastById(
			@ModelAttribute("deletePodcastByIdForm") PodcastByIdForm deletePodcastByIdForm,
			BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/delete";
		}

		LOG.debug("------ deletePodcastById : deletes podcast and its episodes from DB by podcast's id -----");
		deleteService.markPodcastAsUnavailable(deletePodcastByIdForm.getPodcastId());

		return "redirect:/admin/delete";
	}

	@RequestMapping(value = "mark_podcast_by_feedUrl", method = RequestMethod.POST)
	public String markPodcastByFeedUrl(
			@ModelAttribute("deletePodcastByFeedUrlForm") PodcastByFeedUrlForm deletePodcastByFeedUrlForm,
			BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/delete";
		}

		LOG.debug("------ deletePodcastById : deletes podcast and its episodes from DB by podcast's id -----");
	    Integer podcastIdForFeedUrl = readService.getPodcastIdForFeedUrl(deletePodcastByFeedUrlForm
				.getFeedUrl().trim());
	    deleteService.markPodcastAsUnavailable(podcastIdForFeedUrl);

		return "redirect:/admin/delete";
	}

	@RequestMapping(value = "podcast_by_id", method = RequestMethod.POST)
	public String deletePodcastById(
			@ModelAttribute("deletePodcastByIdForm") PodcastByIdForm deletePodcastByIdForm,
			BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/delete";
		}

		LOG.debug("------ deletePodcastById : deletes podcast and its episodes from DB by podcast's id -----");
		deleteService.deletePodcastById(deletePodcastByIdForm.getPodcastId());

		restClient.invokeRefreshNewestAndRecommendedPodcasts();
		restClient.invokeRefreshReferenceData();
		restClient.invokeFlushSearchResultsCache();

		return "redirect:/admin/delete";
	}

	@RequestMapping(value = "podcast_by_feedUrl", method = RequestMethod.POST)
	public String deletePodcastByFeedUrl(
			@ModelAttribute("deletePodcastByFeedUrlForm") PodcastByFeedUrlForm deletePodcastByFeedUrlForm,
			BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/delete";
		}

		LOG.debug("------ deletePodcastById : deletes podcast and its episodes from DB by podcast's id -----");
	    Integer podcastIdForFeedUrl = readService.getPodcastIdForFeedUrl(deletePodcastByFeedUrlForm
				.getFeedUrl().trim());
	    deleteService.deletePodcastById(podcastIdForFeedUrl);

		restClient.invokeRefreshNewestAndRecommendedPodcasts();
		restClient.invokeRefreshReferenceData();
		restClient.invokeFlushSearchResultsCache();

		return "redirect:/admin/delete";
	}

    @RequestMapping(value = "user_by_email", method = RequestMethod.POST)
    public String deleteUserByEmail(
        @ModelAttribute("deleteUserByEmailForm") UserByEmailForm userByEmailForm,
        BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/delete";
        }

        LOG.debug("------ deleteUserByEmail : deletes user and its subscriptions  -----");
        deleteService.deleteUser(userByEmailForm.getEmail());

        return "redirect:/admin/delete";
    }
}
