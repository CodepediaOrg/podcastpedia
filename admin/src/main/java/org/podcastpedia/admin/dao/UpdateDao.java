package org.podcastpedia.admin.dao;

import java.util.Map;

import org.podcastpedia.admin.dao.helper.InputMarkNewEpisodesAsNew;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;


public interface UpdateDao {
	/**
	 * Updates the podcast's attributes (podcast identified in DB by podcast id)
	 * @param podcast 
	 */
	public void updatePodcastFeedAttributesById(Podcast podcast);
	
	/**
	 * Updates the podcast's attributes (podcast identified in DB by url of the feed)
	 * @param podcast
	 */
	public void updatePodcastByFeedUrl(Podcast podcast);
	
	/**
	 * Updates the flag column "is_available" by setting to true or false depending if the episode is reachable or not 
	 */
	public void updateEpisodeAvailability(Episode e);

	/**
	 * Updates the availability of the podcast - default is 200 (HttpStatus.SC_OK) but an error could have happened by update and we need
	 * to deal with that later (it can happened that the podcast url is not available anmore 404 - HttpStatus.SC_NOT_FOUND)
	 * @param podcast
	 */
	public void updatePodcastAvailability(Podcast podcast);

	/**
	 * All the episodes of the podcast are set to status podcast_in_error so that the don't pick up by search and are not shown
	 * As input we become the podcast id and the status PODCAST_IN_ERROR
	 * @param podcast
	 */
	public void updatePodcastEpisodesAvailability(Map<String, Integer> input);

	/**
	 * Updates mediatype, language, update Frequency for podcast (called own metadata)
	 * @param podcast
	 */
	public void updatePodcastOwnMetadatabyId(Podcast podcast);

	/**
	 * Updates the is_new column of all episodes of the podcast as old (not new)
	 * @param podcastId
	 */
	public void markAllEpisodesAsNotNew(Integer podcastId);

	/**
	 * Sets the flat is_new to 1 for the list of episodes 
	 * @param newEpisodes
	 */
	public void markNewEpisodesAsNew(InputMarkNewEpisodesAsNew input);

	/**
	 * Update attribute of the podcast that change (availability, etags etc.)
	 * @param podcast
	 */
	public void updateTransientDataForPodcastById(Podcast podcast);
		
}
