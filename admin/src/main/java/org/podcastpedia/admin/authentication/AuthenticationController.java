package org.podcastpedia.admin.authentication;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Handles and retrieves the login or denied page depending on the URI template
 */
@Controller
@RequestMapping("/users")
public class AuthenticationController {

	protected static Logger LOG = Logger.getLogger(AuthenticationController.class);

	/**
	 * Handles and retrieves the login JSP page
	 *
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value="error", required=false) boolean error,
			ModelMap model) {

		LOG.debug("------ getLoginPage :Received request to show login page -----");

		if (error == true) {
			// Assign an error message
			model.put("error", "Invalid username or password provided !!!");
		} else {
			model.put("error", "");
		}

		// This will resolve to /WEB-INF/jsp/loginpage.jsp
		return "loginpage";
	}

	/**
	 * Handles and retrieves the denied JSP page. This is shown whenever a regular user
	 * tries to access an admin only page.
	 *
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
 	public String getDeniedPage() {

		LOG.debug("------ AuthenticationController.getDeniedPage : Received request to show denied page -----");

		// This will resolve to /WEB-INF/jsp/deniedpage.jsp
		return "deniedpage";
	}

	@RequestMapping(value = "/session_expired", method = RequestMethod.GET)
 	public String getSessionExpiredPage() {

		LOG.debug("------ AuthenticationController.getSessionExpiredPage : Looks like the session expired or don't know exactly what the hell the problem is -----");

		// This will resolve to /WEB-INF/jsp/deniedpage.jsp
		return "sessionExpired";
	}
}
