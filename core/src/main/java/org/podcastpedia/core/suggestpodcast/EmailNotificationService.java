package org.podcastpedia.core.suggestpodcast;

/**
 * Email notification service used in the context of podcast submitting.
 * After a podcast has been submit the Podcastpedia personnel will be informed about it.
 * 
 */
public interface EmailNotificationService {

	/**
	 * Send notification with suggested podcast, packed as one line of metadata
	 * 
	 * @param addPodcastFormData
	 */
	public void sendSuggestPodcastNotification(SuggestedPodcast addPodcastFormData);

}
