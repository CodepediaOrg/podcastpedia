package org.podcastpedia.web.suggestpodcast;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.podcastpedia.core.categories.CategoryService;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.suggestpodcast.EmailNotificationService;
import org.podcastpedia.core.suggestpodcast.SuggestedPodcast;
import org.podcastpedia.core.userinteraction.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.ServletRequest;
import java.util.List;

@Controller
@RequestMapping("/how_can_i_help/add_podcast")
@SessionAttributes("addPodcastForm")
public class SuggestPodcastController {

	protected static Logger LOG = Logger.getLogger(SuggestPodcastController.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserInteractionService userInteractionService;

	@Autowired
	private SuggestPodcastValidator suggestPodcastValidator;

	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	ReCaptchaImpl reCaptcha;

	/**
	 * Add an empty searchData object to the model
	 */
	@ModelAttribute
	public void addDataToModel(ModelMap model) {
		SearchData dataForSearchBar = new SearchData();
		dataForSearchBar.setSearchMode("natural");
		dataForSearchBar.setCurrentPage(1);
		dataForSearchBar.setQueryText(null);
		dataForSearchBar.setNumberResultsPerPage(10);
		model.put("advancedSearchData", dataForSearchBar);

		// it it gets to complicated move it to own controller - i mean
		// suggesting a podcast
		List<Category> allCategories = categoryService
				.getCategoriesOrderedByNoOfPodcasts();

		model.addAttribute("allCategories", allCategories);
		model.addAttribute("mediaTypes", MediaType.values());
		model.addAttribute("languageCodes", LanguageCode.values());
		model.addAttribute("updateFrequencies", UpdateFrequencyType.values());
	}

	@RequestMapping(method = RequestMethod.GET)
	public String prepareAddPodcastForm(
			@RequestParam(value = "tks", required = false) String isTks,
			Model model) {
		LOG.debug("------ prepareAddPodcastForm : Received request to show add podcast form -----");
		SuggestedPodcast addPodcastForm = new SuggestedPodcast();
		model.addAttribute("addPodcastForm", addPodcastForm);
		if (isTks != null) {
			model.addAttribute("thank_you_message", "thank_you");
		}
		return "add_podcast_form_def";
	}

	/**
	 * 
	 * @param addPodcastFormData
	 * @param result
	 * @param model
	 * @param servletRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String processAddPodcastForm(
			@ModelAttribute("addPodcastForm") SuggestedPodcast addPodcastFormData,
			BindingResult result, Model model,
			@RequestParam("recaptcha_challenge_field") String challangeField,
			@RequestParam("recaptcha_response_field") String responseField,
			ServletRequest servletRequest, SessionStatus sessionStatus) {

		LOG.debug("------ processAddPodcastForm : form is being validated and processed -----");
		suggestPodcastValidator.validate(addPodcastFormData, result);

		String remoteAddress = servletRequest.getRemoteAddr();
		ReCaptchaResponse reCaptchaResponse = this.reCaptcha.checkAnswer(
				remoteAddress, challangeField, responseField);

		if (reCaptchaResponse.isValid() && !result.hasErrors()) {

			userInteractionService.addSuggestedPodcast(addPodcastFormData);
			emailNotificationService.sendSuggestPodcastNotification(addPodcastFormData);
			sessionStatus.setComplete();

			return "redirect:/how_can_i_help/add_podcast?tks=true";
		} else {
			model.addAttribute("addPodcastForm", addPodcastFormData);
			if (!reCaptchaResponse.isValid()) {
				result.rejectValue("invalidRecaptcha", "invalid.captcha");
				model.addAttribute("invalidRecaptcha", true);
			}
			return "add_podcast_form_def";
		}

	}

	@ExceptionHandler(HttpSessionRequiredException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "The session has expired")
	public String handleSessionExpired() {
		return "sessionExpired";
	}

}