package org.podcastpedia.admin.delete;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.dao.DeleteDao;
import org.podcastpedia.admin.dao.UpdateDao;
import org.podcastpedia.common.domain.Podcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DeleteServiceImpl implements DeleteService {

	private static Logger LOG = Logger.getLogger(DeleteServiceImpl.class);

	@Autowired
	private DeleteDao deleteDao;

	@Autowired
	private UpdateDao updateDao;

	@Transactional
	public void deletePodcastById(int podcastId) {
		LOG.debug("Received request to delete podcast with id " + podcastId
				+ " from database");
		deleteDao.deletePodcastCategoriesByPodcastId(podcastId);
		deleteDao.deletePodcastTagsByPodcastId(podcastId);
		deleteDao.deleteEpisodesByPodcastId(podcastId);
		deleteDao.deletePodcastById(podcastId);
	}

	@Override
	public void markPodcastAsUnavailable(int podcastId) {

		LOG.debug("Received request to mark podcast with id " + podcastId
				+ " as not reachable");

		// mark podcast and its episode as not reachable (status "404")
		Podcast podcast = new Podcast();
		podcast.setPodcastId(podcastId);
		podcast.setAvailability(404);
		updateDao.updatePodcastAvailability(podcast);

	}

    @Override
    public void deleteUser(String email) {
        deleteDao.deleteUserSubscriptions(email);
        deleteDao.deleteUserFromAuthorities(email);
        deleteDao.deleteUser(email);
    }


}
