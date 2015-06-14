package org.podcastpedia.admin.dao.mybatis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.dao.DeleteDao;
import org.podcastpedia.admin.dao.InsertDao;
import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class InsertDaoIT {
	
	private static final int INSERTED_PODCAST_ID = 111111;

	private static Logger LOG = Logger.getLogger(InsertDaoIT.class);
	
	@Autowired
	private InsertDao insertDao; 
	
	@Autowired
	private DeleteDao deleteDao;
	
	@Autowired
	private ReadDao readDao;
	
	@Ignore @Test
	@Transactional
	@Rollback(true)
	public void testInsertEpisode(){
		
		Episode episode = new Episode();
		episode.setPodcastId(INSERTED_PODCAST_ID);
		episode.setDescription("Episode description");
		episode.setLink("episode LInk");
		episode.setMediaUrl("media url");
		episode.setEpisodeId(0);
		episode.setTitle("title");
		episode.setMediaType(MediaType.Audio); 
		
		insertDao.insertEpisode(episode);
	}
	
	@Ignore @Test //transaction does not work in Test because of the MyIsam engine
//	@Transactional
//	@Rollback(true)
	public void testInsertPodcast(){
		
		LOG.debug("Executing testInsertPodcast to prove insertion of a podcast");
		Podcast podcast = new Podcast();
		
//		podcast.setPodcastId(INSERTED_PODCAST_ID);
		podcast.setCopyright("Ama corporation copyright");
		List<Integer> categoryIDs = new ArrayList<Integer>();
		categoryIDs.add(47);
		podcast.setDescription("some description");
		podcast.setLastEpisodeMediaUrl("last episode media url");
		podcast.setLastUpdate(new Date());
		podcast.setInsertionDate(new Date());
		podcast.setLink("this is an unique links daaa");
		podcast.setPodcastLanguage("de");
		podcast.setTitle("Podcast's title");
		podcast.setUrl("www.ama-corporation.com");
		podcast.setUrlOfImageToDisplay("url of the podcast's image");
		podcast.setUpdateFrequency(UpdateFrequencyType.YEARLY);
		
		insertDao.addPodcast(podcast);
		int newPodcastId = podcast.getPodcastId();
		
		Podcast readPodcast = readDao.getPodcastAndEpisodesById(newPodcastId);
		
		assertNotNull(readPodcast);
		
		//now delete the podcast since Transactional is not avaialble in MySql default engine
		deleteDao.deletePodcastById(newPodcastId);
		
		//now try to read it again
		readPodcast = readDao.getPodcastAndEpisodesById(newPodcastId);
		
		assertNull(readPodcast);
	}
	
	@Test
	public void testReadPodcast(){
		Podcast response = readDao.getPodcastAndEpisodesById(1);
		assertNotNull(response); 
	}
	
}
