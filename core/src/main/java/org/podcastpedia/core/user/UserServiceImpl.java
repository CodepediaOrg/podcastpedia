package org.podcastpedia.core.user;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    public static final int USER_NOT_YET_ENABLED = 0;
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
        user.setRegistrationDate(new Date());
        user.setEnabled(USER_NOT_YET_ENABLED);
        //if display name not introduced then use the name from the email address(the one before @)
        if(user.getDisplayName()==null){
            user.setDisplayName(user.getUsername());
        }
        userDao.addUser(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
