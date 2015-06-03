package org.podcastpedia.web.userinteraction.emailsubscription;

public interface EmailSubscriptionService {
	

	/**
	 * If subscriber already present on Podcastpedia, it will try to add the new subscription to podcast (by podcastId)
	 * If not present it will also be persisted, along with the podcastId it subscribed to 
	 * 
	 * @param email
	 * @param podcastId
	 */
	public void addNewEmailSubscription(String email, Integer podcastId);

}
