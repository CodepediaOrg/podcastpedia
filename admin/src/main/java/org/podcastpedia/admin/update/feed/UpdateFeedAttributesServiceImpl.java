package org.podcastpedia.admin.update.feed;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.admin.dao.UpdateDao;
import org.podcastpedia.admin.update.UpdateService;
import org.podcastpedia.admin.update.UpdateServiceImpl;
import org.podcastpedia.admin.util.PodcastAndEpisodeAttributesService;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.rometools.rome.io.FeedException;

public class UpdateFeedAttributesServiceImpl implements
		UpdateFeedAttributesService {

	private static Logger LOG = Logger.getLogger(UpdateServiceImpl.class);

	@Autowired
	private ReadDao readDao;

	@Autowired
	private UpdateDao updateDao;
	
	@Autowired
	private UpdateService updateService;

	@Autowired
	private PodcastAndEpisodeAttributesService podcastAndEpisodeAttributesService;

	@Autowired
	private ConfigBean configBean;
	@Override
	public void updateFeedAttributesForPodcastId(Integer podcastId,
			Boolean isLoadedFromLocalFile) throws MalformedURLException,
			IllegalArgumentException, IOException, FeedException {

		Podcast podcast = readDao.getPodcastById(podcastId);

		if (isLoadedFromLocalFile) {
			podcast.setPodcastFeed(updateService.getSyndFeedFromLocalFile(configBean
					.get("LOCAL_PATH_FOR_FEED")));
		}

		try {
			boolean feedAttributesModified = podcastAndEpisodeAttributesService
					.setPodcastFeedAttributesWithWarnings(podcast);
			if (feedAttributesModified) {
				updateDao.updatePodcastFeedAttributesById(podcast);
				LOG.info("Feed [" + podcast.getUrl() + "] attributes MODIFIED ");
			} else {
				LOG.info("Feed [" + podcast.getUrl()
						+ "] attributes NOT MODIFIED");
			}
		} catch (IllegalArgumentException e) {
			LOG.error(
					" IllegalArgumentException has been thrown when setting attributes for podcast "
							+ podcast.getUrl(), e);
		} catch (FeedException e) {
			LOG.error(
					" FeedException has been thrown when setting attributes for podcast "
							+ podcast.getUrl(), e);
		} catch (IOException e) {
			LOG.error(
					" IOException has been thrown for podcast "
							+ podcast.getUrl(), e);
		} catch (Exception e) {
			LOG.error(
					" Unknown exception when updating podcast "
							+ podcast.getUrl(), e);
		}

	}

}
