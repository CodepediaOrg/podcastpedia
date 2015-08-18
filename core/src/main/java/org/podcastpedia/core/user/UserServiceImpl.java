package org.podcastpedia.core.user;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	public List<Podcast> getSubscriptions(String username) {
		return userDao.getSubscriptions(username);
	}

	@Override
	public List<Episode> getLatestEpisodesFromSubscriptions(String username) {
		return userDao.getLatestEpisodesFromSubscriptions(username);
	}

    @Override
    public void submitUserForRegistration(User user) {
        userDao.addUser(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
