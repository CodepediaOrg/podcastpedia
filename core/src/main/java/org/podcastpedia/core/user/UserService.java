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

    /**
     * Verifies if the given @param username is already registered(present in the database)
     * @param username
     * @return
     */
    public boolean isExistingUser(String username);

    /**
     * Subscribes the user identified by the @param username to the podcast identified by the @param podcastId
     * @param username
     * @param podcastId
     */
    public void subscribeToPodcast(String username, int podcastId);

    /** An user has the possibility to vote up(+1) or down(-1) for a podcast once registered.
     * If she voted before for the podcast, the newest vote will override the old value, and
     * the votedOn date will be updated */
    void votePodcast(String username, int podcastId, int vote);

    /** An user has the possibility to vote up(+1) or down(-1) for an episode once registered.
     * If she voted before for the episode, the newest vote will override the old value, and
     * the votedOn date will be updated */
    void voteEpisode(String username, int podcastId, int episodeId, int voteValue);

    void enableUser(String username, String registrationToken);
}
