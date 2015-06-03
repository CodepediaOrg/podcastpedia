package org.podcastpedia.dao.mybatis;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.common.domain.Rating;
import org.podcastpedia.common.domain.Subscription;
import org.podcastpedia.common.domain.Tag;
import org.podcastpedia.web.suggestpodcast.SuggestedPodcast;
import org.podcastpedia.web.tags.TagDao;
import org.podcastpedia.web.userinteraction.UserInteractionDao;
import org.podcastpedia.web.userinteraction.emailsubscription.EmailSubscriber;
import org.podcastpedia.web.userinteraction.emailsubscription.EmailSubscriptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


/**
 * For this class we can use transaction as the tables are InnoDB, not MyISAM as for podcasts and episodes 
 * 
 * @author ama
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-spring-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class UserInteractionDaoTest {
	
	private static Logger LOG = Logger.getLogger(UserInteractionDaoTest.class);
	
	@Autowired
	private UserInteractionDao userInteractionDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private EmailSubscriptionDao emailSubscriptionDao;
	
	
	@Ignore @Test
	public void testGetAllCategories() throws Exception {
		LOG.debug(" \n\n------ executing CategoryDaoTest.testGetAllCategories -------");
		
		SuggestedPodcast input = new SuggestedPodcast();
		input.setFeedUrl("test.xml");
		input.setInsertionDate(new Date());
		
		userInteractionDao.insertSuggestedPodcast(input);

		assert true;
	}
	
	@Test  
	public void testGetTagList() throws Exception {
		LOG.debug(" \n\n------ testGetTagListe -------");

		List<Tag> tagList = tagDao.getTagList("a%");
		
		Assert.assertTrue(tagList.size() > 10); 
		
	}
	
	@Test
	public void testGetEmailSubscriber() {
		EmailSubscriber selectEmailSubscriberByEmail = emailSubscriptionDao.selectEmailSubscriberByEmail("adrian.matei@yahoo.com");
		LOG.info(selectEmailSubscriberByEmail);
	}
	
	@Test
	public void testGetEmailSubscriberSebastian() {
		EmailSubscriber selectEmailSubscriberByEmail = emailSubscriptionDao.selectEmailSubscriberByEmail("sebastian.g.matei@gmail.com");
		LOG.info(selectEmailSubscriberByEmail);
	}	
}
