package org.podcastpedia.web.user;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.User;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.user.UserEmailNotificationService;
import org.podcastpedia.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("users/password-forgotten")
public class PasswordForgottenController {

	protected static Logger LOG = Logger.getLogger(PasswordForgottenController.class);

	@Autowired
    UserService userService;

    @Autowired
    private PasswordForgottenFormValidator passwordForgottenFormValidator;

    @Autowired
    private PasswordForgottenEmailSendFormValidator passwordForgottenEmailSendFormValidator;

    @Autowired
    private UserEmailNotificationService userEmailNotificationService;

    @Autowired
    private ReCaptchaImpl reCaptcha;

    @Resource(name="jdbcAuthenticationManager")
    protected AuthenticationManager authenticationManager;

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
    public String prepareEmailForPasswordForgottenForm(
        @ModelAttribute("user") User user,
        Model model){

        LOG.debug("------ prepareEmailForPasswordResetForm -----");

        return "password_forgotten_email_form_def";
    }

    @RequestMapping(value="send-email", method=RequestMethod.POST)
    public String preparePasswordForgottenEmailRequest(
        HttpServletRequest request,
        @ModelAttribute("user") User user,
        BindingResult result,
        Model model,
        @RequestParam("recaptcha_challenge_field") String challangeField,
        @RequestParam("recaptcha_response_field") String responseField,
        ServletRequest servletRequest, SessionStatus sessionStatus
    ){

        LOG.debug("------ validate if the user is registered -----");
        passwordForgottenEmailSendFormValidator.validate(user, result);

        boolean userIsNotRegistered = result.hasErrors();
        if(userIsNotRegistered){
            //do nothing
            String queryString="?email=" + user.getUsername() + "&displayName=" + user.getDisplayName();
            return "redirect:/users/password-forgotten/confirm-email" + queryString;
        } else {
            String remoteAddress = servletRequest.getRemoteAddr();
            ReCaptchaResponse  reCaptchaResponse = this.reCaptcha.checkAnswer(
                remoteAddress, challangeField, responseField);

            model.addAttribute("user", user);
            //if(!result.hasErrors()){
            if(!result.hasErrors() && reCaptchaResponse.isValid()){
                String localAddr = request.getRequestURL().toString();
                localAddr=servletRequest.getLocalAddr();
                userService.updateUserForPasswordReset(user);
                userEmailNotificationService.sendPasswortResetEmailConfirmation(localAddr, user);

                sessionStatus.setComplete();
                String queryString="?email=" + user.getUsername() + "&displayName=" + user.getDisplayName();
                return "redirect:/users/password-forgotten/confirm-email" + queryString;
                //return "user_registration_sent_email_def";
            } else {
                if (!reCaptchaResponse.isValid()) {
                    result.rejectValue("invalidRecaptcha", "invalid.captcha");
                    model.addAttribute("invalidRecaptcha", true);
                }

                return "password_forgotten_def";
            }
        }
    }

    @RequestMapping(value="password-reset", method=RequestMethod.GET)
    public String preparePasswordForgottenForm(
        @ModelAttribute("user") User user,
        Model model){

        LOG.debug("------ preparePasswordResetForm -----");

        return "password_forgotten_def";
    }


    @RequestMapping(value="password-reset", method=RequestMethod.POST)
    public String preparePasswordForgottenRequest(
        @ModelAttribute("user") User user,
        BindingResult result,
        Model model,
        HttpServletRequest request,
        SessionStatus sessionStatus
    ){

        LOG.debug("------ processContactForm : form is being validated and processed -----");
        passwordForgottenFormValidator.validate(user, result);

        model.addAttribute("user", user);

        //if(!result.hasErrors()){
        if(!result.hasErrors()){
            String unencryptedPassword= user.getPassword();
            userService.updateUserPassword(user);
            sessionStatus.setComplete();

            authenticateUserAndSetSession(user, unencryptedPassword, request);

            //TODO - redirect to users homepage after password reset
            return "redirect:/users/homepage";
        } else {
            return "password_forgotten_def";
        }

    }

    private void authenticateUserAndSetSession(User user, String unencryptedPassword, HttpServletRequest request) {
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, unencryptedPassword);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
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

        return "password_forgotten_sent_email_def";
    }

    /*
    @RequestMapping(value = "confirmed", method=RequestMethod.GET)
    public String emailConfirmated(
        @RequestParam(value="user", required=true) String username,
        @RequestParam(value="token", required=true) String passwordResetToken
    ){

        LOG.debug("------ received confirmation email -----");
        userService.enableUserAfterPasswordForgotten(username, passwordResetToken);

        return "redirect:/login/custom_login?isConfirmedEmailPasswordReset=true";
    }

    */

    @ExceptionHandler(HttpSessionRequiredException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String handleSessionExpired() {
        return "login_def";
    }
}
