package org.podcastpedia.core.userinteraction.emailsubscription;

import java.util.List;

public class EmailSubscriber {
	
	/** holds the email of the subscriber */
	private String email;	
	
	/** holds ids of the podcasts the user subscribed to by email */
	private List<Integer> subscribedPodcastIds;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Integer> getSubscribedPodcastIds() {
		return subscribedPodcastIds;
	}

	public void setSubscribedPodcastIds(List<Integer> subscribedPodcastIds) {
		this.subscribedPodcastIds = subscribedPodcastIds;
	}
		
}
