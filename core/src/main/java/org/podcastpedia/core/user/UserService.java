package org.podcastpedia.core.user;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.User;

import java.util.List;

public interface UserService {

	public List<Podcast> getSubscriptions(String username);

    List<Podcast> getPodcastsForPlaylist(String email, String playlist);

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
     * Generates a new registration-token to be used in password reactivation and sets the user
     * temporarily on disabled until she gets reactivated and changes her password
     *
     * @param user
     */
    void updateUserForPasswordReset(User user);

    /**
     * Verifies if the given @param username is already registered(present in the database)
     * @param username
     * @return
     */
    public boolean isExistingUser(String username);

    /**
     * Subscribes the user identified by the @param username to the podcast identified by the @param podcastId
     * @param email
     * @param podcastId
     * @param playlist
     * @param email
     */
    public void subscribeToPodcast(String email, int podcastId, String playlist);

    /**
     * Unsubscribes the user identified by the @param username from the podcast identified by the @param podcastId
     *
     * @param username
     * @param podcastId
     */
    public void unsubscribeFromPodcast(String username, int podcastId);

    /** An user has the possibility to vote up(+1) or down(-1) for a podcast once registered.
     * If she voted before for the podcast, the newest vote will override the old value, and
     * the votedOn date will be updated */
    void votePodcast(String username, int podcastId, int vote);

    /** An user has the possibility to vote up(+1) or down(-1) for an episode once registered.
     * If she voted before for the episode, the newest vote will override the old value, and
     * the votedOn date will be updated */
    void voteEpisode(String username, int podcastId, int episodeId, int voteValue);

    void enableUserAfterRegistration(String username, String registrationToken);

    /**
     * The user will be re-enabled after the password forgotten/reset email is confirmed.
     *
     * @param username of the user
     * @param registrationToken that is generated when password is reset
     */
    public void enableUserAfterPasswordForgotten(String username, String registrationToken);

    /**
     *
     *
     * @param email the id of the user
     * @return a list of playlist names created by the user
     */
    public List<String> getPlaylistNames(String email);


    /**
     * Removes the podcast from the user's playlist
     *
     * @param email of the logged in user
     * @param podcastId of the podcast to be removed from the playlist
     * @param playlist the playlist
     */
    void removeFromPlaylist(String email, Integer podcastId, String playlist);
}
