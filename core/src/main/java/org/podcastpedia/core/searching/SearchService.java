package org.podcastpedia.core.searching;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


/**
 * Service interface used to access the DAO layer, for receiving search results.  
 * 
 * @author amasia
 *
 */
@Service("searchService")
public interface SearchService {
	
	/**
	 * Based on the search criteria a list of podcasts will be returned and  some extra metadata. 
	 * The method is used both by the search controller where based on the selected search criteria found in @param search data 
	 * results will be returned and in the feed controller where basically withe the same search criteria except the <code>numberOFPages</code>
	 * and <code>numberOfResults</code>  a feed (rss or atom) will be generated for the current page.  
	 * 
	 * @param searchData
	 * @return 
	 */
	public SearchResult getResultsForSearchCriteria(SearchData searchData) throws UnsupportedEncodingException;

	
}

