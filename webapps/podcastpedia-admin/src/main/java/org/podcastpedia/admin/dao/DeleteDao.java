package org.podcastpedia.admin.dao;

import org.podcastpedia.common.domain.util.EpisodeListToBeDeleted;


public interface DeleteDao {
	/**
	 * deletes from Database the podcast with the given podcast id
	 */
	public void deletePodcastById(int podcastId);
	
	/**
	 * deletes all episodes from the database with the given podcast id (basically the episodes that belong to
	 * the given podcast by its id)
	 * 
	 * @param podcastId
	 */
	public void deleteEpisodesByPodcastId(int podcastId);
	
	/**
	 * deletes the podcast_categories entries from the database with the given podcast id (basically the episodes that belong to
	 * the given podcast by its id)
	 * 
	 * @param podcastId
	 */
	public void deletePodcastCategoriesByPodcastId(int podcastId);	
	
	/**
	 * deletes the podcast from the database with the given feedUrl (this is as UNIQUE in the db persisted) 
	 * @param feedUrl
	 */
	public void deletePodcastByFeedUrl(String feedUrl);
	
	/**
	 * deletes the episodes from db for the podcast with the given feedUrl 
	 *  
	 * @param feedUrl
	 */
	public void deleteEpisodesByFeedUrl(String feedUrl);	
	
	/**
	 * deletes the podcast_categories entries from db for the podcast with the given feedUrl 
	 *  
	 * @param feedUrl
	 */
	public void deletePodcastCategoriesByFeedUrl(String feedUrl);	
	
	/**
	 * deletes all episodes from the database 
	 */
	public void deleteAllEpisodes();

	/**
	 * delete PodcastTags given the podcast id 
	 * @param podcastId
	 */
	public void deletePodcastTagsByPodcastId(Integer podcastId);
	
	/**
	 * delete PodcastTags given the podcast's feed url 
	 * @param feedUrl
	 */
	public void deletePodcastTagsByFeedUrl(String feedUrl);	
	
	/**
	 * deletes the list of episodes from the database 
	 * 
	 * @param episodes
	 */
	public void deleteEpisodes(EpisodeListToBeDeleted episodes);
}
