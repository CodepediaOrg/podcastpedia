package org.podcastpedia.admin.dao.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.dao.DeleteDao;
import org.podcastpedia.common.domain.util.EpisodeListToBeDeleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class DeleteDaoIT {
	private static Logger LOG = Logger.getLogger(DeleteDaoIT.class);
	
	
	@Autowired
	private DeleteDao deleteDao;
	
	@Ignore @Test
	public void testDeleteEpisodes(){
		EpisodeListToBeDeleted input = new EpisodeListToBeDeleted();
		input.setPodcastId(4);
		
   		List<Integer> episodesIDs = new ArrayList<Integer>();
   		episodesIDs.add(4);
   		
   		input.setEpisodesIDs(episodesIDs);
   		
   		deleteDao.deleteEpisodes(input);
	}
}
