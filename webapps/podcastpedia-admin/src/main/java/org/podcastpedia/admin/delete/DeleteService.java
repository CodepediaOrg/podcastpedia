package org.podcastpedia.admin.delete;

public interface DeleteService {
	
	/**
	 * Deletes the podcast and its episodes by its id.
	 * 
	 * @param podcastId the id of the podcast to be deleted 
	 */
	public void deletePodcastById(int podcastId);
	
	/**
	 * Marks the podcast with podcastId as not reachable (404), so it won't get
	 * picked by the update jobs or the web application.
	 * 
	 * It's more like an update instead of the delete, but it's in the deletion context
	 * 
	 * @param podcastId the id of the podcast to be marked as unavailable
	 */
	public void markPodcastAsUnavailable(int podcastId);

}
