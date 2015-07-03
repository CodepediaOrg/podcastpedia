package org.podcastpedia.core.caching;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequestMapping("/rest/caching/update")
public class CachingUpdateRestController {
	
	protected static Logger LOG = Logger.getLogger(CachingUpdateRestController.class);
	
	@Autowired
	private CacheUpdateService cacheUpdateService;
				
	/**
	 * 
	 * This method is called by the admin application after a new podcast has been added for example
	 * so that the categories with the most podcast are displayed
	 */
	@RequestMapping("flush_newest_and_recommended_podcasts_cache")
	@ResponseStatus(HttpStatus.OK)
	public void evictStartPagePodcastsCache(){
		LOG.info("------ evictStartPagePodcastsCache was called --------");
		cacheUpdateService.clearNewestAndRecommendedPodcastsCache();
	}		

	/**
	 * 
	 * This method is called by the admin application after a new podcast has been added for example
	 * so that the categories with the most podcast are displayed
	 */
	@RequestMapping("flush_search_results_cache")
	@ResponseStatus(HttpStatus.OK)
	public void clearSearchResultsCache(){
		LOG.info("------ clearSearchResultsCache was called --------");
		cacheUpdateService.clearSearchResults();
	}	
	
	/**
	 * 
	 * Method will be called to flush all caches 
	 */
	@RequestMapping("flush_all_caches")
	@ResponseStatus(HttpStatus.OK)
	public void clearAllCaches(){
		LOG.info("------ clearAllCaches was called --------");
		cacheUpdateService.clearAllCaches();
	}
	
	/**
	 * 
	 * This method is called by the admin application after a new podcast has been added for example
	 * so that the categories with the most podcast are displayed
	 */
	@RequestMapping("flush_reference_data_cache")
	@ResponseStatus(HttpStatus.OK)
	public void renewReferenceDataCache(){
		LOG.info("------ renewReferenceDataCache was called --------");
		cacheUpdateService.clearReferenceDataCache();
	}
			
}
