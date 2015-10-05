package org.podcastpedia.core.user;

import org.podcastpedia.common.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    public static final int USER_NOT_YET_ENABLED = 0;
    public static final int USER_ENABLED = 1;
    public static final String ROLE_USER = "ROLE_USER";
    @Autowired
	UserDao userDao;

	@Override
	public List<Podcast> getSubscriptions(String username) {

        List<Podcast> subscriptions = userDao.getSubscriptions(username);
        //return only the last 3 episodes, ordered by publication date
        for(Podcast subscription: subscriptions){
            if(!subscription.getEpisodes().isEmpty() && subscription.getEpisodes().size() > 3){
                subscription.setEpisodes(subscription.getEpisodes().subList(0,3));
            }
        }

        return subscriptions;
	}

	@Override
	public List<Episode> getLatestEpisodesFromSubscriptions(String username) {
		return userDao.getLatestEpisodesFromSubscriptions(username);
	}

    @Override
    public void submitUserForRegistration(User user) {
        user.setRegistrationDate(new Date());
        user.setEnabled(USER_NOT_YET_ENABLED);
        //if display name not introduced then use the name from the email address(the one before @)
        if(user.getDisplayName()==null){
            user.setDisplayName(user.getUsername());
        }
        user.setPassword(encryptPassword(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    public boolean isExistingUser(String username) {
        User user = userDao.selectUserByUsername(username);

        return user != null;
    }

    @Override
    public void subscribeToPodcast(String username, int podcastId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", username);
        params.put("podcastId", podcastId);

        userDao.subscribeToPodcast(params);
    }

    @Override
    public void unsubscribeFromPodcast(String username, int podcastId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", username);
        params.put("podcastId", podcastId);

        userDao.unsubscribeFromPodcast(params);
    }

    @Override
    @CacheEvict(value="podcasts", key="#podcastId")
    public void votePodcast(final String username, final int podcastId, final int vote) {
        PodcastVote podcastVote = new PodcastVote();
        podcastVote.setUsername(username);
        podcastVote.setPodcastId(podcastId);
        podcastVote.setVote(vote);

        userDao.addPodcastVote(podcastVote);
    }

    @Override
    @CacheEvict(value = "podcasts", key = "T(java.lang.String).valueOf(#podcastId).concat('-').concat(#episodeId)")
    public void voteEpisode(String username, int podcastId, int episodeId, int vote) {
        EpisodeVote episodeVote = new EpisodeVote();
        episodeVote.setUsername(username);
        episodeVote.setPodcastId(podcastId);
        episodeVote.setEpisodeId(episodeId);
        episodeVote.setVote(vote);

        userDao.addEpisodeVote(episodeVote);
    }

    @Override
    public void enableUser(String username, String registrationToken) {
        User user = new User();
        user.setUsername(username);
        user.setRole(ROLE_USER);
        user.setEnabled(USER_ENABLED);
        user.setRegistrationToken(registrationToken);

        userDao.addUserRole(user);
        userDao.enableUser(user);
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        return hashedPassword;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
