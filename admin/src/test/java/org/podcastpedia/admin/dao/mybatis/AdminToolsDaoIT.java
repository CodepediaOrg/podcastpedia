package org.podcastpedia.admin.dao.mybatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.dao.AdminToolsDao;
import org.podcastpedia.admin.test.util.TestUtils;
import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.Tag;
import org.podcastpedia.common.types.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class AdminToolsDaoIT {
	
	private static final int INSERTED_PODCAST_ID = 111111;

	private static Logger LOG = Logger.getLogger(AdminToolsDaoIT.class);
	
	@Autowired
	private AdminToolsDao dao; 

	@Autowired
	private TestUtils testUtils; 
	
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
		
		dao.insertEpisode(episode);
	}
	
	@Ignore @Test //transaction does not work in Test because of MyIsam enginge
	@Transactional
	@Rollback(true)
	public void testInsertPodcast(){
		
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
		
		dao.addPodcast(podcast);
	}
	
	@Test
	public void testGetAllCategories() throws Exception {
		LOG.debug(" \n\n------ executing testGetAllCategories -------");	
		List<Category> results = dao.getAllCategories();
		LOG.debug("Number of categories found - " + results.size());
		Integer numberOfCategories = testUtils.getNumberOfCategories();
		
		assert results.size() == numberOfCategories;
	}	
	
	@Test
	public void testGetAllPodcasts() throws Exception {
		LOG.debug(" \n\n------ executing testGetAllPodcasts -------");	
		List<Podcast> results = dao.getAllPodcasts();
		LOG.debug("Number of podcasts found - " + results.size());
		assert results != null;
	}	
	
	@Test
	public void testNothing(){}
	
	@Test
	public void testCleanString(){
		String title = "quarks and co";
		String cleanedTitle = title.trim().replaceAll("[^a-zA-Z0-9\\s]", "");
		cleanedTitle = cleanedTitle.trim().replaceAll("[\\s]", "-");
	
		LOG.debug("title :" + title + "cleaned title :"  + cleanedTitle);
		
		assert cleanedTitle.equals("quarks-and-co");

	}
	
	@Test
	public void testCleanString2(){
		String title = "  quarks  &     co   ";
		String cleanedTitle = title.trim().replaceAll("[^a-zA-Z0-9\\s]", "");
		cleanedTitle = cleanedTitle.replaceAll(" +", "-");
	
		LOG.debug("title :" + title + "cleaned title :"  + cleanedTitle);
		
		assert cleanedTitle.equals("quarks-co");

	}	
	
	@Test
	public void testCleanString3Encoding(){
		String title = "  quarks   și     co   ";
		String cleanedTitle = title.trim().replaceAll("[^a-zA-Z0-9\\sș]", "");
		cleanedTitle = cleanedTitle.replaceAll(" +", "-");
	
		LOG.debug("title :" + title + "cleaned title :"  + cleanedTitle);
		
		assert cleanedTitle.equals("quarks-și-co");

	}	
	
	@Test
	public void testCleanString4(){
		String title = "  quarks ---  and -    co   ";
		String cleanedTitle = title.trim().replaceAll("[^a-zA-Z0-9\\sș\\-]", "");
		cleanedTitle = cleanedTitle.replaceAll("[\\-| ]+", "-");
	
		LOG.debug("title :" + title + "cleaned title :"  + cleanedTitle);
		
		assert cleanedTitle.equals("quarks-and-co");

	}	
	
	@Test
	public void testCleanString5(){
		String title = "  quarks ---  and -    co  28.06.2009 test... ";
		String cleanedTitle = title.trim().replaceAll("[^a-zA-Z0-9\\sș\\-\\.]", "");
		cleanedTitle = cleanedTitle.replaceAll("[\\-| |\\.]+", "-");
	
		LOG.debug("title :" + title + "cleaned title :"  + cleanedTitle);
		
		assert cleanedTitle.equals("quarks-and-co-28-06-2009-test-");

	}	
	
	@Test
	public void testGetTag() throws Exception {
		LOG.debug(" \n\n------ executing testGetTag -------");	
		Tag response = dao.getTagByName("java");
		LOG.debug("Tag id - " + response.getTagId());
		assert response != null;
	}	
	
//	@Test
//	public void testInsertPodcastTag() throws Exception {
//		LOG.debug(" \n\n------ executing testInsertPodcastTag -------");	
//		PodcastTag podcastTag= new PodcastTag();
//		podcastTag.setPodcastId(1);
//		podcastTag.setTagId(8);
//		
//		dao.insertPodcastTag(1, 8);
//		
//		LOG.debug("Tag id - " + response.getTagId());
//		assert response != null;
//	}	
}
