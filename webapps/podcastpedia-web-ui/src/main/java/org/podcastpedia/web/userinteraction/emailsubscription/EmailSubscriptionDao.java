package org.podcastpedia.web.userinteraction.emailsubscription;

import java.util.Map;

public interface EmailSubscriptionDao {
	
	/**
	 * The returned list will be used to check if the user already subscribed to podcasts
	 * via email, and to which podcastIds it subscribed to
	 * 
	 * @param email
	 * @return
	 */
	public EmailSubscriber selectEmailSubscriberByEmail(String email);
	
	/**
	 * Inserts an email subscribers to the Users table, after checking before via <code>selectEmailSubscriberByEmail</code>
	 * that the email subscriber is not present in the database 
	 */
	public void addEmailSubscriberToUsers(String email);
	
	/**
	 * Inserts row in the join table podcasts_email_subscribers
	 * 
	 * @param params contains the <code>email</code> and the <code>podcastId</code> it subscribed to. 
	 */
	public void addPodcastEmailSubscription(Map<String, Object> params);
}
