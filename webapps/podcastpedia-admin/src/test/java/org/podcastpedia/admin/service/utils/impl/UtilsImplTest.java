package org.podcastpedia.admin.service.utils.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.dao.ReadDao;
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
public class UtilsImplTest {
	private static Logger LOG = Logger.getLogger(UtilsImplTest.class);
	
	
	@Autowired
	private ReadDao readDao;

	
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
	public void testSetPodcastAttributes() throws IllegalArgumentException, FeedException, IOException{
		String podcastUrl = "http://podcast.wdr.de/quarks.xml";
		Podcast podcast = new Podcast();
		podcast.setUrl(podcastUrl);
		podcastAndEpisodeAttributesService.setPodcastFeedAttributes(podcast, false);
		
		assertNotNull(podcast.getLastEpisodeMediaUrl());
		assertNotNull(podcast.getPublicationDate());
		
		assertTrue(podcast.getTitle().equals("Quarks & Co - zum Mitnehmen"));
		assertTrue(podcast.getDescription().equals("Quarks & Co: Das Wissenschaftsmagazin"));
	}
	
}
