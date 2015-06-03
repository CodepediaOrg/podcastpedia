package org.podcastpedia.admin.update.batch;

import java.util.concurrent.Future;

import org.podcastpedia.common.types.UpdateFrequencyType;

public interface BatchUpdateService {

	/**
	 * Updates all the podcasts with the given frequency with the given number
	 * of worker threads
	 * 
	 * @param updateFrequency
	 */
	public void updatePodcastsWithFrequency(
			UpdateFrequencyType updateFrequency, Integer numberWorkerThreads);

	/**
	 * Updates the podcasts from this range
	 * 
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public Future<String> updatePodcastsFromRange(Integer startRow,
			Integer endRow);

	/**
	 * Updates the podcasts from this range
	 * 
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public Future<String> updatePodcastsFromRange(Integer startRow,
			Integer endRow, Integer podcastUpdateFrequency);

	/**
	 * all podcasts will be updated - episodes that are not reachable will be
	 * removed, new episodes are added and podcast feed attributes are updated
	 */
	public void updateAllPodcasts();
}
