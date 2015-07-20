package org.podcastpedia.admin.update.batch;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.admin.update.UpdateService;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.rometools.rome.io.FeedException;

public class BatchUpdateServiceImpl implements BatchUpdateService {
	
	private static Logger LOG = Logger.getLogger(BatchUpdateServiceImpl.class);
	
	/** default number of threads to work on actualizing podcastws */
	private static final Integer DEFAULT_NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS = 10;
	
	@Autowired
	private ReadDao readDao;
	
	@Autowired
	private ConfigBean configBean;
	
	@Autowired
	private UpdateService updateService;
	
	@Override
	public void updatePodcastsWithFrequency(
			UpdateFrequencyType updateFrequency, Integer numberOfWorkerThreads) {

		LOG.debug("Executing updatePodcastsWithFrequency with update frequency "
				+ updateFrequency);

		Integer podcastsUpdateFrequencyCode = updateFrequency != null ? updateFrequency
				.getCode() : null;

		// if the input parameter is missing than we get it from the database
		if (podcastsUpdateFrequencyCode == null) {
			Integer.valueOf(configBean.get("PODCAST_FREQUENCY_TYPE_TO_UPDATE"));
		}

		Integer totalNumberOfPodcasts = null;

		totalNumberOfPodcasts = readDao
				.getNumberOfPodcastsWithUpdateFrequency(podcastsUpdateFrequencyCode);

		if (numberOfWorkerThreads == null) {
			numberOfWorkerThreads = Integer.valueOf(configBean
					.get("NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS"));
		}
		// set default value if data not available inthe database
		if (numberOfWorkerThreads == null) {
			numberOfWorkerThreads = DEFAULT_NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS;
		}

		Integer mod = totalNumberOfPodcasts % numberOfWorkerThreads;
		Integer chunkSize = totalNumberOfPodcasts / numberOfWorkerThreads;

		for (int i = 0; i < numberOfWorkerThreads; i++) {
			int startRow = i * chunkSize;
			if (i == numberOfWorkerThreads - 1) {
				chunkSize = chunkSize + mod;
				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
						+ "]");
				this.updatePodcastsFromRange(startRow, chunkSize,
						podcastsUpdateFrequencyCode);
			} else {
				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
						+ "]");
				this.updatePodcastsFromRange(startRow, chunkSize,
						podcastsUpdateFrequencyCode);
			}
		}
	}


	@Async
	public Future<String> updatePodcastsFromRange(Integer startRow,
			Integer chunkSize, Integer podcastsUpdateFrequency) {

		List<Podcast> podcastsFromRange = null;

		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("startRow", startRow);
		params.put("endRow", chunkSize);
		params.put("updateFrequency", podcastsUpdateFrequency);

		podcastsFromRange = readDao
				.getPodcastsFromRangeWithUpdateFrequency(params);

		for (Podcast podcast : podcastsFromRange) {
			try {
				updateService.updatePodcastById(podcast, false, false);
			} catch (IllegalArgumentException e) {
				LOG.error(" IllegalArgumentException has been thrown when setting attributes for podcast "
						+ podcast.getUrl() + e.getMessage());
				continue; // go to the next podcast
			} catch (FeedException e) {
				LOG.error(" FeedException has been thrown when setting attributes for podcast "
						+ podcast.getUrl() + e.getMessage());
				continue; // go to the next podcast

			} catch (IOException e) {
				LOG.error(" IOException has been thrown for podcast "
						+ podcast.getUrl() + e.getMessage());
				continue; // go to the next podcast
			} catch (Exception e) {
				LOG.error(" Unknown exception when updating podcast "
						+ podcast.getUrl() + e.getMessage());
				continue; // go to the next podcast
			}
		}

		LOG.info("************** THREAD FINISHED startRow - " + startRow
				+ " **************");
		return new AsyncResult<String>("THREAD finished startRow - " + startRow);

	}

	@Async
	public Future<String> updatePodcastsFromRange(Integer startRow,
			Integer chunkSize) {
		List<Podcast> podcastsFromRange = null;

		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("startRow", startRow);
		params.put("endRow", chunkSize);

		// all update frequencies are considered
		podcastsFromRange = readDao.getPodcastsFromRange(params);

		for (Podcast podcast : podcastsFromRange) {
			try {
				updateService.updatePodcastById(podcast, false, false);
			} catch (IllegalArgumentException e) {
				LOG.error(
						" IllegalArgumentException has been thrown when setting attributes for podcast "
								+ podcast.getUrl(), e);
				continue; // go to the next podcast
			} catch (FeedException e) {
				LOG.error(
						" FeedException has been thrown when setting attributes for podcast "
								+ podcast.getUrl(), e);
				continue; // go to the next podcast

			} catch (IOException e) {
				LOG.error(
						" IOException has been thrown for podcast "
								+ podcast.getUrl(), e);
				continue; // go to the next podcast

			} catch (Exception e) {
				LOG.error(
						" Unknown exception when updating podcast "
								+ podcast.getUrl(), e);
				continue; // go to the next podcast
			}

		}

		LOG.info("************** THREAD FINISHED startRow - " + startRow
				+ " **************");
		return new AsyncResult<String>("THREAD finished startRow - " + startRow);

	}
	
	/**
	 * all podcasts will be updated - episodes that are not reachable will be
	 * removed, new episodes are added and podcast feed attributes are updated
	 */
	public void updateAllPodcasts() {

		Integer numberOfWorkerThreads = Integer.valueOf(configBean
				.get("NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS"));
		// set default value if data not available inthe database
		if (numberOfWorkerThreads == null) {
			numberOfWorkerThreads = DEFAULT_NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS;
		}

		Integer totalNumberOfPodcasts = readDao.getNumberOfPodcasts();

		Integer mod = totalNumberOfPodcasts % numberOfWorkerThreads;
		Integer chunkSize = totalNumberOfPodcasts / numberOfWorkerThreads;

		for (int i = 0; i < numberOfWorkerThreads; i++) {
			int startRow = i * chunkSize;
			if (i == numberOfWorkerThreads - 1) {
				chunkSize = chunkSize + mod;
				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
						+ "]");
				this.updatePodcastsFromRange(startRow, chunkSize);
			} else {
				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
						+ "]");
				this.updatePodcastsFromRange(startRow, chunkSize);
			}
		}
	}	

}
