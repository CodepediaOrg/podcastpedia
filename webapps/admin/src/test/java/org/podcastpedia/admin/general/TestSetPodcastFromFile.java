package org.podcastpedia.admin.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.UpdateFrequencyType;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
public class TestSetPodcastFromFile {
	
	private static Logger LOG = Logger.getLogger(TestSetPodcastFromFile.class);	
	
	@Test
	public void testSetPodcastEnumsFromStrings(){
		LOG.debug("Executing  testGetNumberOfWorkerThreadsProperty");
		Podcast podcast = new Podcast();
		podcast.setUpdateFrequency(UpdateFrequencyType.valueOf("WEEKLY"));
		podcast.setMediaType(MediaType.valueOf("Video"));
		
		Assert.assertTrue(podcast.getUpdateFrequency()== UpdateFrequencyType.WEEKLY);
		Assert.assertTrue(podcast.getMediaType() == MediaType.Video);
	}
	
	@Test
	public void testSetPodcastFromStringLine(){
		LOG.debug("Executing  testSetPodcastFromStringLine");
		Podcast podcast = new Podcast();
		String stringLine = "podcastUrl.xml;29,30;de;Video;WEEKLY;cool podcast,science";
		String[] split = stringLine.split(";");
		//set podcast's url
		podcast.setUrl(split[0]);
		String categoryIdsStr = split[1];		
		//set podcast's category ids
		String[] categoryIds = categoryIdsStr.split(",");
		List<Integer> podcastCategoryIDs = new ArrayList<Integer>();
		for (String categoryId : categoryIds) {
			podcastCategoryIDs.add(Integer.valueOf(categoryId));
		}
		podcast.setCategoryIDs(podcastCategoryIDs);
		//setpodcasts languageCode
		podcast.setPodcastLanguage(split[2]);
		//set podcast's media type
		podcast.setMediaType(MediaType.valueOf(split[3]));
		//set podcast's update Frequency 
		podcast.setUpdateFrequency(UpdateFrequencyType.valueOf(split[4]));
		//set podcast's tags
		podcast.setTagsStr(split[5]);
		
		//do the assertions now
		Assert.assertTrue(podcast.getUrl().equals("podcastUrl.xml"));
		Assert.assertTrue(podcast.getCategoryIDs().size() == 2);
		Assert.assertTrue(podcast.getPodcastLanguage().equals("de"));
		Assert.assertTrue(podcast.getMediaType() == MediaType.Video);
		Assert.assertTrue(podcast.getUpdateFrequency()== UpdateFrequencyType.WEEKLY);
		Assert.assertTrue(podcast.getTagsStr().equals("cool podcast,science"));
	}	
	
	@Test
	public void testUniqueTags(){
		String [] tags = {"baby","boy","baby", "Baby"};		
		//remove duplicate tags if existant 
		Set<String> uniqueTags = new HashSet<String>(Arrays.asList(tags));
		
		Assert.assertTrue("Two unique tags", uniqueTags.size() == 3); 
		LOG.debug(uniqueTags);
	}
	
	
}
