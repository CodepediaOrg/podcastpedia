package org.podcastpedia.web.user;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.User;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/login")
public class CustomLoginController {

    @Autowired
    UserService userService;

	protected static Logger LOG = Logger.getLogger(CustomLoginController.class);

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

    //@RequestMapping(method=RequestMethod.GET)
    public String prepareUserRegistrationForm(
        @ModelAttribute("userRegistration") User user,
        Model model){
        LOG.debug("------ prepareUserRegistrationForm : Received request to show user registration form -----");

        user = new User();
        model.addAttribute("userRegistration", user);

        return "user_registration_def";
    }

    //Spring Security see this :
    @RequestMapping(value = "custom_login", method = RequestMethod.GET)
    public String login(HttpServletRequest request,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "isConfirmedEmail", required = false) boolean isConfirmedEmail,
            @RequestParam(value = "isConfirmedEmailPasswordReset", required = false) boolean isConfirmedEmailPasswordReset,
            @ModelAttribute("userRegistration") User user,
            Model model) {

        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);

        user = new User();
        model.addAttribute("userRegistration", user);

        if (error != null) {
            //hack TODO - make the verification more granular - unknown email or wrong password
           /*
            if(!userService.isExistingUser(username)){//where to get the useranme from
               model.addAttribute("emailNotRegistered", "This email is not registered");
            } else {
                //the password must be incorrect
                model.addAttribute("wrongPassword", "The password you provided is not right");
            }
            */
            model.addAttribute("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }

        if (isConfirmedEmail) {
            model.addAttribute("isConfirmedEmail", isConfirmedEmail);
        }

        if (isConfirmedEmailPasswordReset) {
            model.addAttribute("isConfirmedEmailPasswordReset", isConfirmedEmailPasswordReset);
        }

        return "login_def";
    }
}
