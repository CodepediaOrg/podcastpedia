package org.podcastpedia.web.episodes;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.EpisodeWrapper;
import org.podcastpedia.common.exception.BusinessException;


/**
 * Provides episode details
 * @author adrian
 *
 */
public interface EpisodeService {

	public EpisodeWrapper getEpisodeDetails(Integer podcastId, Integer episodeId) throws BusinessException;
	
	  
	  /**
	   * Returns an episode based on the given podcastId and episodeId
	   * 
	   * @param podcastId
	   * @param episodeId
	   * @return
	   */
	  public Episode getEpisodeById(Integer podcastId, Integer episodeId);


	/**
	 * Returns the episodes from the archive to be displayed for the current page
	 * @param podcastId
	 * @param currentPage
	 * @param numberEpisodes
	 * @return
	 * @throws BusinessException 
	 */
	public List<Episode> getEpisodesFromArchive(Integer podcastId,
			Integer currentPage, Integer numberEpisodes) throws BusinessException;
	
	/**
	 * Returns the episodes ordered by publication date descending, starting from @param offset and returning a @param limit of episodes
	 * 
	 * @param podcastId
	 * @param offset
	 * @param limit
	 * @return
	 * @throws BusinessException
	 */
	public List<Episode> getEpisodesForPodcast(Integer podcastId,
			Integer offset, Integer limit) throws BusinessException;	

	/**
	 * Given the number of Episodes it calculates how many episodes should be returend per page 
	 * and how many pages are to be displayed 
	 * @param numberOfEpisodes
	 * @return
	 */
	public Integer getNumberOfArchivePages(Integer numberOfEpisodes);	
		    
}
