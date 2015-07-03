package org.podcastpedia.core.userinteraction.emailsubscription;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class EmailSubscriptionServiceImpl implements EmailSubscriptionService {
	
	@Autowired
	EmailSubscriptionDao emailSubscriptionDao;

	public void addNewEmailSubscription(String email, Integer podcastId) {
		
		EmailSubscriber emailSubscriptionsForEmail = emailSubscriptionDao.selectEmailSubscriberByEmail(email);
		
		if(emailSubscriptionsForEmail != null){
			if(emailSubscriptionsForEmail.getSubscribedPodcastIds().contains(podcastId)){
				//we do nothing because the user already subscribed to this podcast
			} else {
				addPodcastEmailSubscription(email, podcastId);				
			}
		} else {
			emailSubscriptionDao.addEmailSubscriberToUsers(email);
			addPodcastEmailSubscription(email, podcastId);
		}
		
	}

	private void addPodcastEmailSubscription(String email, Integer podcastId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		params.put("podcastId", podcastId);
		
		emailSubscriptionDao.addPodcastEmailSubscription(params);
	}

	public void setEmailSubscriptionDao(EmailSubscriptionDao emailSubscriptionDao) {
		this.emailSubscriptionDao = emailSubscriptionDao;
	}
		
}
