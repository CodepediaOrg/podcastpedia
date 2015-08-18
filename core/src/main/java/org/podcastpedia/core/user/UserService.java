package org.podcastpedia.core.user;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.User;

import java.util.List;

public interface UserService {

	public List<Podcast> getSubscriptions(String username);

	/**
	 * Returns a list with the latest episodes from a user, identified by username, subscriptions
	 * ordered by the publication date DESC(endent)
	 *
	 * @param username
	 * @return
	 */
	public List<Episode> getLatestEpisodesFromSubscriptions(String username);

    /**
     * After a user has filled and submitted the registration form, the request will be processed (database entry is set, not activated yet,
     * and an email is sent to confirm the user)
     * @param user
     */
    public void submitUserForRegistration(User user);
}
