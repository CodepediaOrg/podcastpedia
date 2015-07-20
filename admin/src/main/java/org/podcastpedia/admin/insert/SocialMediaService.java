package org.podcastpedia.admin.insert;

import org.podcastpedia.common.domain.Podcast;

/**
 * Social media services interaction 
 */
public interface SocialMediaService {
	
	public void postOnTwitterAboutNewPodcast(Podcast podcast, String urlOnPodcastpedia);
	
	//public void postOnFacebookAboutNewPodcast(Podcast podcast, String urlOnPodcastpedia);
	
}
