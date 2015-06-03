package org.podcastpedia.admin.util.restclient;

public interface RestClient {

	/** 
	 * method is used to invoke te rest operation on the podcastmania to refresh the categories
	 * after a new podcast has been added in the database 
	 */
	public void invokeRefreshReferenceData();
	
	/** 
	 * method is used to invoke the rest operation on the podcastmania to refresh the recommended podcasts
	 * (e.g. after they have been added to the database) 
	 */
	public void invokeRefreshNewestAndRecommendedPodcasts();
	
	/**
	 * invokes controller to refresh all caches of the applications 
	 */
	public void invokeRefreshAllCaches();
	
	/** rest service on main application will be called to flush the search results cache */
	public void invokeFlushSearchResultsCache();
}
