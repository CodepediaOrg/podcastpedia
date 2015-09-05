package org.podcastpedia.web.user;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.User;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.user.EmailNotificationService;
import org.podcastpedia.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.ServletRequest;
import java.util.UUID;


@Controller
@RequestMapping("users/registration")
public class RegistrationController {

	protected static Logger LOG = Logger.getLogger(RegistrationController.class);

	@Autowired
    UserService userService;

    @Autowired
    private UserRegistrationFormValidator userRegistrationFormValidator;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private ReCaptchaImpl reCaptcha;

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

    @RequestMapping(method=RequestMethod.GET)
    public String prepareUserRegistrationForm(
        @ModelAttribute("user") User user,
        Model model){
        LOG.debug("------ prepareUserRegistrationForm : Received request to show user registration form -----");

        user = new User();
        model.addAttribute("user", user);

        return "user_registration_def";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String prepareUserRegistrationRequest(
        @ModelAttribute("user") User user,
        BindingResult result,
        Model model,
        @RequestParam("recaptcha_challenge_field") String challangeField,
        @RequestParam("recaptcha_response_field") String responseField,
        ServletRequest servletRequest, SessionStatus sessionStatus
    ){

        LOG.debug("------ processContactForm : form is being validated and processed -----");
        userRegistrationFormValidator.validate(user, result);

        /** TODO introduce recaptcha...

        String remoteAddress = servletRequest.getRemoteAddr();
        ReCaptchaResponse reCaptchaResponse = this.reCaptcha.checkAnswer(
            remoteAddress, challangeField, responseField);
         */
        model.addAttribute("user", user);
        if(!result.hasErrors()){
        //if(!result.hasErrors() && reCaptchaResponse.isValid()){

            user.setRegistrationToken(UUID.randomUUID().toString());
            userService.submitUserForRegistration(user);
            emailNotificationService.sendRegistrationEmailConfirmation(user);
            emailNotificationService.sendUserRegistrationNotification(user);
            sessionStatus.setComplete();
            String queryString="?email=" + user.getUsername() + "&displayName=" + user.getDisplayName();
            return "redirect:/users/registration/confirm-email" + queryString;
            //return "user_registration_sent_email_def";
        } else {
            /*
            if (!reCaptchaResponse.isValid()) {
                result.rejectValue("invalidRecaptcha", "invalid.captcha");
                model.addAttribute("invalidRecaptcha", true);
            }
            */
            return "user_registration_def";
        }


    }

    @RequestMapping(value = "confirm-email", method=RequestMethod.GET)
    public String requestEmailConfirmationPage(
        @RequestParam(value="email", required=false) String email,
        @RequestParam(value="displayName", required=false) String displayName,

        @ModelAttribute("user") User user,
        Model model){

        LOG.debug("------ display page asking the user to confirm the email -----");

        model.addAttribute("email", email);
        model.addAttribute("displayName", displayName);

        return "user_registration_sent_email_def";
    }

    @RequestMapping(value = "confirmed", method=RequestMethod.GET)
    public String emailConfirmated(
        @RequestParam(value="user", required=true) String username,
        @RequestParam(value="token", required=true) String registrationToken
        ){

        LOG.debug("------ received confirmation email -----");
        userService.enableUser(username, registrationToken);

        return "redirect:/login/custom_login?isConfirmedEmail=true";
    }
}
