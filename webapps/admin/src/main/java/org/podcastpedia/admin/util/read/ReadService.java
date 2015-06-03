package org.podcastpedia.admin.util.read;

import java.util.List;

import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.exception.BusinessException;


public interface ReadService {
	/**
	 * Returns a list with all available podcast categories 
	 * @return
	 */
	public List<Category> getAllAvailableCategories();	
	
	/**
	 * Returns podcasts from range [startRow, endRow]
	 * 
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<Podcast> getPodcastsFromRange(Integer startRow, Integer endRow);
	
	  /**
	   * Returns number of podcasts from the database 
	   * @return
	   */
	  public Integer getNumberOfPodcasts();

	/**
	 * Title says it all
	 * @param podcastsUpdateFrequencyCode
	 * @return
	 */
	public Integer getNumberOfPodcastsWithUpdateFrequency(Integer podcastsUpdateFrequencyCode);	
	
	/**
	 * Returns the id of the podcasts given its feed url 
	 * @param feedUrl
	 * @return
	 */
	public Integer getPodcastIdForFeedUrl(String feedUrl);
	
	/**
	 * Returns a podcast by its id
	 * 
	 * @param podcastId
	 * @return
	 */
	public Podcast getPodcastById(int podcastId)  throws BusinessException;
	
	/**
	 * Returns a podcast by its id
	 * 
	 * @param podcastId
	 * @return
	 */
	public Podcast getPodcastByFeedUrl(String feedUrl)  throws BusinessException;
	
	/**
	 * Returns just the attributes of the podcast (no episodes, or categories, or tags)
	 * 
	 * @param feedUrl
	 * @return
	 */
	public Podcast getPodcastAttributesByFeedUrl(String feedUrl);
	
}
