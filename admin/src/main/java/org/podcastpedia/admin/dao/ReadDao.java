package org.podcastpedia.admin.dao;

import java.util.List;
import java.util.Map;

import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.Tag;
import org.podcastpedia.common.types.UpdateFrequencyType;


public interface ReadDao {
	
	
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
	   * Retrieves all podcasts between startRow and endRow 
	   * 
	   * @return a list of podcasts. 
	   */
	public List<Podcast> getPodcastsFromRange(Map<String, Integer> input);
	
	  /**
	   * Retrieves all podcasts between startRow and endRow, with the given updateFrequency 
	   * 
	   * @return a list of podcasts. 
	   */
	public List<Podcast> getPodcastsFromRangeWithUpdateFrequency(Map<String, Integer> input);	
	
	/**
	 * Returns a tags type given the tags name
	 *  
	 * @param tagStr
	 * 
	 * @return
	 */
	public Tag getTagByName(String tagStr);
	
	  /**
	   * Retrieve a podcast by its id. 
	   * 
	   * @param podcastId
	   * @return a podcast. 
	   */
	  public Podcast getPodcastAndEpisodesById(int podcastId);

	  /**
	   * Retrieve a podcast by its url. 
	   * 
	   * @param url
	   * @return a podcast. 
	   */
	  public Podcast getPodcastAndEpisodesByURL(String url);	  
	  
	  /**
	   * For a given podcast (identified by podcastId) returns
	   * the maximum available episode id(from table EPISODES), to be used when inserting new episodes
	   * 
	   * @param podcastId
	   * @return
	   */
	  public Integer getMaxEpisodeIdForPodcast(Integer podcastId);
	  
	  /**
	   * Returns number of podcasts from the database 
	   * @return
	   */
	  public Integer getNumberOfPodcasts();
	  
	/**
	 * REturns number of podcasts with the given update Frequency 
	 * @param podcastsUpdateFrequency
	 * @return
	 */
	public Integer getNumberOfPodcastsWithUpdateFrequency(
			Integer podcastsUpdateFrequency);

	public Integer getPodcastIdForFeedUrl(String feedUrl);
	
	  /** TODO - ATTENTION duplicated code with function from podcastDao 
	   * Retrieve a podcast by its id. 
	   * 
	   * @param podcastId
	   * @return a podcast. 
	   */
	  public Podcast getPodcastById(int podcastId);
	  
	  /** TODO - ATTENTION duplicated code with function from podcastDao
	   * Retrieve a podcast by its url. 
	   * 
	   * @param url
	   * @return a podcast. 
	   */
	  public Podcast getPodcastByURL(String url);
	  
	/**
	 * Returns just the attributes of the podcast (no episodes, or categories, or tags)
	 * 
	 * @param feedUrl
	 * @return
	 */
	  public Podcast getPodcastAttributesByFeedUrl(String feedUrl);

	  /**
	   * Returns podcast's attributes necessary in the update process
	   *  
	   * @param podcastId
	   * @return
	   */
	  public Podcast getPodcastForUpdateById(Integer podcastId);

	  /**
	   * Returns relevant (available ) episodes for update for a podcast 
	   * @param podcastId
	   * @return
	   */
	  public List<Episode> getAvailableEpisodesFromDB(Integer podcastId);	
	  
	  /**
	   * 
	   * @param updateFrequency
	   * @return
	   */
	  public List<Podcast> getPodcastsAndEpisodeWithUpdateFrequency(UpdateFrequencyType updateFrequency);
}
