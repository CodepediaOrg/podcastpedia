package org.podcastpedia.dao.mybatis;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.web.startpage.StartPageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-spring-context.xml") // the Spring context file
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class StartPageDaoIT {
	
	private static Logger LOG = Logger.getLogger(StartPageDaoIT.class);
	
	@Autowired
	private StartPageDao startPageDao;

	/** Get top rated podcasts */
	@Test
	public void testGetRecommendedPodcasts() throws Exception {
		LOG.debug(" ------ executing testGetRecommendedPodcasts ------- ");
			
		List<Podcast> response= startPageDao.getRecommendedPodcasts(10);	
		//verify if the podcastId has been set as expected
		assert response.size() == 5; 	
		
	}

}
