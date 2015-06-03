package org.podcastpedia.web.podcasts;

import java.util.List;

import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.exception.BusinessException;
import org.springframework.stereotype.Service;


/**
 * Service interface, mainly used to access the DAO layer. 
 * 
 * @author amasia
 *
 */
@Service("podcastService")
public interface PodcastService {
		
	/**
	 * Returns a podcast by its id
	 * 
	 * @param podcastId
	 * @return
	 */
	public Podcast getPodcastById(int podcastId)  throws BusinessException;

	/**
	* Retunrs the number of episodes available for the given podcast 
	* @param podcastId
	* @return
	*/
	public Integer getNumberEpisodesForPodcast(Integer podcastId);

	/**
	* Return attributes of the podcast given the feed url 
	* 
	* 
	* @param numberOfPodcasts (number of podcasts to be returned)
	* @return
	*/
	public List<Podcast> getPodcastAttributesByFeedUrl(String feedUrl);

	public Podcast getPodcastForIdentifier(String identifier)  throws BusinessException;		  
	  
}

