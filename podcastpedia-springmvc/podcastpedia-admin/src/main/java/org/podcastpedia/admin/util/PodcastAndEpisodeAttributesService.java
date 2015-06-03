package org.podcastpedia.admin.util;

import java.io.IOException;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;

public interface PodcastAndEpisodeAttributesService {

	/**
	 * Given a podcast's feed url different attributes like title, description,
	 * image url etc. are set for the podcast The second parameter <b></b>
	 * specifies whether the SyndFeed property of the podcast has already been
	 * set
	 * 
	 * @param p
	 */
	public void setPodcastFeedAttributes(Podcast podcast,
			boolean feedPropertyHasBeenSet) throws IllegalArgumentException,
			FeedException, IOException;

	/**
	 * Given a podcast's feed url different attributes like title, description,
	 * image url etc. are set for the podcast The second parameter <b></b>
	 * specifies whether the SyndFeed property of the podcast has already been
	 * set
	 * 
	 * @param p
	 */
	public boolean setPodcastFeedAttributesWithWarnings(Podcast podcast)
			throws IllegalArgumentException, FeedException, IOException;

	/**
	 * Given an entry in the attributes of the episode are set
	 * 
	 * @param episode
	 * @param entry
	 */
	public void setEpisodeAttributes(Episode episode, Podcast podcast,
			SyndEntry entry);

}
