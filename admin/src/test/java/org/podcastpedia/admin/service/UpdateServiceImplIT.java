package org.podcastpedia.admin.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.admin.update.UpdateService;
import org.podcastpedia.admin.util.PodcastAndEpisodeAttributesService;
import org.podcastpedia.common.domain.Podcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.rometools.rome.io.FeedException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class UpdateServiceImplIT {
	
	private static Logger LOG = Logger.getLogger(UpdateServiceImplIT.class);
	
	@Autowired
	private ReadDao readDao;
	
	@Autowired
	private UpdateService updateService;
	
	@Autowired
	private PodcastAndEpisodeAttributesService podcastAndEpisodeAttributesService;
	
//	@Test
//	public void testGetTagByName(){
//		Podcast podcast1 = readDao.getPodcastById(22);
//		Podcast podcast1 = readDao.getPodcastById(483);
//		Podcast podcast1 = readDao.getPodcastById(519);
//		Podcast podcast1 = readDao.getPodcastById(22);
//		
//	}
	
	@Test
	public void testSetAttributes() throws IllegalArgumentException, FeedException, IOException{
		Podcast podcast1 = readDao.getPodcastAndEpisodesById(1);
		podcastAndEpisodeAttributesService.setPodcastFeedAttributes(podcast1, false);		
	}
	
}
