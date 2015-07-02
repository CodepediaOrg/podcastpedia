package org.podcastpedia.admin.dao;

import java.util.List;

import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.PodcastCategory;
import org.podcastpedia.common.domain.PodcastTag;
import org.podcastpedia.common.domain.Tag;


public interface AdminToolsDao {
	
	/**
	 * Inserts episode in the database 
	 * @param episode
	 */
	public void insertEpisode(Episode episode);

	/**
	 * Updates the podcast's attributes (podcast identified in DB by podcast id)
	 * @param podcast 
	 */
	public void updatePodcastById(Podcast podcast);
	
	/**
	 * Updates the podcast's attributes (podcast identified in DB by url of the feed)
	 * @param podcast
	 */
	public void updatePodcastByFeedUrl(Podcast podcast);
	
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
	 * Returns a list with all available categories 
	 * @return
	 */
	public List<Category> getAllCategories();	
	
	  /**
	   * Retrieve all podcasts.
	   * 
	   * @return a list of podcasts. 
	   */
	public List<Podcast> getAllPodcasts();
	

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

	/**
	 * Returns a tags type given the tags name
	 *  
	 * @param tagStr
	 * 
	 * @return
	 */
	public Tag getTagByName(String tagStr);
}
