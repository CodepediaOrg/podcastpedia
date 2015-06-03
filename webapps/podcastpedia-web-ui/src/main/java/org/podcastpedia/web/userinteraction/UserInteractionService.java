package org.podcastpedia.web.userinteraction;

import org.podcastpedia.web.suggestpodcast.SuggestedPodcast;

/**
 * Interface to describe the interaction the user will have with the application
 * from the service layer perspective.
 * 
 * @author amasia
 * 
 */
public interface UserInteractionService {

	/**
	 * service method to add the suggested podcast from the visitor to the
	 * persistance layer
	 */
	public void addSuggestedPodcast(SuggestedPodcast addPodcastFormData);

}
