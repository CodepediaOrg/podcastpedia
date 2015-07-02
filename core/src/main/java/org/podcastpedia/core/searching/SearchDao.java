package org.podcastpedia.core.searching;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;

import java.util.List;


public interface SearchDao {
	
	/**
	 * Retrieves results based on queryText from the MySQL Database used full-text searches in either 
	 * Natural mode or Boolean Mode. 
	 * @see <a href="http://dev.mysql.com/doc/refman/5.0/en/fulltext-boolean.html"> MySQL Boolean Full-Text Searches </a>
	 * 
	 * @param queryText
	 * @return
	 */	
	public List<Podcast> getPodcastsForSearchCriteria(SearchData searchData); 

	/**
	 * Method used to retrieve beforehand the total number of search results 
	 * to be used in pagination.(boolean mode search)
	 * 
	 * @param searchData
	 * @return
	 */
	public int getNumberOfPodcastsForSearchCriteria(SearchData searchData);
	
	
	/**
	 * Returns the number of episodes that corresponds to the given search
	 * criteria 
	 * 
	 * @param searchData
	 * @return
	 */
	public int getNumberOfEpisodesForSearchCriteria(SearchData searchData);
		
	/**
	 * Returns a list of episodes for the given search criteria 
	 * @param searchData
	 * @return
	 */
	public List<Episode> getEpisodesForSearchCriteria(SearchData searchData);
	
	/**
	 * Inserts a search in the database 
	 * 
	 * @param searchData
	 */
	public void addSearch(SearchData searchData);
}
