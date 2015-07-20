package org.podcastpedia.dao.mybatis;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.core.podcasts.PodcastDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-spring-context.xml") // the Spring context file
//@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class PodcastDaoIT {
	
	private static Logger LOG = Logger.getLogger(PodcastDaoIT.class);
	
	@Autowired
	private PodcastDao podcastDao;
	
		
	/** Test podcast categories */
	@Ignore @Test //TODO make automatic 
	public void testGetPodcastByUrl() throws Exception {
		LOG.debug(" \n\n------ executing PodcastDaoTest.testGetPodcastByUrl -------");
		
		String podcastUrl = "http://podcast.wdr.de/wissenmachtah.xml";		
		Podcast response = podcastDao.getPodcastByURL(podcastUrl);	
		//this podcasts belongs to four categories
		assert response.getCategories().size() == 4; 
		
		String podcastUrl2 = "http://www.zdf.de/ZDFmediathek/podcast/1193018?view=podcast";		
		Podcast response2 = podcastDao.getPodcastByURL(podcastUrl2);	
		//this podcasts belongs to NO categories
		assert response2.getCategories().size() == 0;		
		
	}

	/** Test podcast episodes */
	@Test
	public void testGetPodcastEpisodes() throws Exception {
		LOG.debug(" \n\n------ executing PodcastDaoTest.testGetPodcastEpisodes -------");
		
		String podcastUrl = "http://podcast.wdr.de/quarks.xml";		
		Podcast response = podcastDao.getPodcastByURL(podcastUrl);	
		//verify if the podcastId has been set as expected
		assert response.getPodcastId() == response.getEpisodes().get(0).getPodcastId();		
		
	}

	
	@Test
	public void testGetPodcastById() throws Exception {
		LOG.debug(" ------ executing testGetRecommendedPodcasts ------- ");
			
		Podcast podcastById = podcastDao.getPodcastById(1);	
		//verify if the podcastId has been set as expected
		assert podcastById != null; 	
		
	}	
	
//	@Test
//	public void testGetAllTags(){
//		Map<String, Integer> params = new HashMap<String, Integer>();
//		params.put("nrTagsPerPage", 300);			
//		params.put("startPoint", 0 * 300);
//		List<Tag> allTagsOrderedByNumberOfPodcasts = podcastDao.getTagsOrderedByNumberOfPodcasts(params);
//		
//		Assert.assertTrue(allTagsOrderedByNumberOfPodcasts.size()>100);
//		
//	}
}
