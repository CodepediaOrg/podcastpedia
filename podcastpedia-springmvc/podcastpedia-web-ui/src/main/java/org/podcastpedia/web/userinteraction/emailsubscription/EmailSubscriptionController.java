package org.podcastpedia.web.userinteraction.emailsubscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/email-subscription")
public class EmailSubscriptionController {

	@Autowired
	EmailSubscriptionService emailSubscriptionService;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String subscribeToPodcast(
			@RequestParam Integer podcastId,
			@RequestParam String email
			){
		
		emailSubscriptionService.addNewEmailSubscription(email, podcastId);
		String response = "Email subscription was successful";
		
		return response; 
	}
			
}
