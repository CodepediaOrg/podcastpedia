package org.podcastpedia.core.user;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

	/**
	 * Returns a list with all the podcasts the user, identified by username has subscribed to
	 *
	 * @param username
	 * @return
	 */
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
     * Adds user to the database, but the status is not yet enabled.
     *
     * @param user
     */
    public void addUser(User user);

    /** returns an user given its username */
    public User selectUserByUsername(String username);

    /**
     * Adds username and podcastId to the subscriptions table
     * @param params
     */
    void subscribeToPodcast(Map<String, Object> params);
}
