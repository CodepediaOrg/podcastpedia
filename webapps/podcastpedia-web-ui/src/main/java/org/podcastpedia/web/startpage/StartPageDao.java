package org.podcastpedia.web.startpage;

import java.util.List;
import java.util.Map;

import org.podcastpedia.common.domain.Podcast;

public interface StartPageDao {
	
	  /**
	   * Returns the newest podcasts (ORDER BY last_updated DESC)
	   * 
	   * @param numberOfPodcasts (number of podcasts to be returned)
	   * @return
	   */
	  public List<Podcast> getLastUpdatedPodcasts(Integer numberOfPodcasts);

	  /**
	   * Returns the newest podcasts (ORDER BY insertion_date DESC)
	   * 
	   * @param numberOfPodcasts (number of podcasts to be returned and language code)
	   * @return
	   */
	  public List<Podcast> getLastUpdatedPodcastsWithLanguage(Map<String, Object> params);	
	  
	  /**
	   * Returns recommended podcasts (ORDER BY rating DESC)
	   * 
	   * 
	   * @param numberOfPodcasts (number of podcasts to be returned)
	   * @return
	   */
	  public List<Podcast> getRecommendedPodcasts(Integer numberOfPodcasts);
	  
	  /**
	   * Returns top rated podcasts (ORDER BY rating DESC)
	   * 
	   * 
	   * @param numberOfPodcasts (number of podcasts to be returned)
	   * @return
	   */
	  public List<Podcast> getTopRatedPodcasts(Integer numberOfPodcasts);	  
	  
	  /**
	   * Returns top rated podcasts (ORDER BY rating DESC) - 10 such podcasts
	   * 
	   * 
	   * @param languageCode (code of the language of the shown podcasts) and numberOfResults
	   * @return
	   */
	  public List<Podcast> getTopRatedPodcastsWithLanguage(Map<String, Object> params);		

	  /**
	   * Returns 'numberOfPodcasts'-random podcasts from the database.
	   * 
	   * @param numberOfPodcasts
	   * @return
	   */
	  public List<Podcast> getRandomPodcasts(Integer numberOfPodcasts);	
	  
	  /**
	   * Returns the new entries from the podcast directory (sort by insertion date DESC)
	   * 
	   * @param numberOfPodcasts
	   * @return
	   */
	  public List<Podcast> getNewEntries(Integer numberOfPodcasts);
}
