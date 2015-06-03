package org.podcastpedia.web.caching;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;


public class CacheUpdateServiceImpl implements CacheUpdateService {

	protected static Logger LOG = Logger.getLogger(CacheUpdateServiceImpl.class);

	@CacheEvict(value="referenceData", allEntries=true)
	public void clearReferenceDataCache() {
		LOG.warn("Reference data cache was flushed");
	}
	
	@CacheEvict(value="newestAndRecommendedPodcasts", allEntries=true)
	public void clearNewestAndRecommendedPodcastsCache() {
		LOG.warn("Start page podcasts (newest, recommended, best rated) cache was flushed");
	}

	/**
	 * Through usage of annotations all of the managed caches will be cleared.
	 */
	@Caching(evict = {
		    @CacheEvict(value="referenceData", allEntries=true),
		    @CacheEvict(value="podcasts", allEntries=true),
		    @CacheEvict(value="searchResults", allEntries=true),
		    @CacheEvict(value="newestAndRecommendedPodcasts", allEntries=true),
		    @CacheEvict(value="randomAndTopRatedPodcasts", allEntries=true)		    
		})	
	public void clearAllCaches() {
		LOG.warn("All caches have been completely cleared");
	}
	
	@CacheEvict(value="searchResults", allEntries=true)
	public void clearSearchResults() {
		LOG.warn("Search results cache is completely flushed");			
	}

}
