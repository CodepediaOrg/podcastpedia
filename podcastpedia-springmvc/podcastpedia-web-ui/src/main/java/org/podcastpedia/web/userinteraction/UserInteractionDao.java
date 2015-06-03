package org.podcastpedia.web.userinteraction;

import org.podcastpedia.web.contact.ContactForm;
import org.podcastpedia.web.suggestpodcast.SuggestedPodcast;


/**
 * Interface that describes the database access from action taken by the users / visitors 
 * 
 * @author amasia
 *
 */
public interface UserInteractionDao {
	
	/**
	 * adds the suggested podcast (feed url exptected) in the database 
	 * 
	 * @param podcast
	 */
	public void insertSuggestedPodcast(SuggestedPodcast podcast);

	/** 
	 * When the user sends a message besides sending an email, we will also insert the message in the database.
	 * 
	 * @param contactForm
	 */
	public void insertContactMessage(ContactForm contactForm);

}
