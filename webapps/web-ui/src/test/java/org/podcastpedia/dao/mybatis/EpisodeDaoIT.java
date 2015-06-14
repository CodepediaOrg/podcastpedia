package org.podcastpedia.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.web.episodes.EpisodeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-spring-context.xml") // the Spring context file
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class EpisodeDaoIT {
	
	private static Logger LOG = Logger.getLogger(EpisodeDaoIT.class);
	
	@Autowired
	private EpisodeDao episodeDao; 
		
	@Test 
	public void testGetLastEpisodesForPodcastIdentifier() throws Exception {
		LOG.debug(" \n\n------ executing PodcastDaoTest.testGetPodcastByUrl -------");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "quarks");
		params.put("count", 10);		
		List<Episode> lastEpisodes = episodeDao.getEpisodesForPodcastName(params);
		
		Assert.assertTrue(lastEpisodes.size() > 0);
	}	
}
