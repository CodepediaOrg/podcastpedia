package org.podcastpedia.admin.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class HomePageController {

	/** This creates the view for the admin home page */
	@RequestMapping(method = RequestMethod.GET)
	public String adminHomePage(Model model) {

		return "adminHomePage_def"; // for the time being this is the home
											// page, it shouldn't be like that
	}

}
