package org.podcastpedia.admin.dao;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.PodcastCategory;
import org.podcastpedia.common.domain.PodcastTag;
import org.podcastpedia.common.domain.Tag;

public interface InsertDao {
	
	/**
	 * Inserts episode in the database 
	 * @param episode
	 */
	public void insertEpisode(Episode episode);
	
	/**
	 * Adds a new podcast to the database 
	 * 
	 * @param podcast
	 */
	public void addPodcast(Podcast podcast);	
	
	/**
	 * Adds a new data set in PODCASTS_CATEGORIES table 
	 * @param podcastCategory
	 */
	public void insertPodcastCategory(PodcastCategory podcastCategory);	
	

	/**
	 * Inserts a new tags in the Tags table
	 * @param tag
	 * @return
	 */
	public void insertTag(Tag tag);

	/**
	 * Inserts a podcast_tag in the Podcasts_tags table
	 * @param podcastId
	 * @param tagId
	 */
	public void insertPodcastTag(PodcastTag podcastTag);

}
