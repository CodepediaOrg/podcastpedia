package org.podcastpedia.core.caching;

public interface CacheUpdateService {
	
	/** method called to flush reference data cache */
	public void clearReferenceDataCache();
	
	/** method called to flush start page podcasts cache */
	public void clearNewestAndRecommendedPodcastsCache();
	
	/** when called method will flush all existing caches */
	public void clearAllCaches();
	
	/** 
	 * search results cache gets refreshed 
	 */
	public void clearSearchResults();
	
}
