package org.podcastpedia.admin.dao.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.dao.UpdateDao;
import org.podcastpedia.admin.dao.helper.InputMarkNewEpisodesAsNew;
import org.podcastpedia.common.domain.Episode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class UpdateDaoTest {
	private static Logger LOG = Logger.getLogger(UpdateDaoTest.class);
	
	
	@Autowired
	private UpdateDao updateDao;
	
	@Test
	public void testUpdateEpisodeAvailability(){

		Episode e = new Episode();
		e.setPodcastId(16);
		e.setEpisodeId(1);
		e.setIsAvailable(0);
		
		updateDao.updateEpisodeAvailability(e);
		
	}
	
	@Ignore @Test
	public void markNewEpisodesAsNew(){
		
		//first make them all not new
		updateDao.markAllEpisodesAsNotNew(1);
		List<Episode> episodes = new ArrayList<Episode>();
		Episode episode1 = new Episode();
		episode1.setEpisodeId(0);
		episodes.add(episode1);
		Episode episode2 = new Episode();
		episode2.setEpisodeId(1);
		episodes.add(episode2);
		
		InputMarkNewEpisodesAsNew input = new InputMarkNewEpisodesAsNew();
		input.setEpisodes(episodes);
		input.setPodcastId(1);
		updateDao.markNewEpisodesAsNew(input);
	}
}
