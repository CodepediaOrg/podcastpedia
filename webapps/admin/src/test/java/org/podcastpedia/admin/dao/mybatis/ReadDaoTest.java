package org.podcastpedia.admin.dao.mybatis;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.Tag;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.SitemapIndexGenerator;
import com.redfin.sitemapgenerator.SitemapIndexUrl;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class ReadDaoTest {
	private static Logger LOG = Logger.getLogger(ReadDaoTest.class);
	
	
	@Autowired
	private ReadDao readDao;
	
	@Test
	public void testGetTagByName(){
		LOG.debug("Testing method ReadDao.getTabByName");
		Tag tagByName = readDao.getTagByName("Schweiz");
		
		assertNotNull(tagByName);
		
	}
	
	@Test 
	public void testGetPodcastsFromRange(){
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("startRow", 1);
		params.put("endRow", 10);
		List<Podcast> response = readDao.getPodcastsFromRange(params);
		
		Assert.assertTrue(response.size() == 10);
	}
	
	@Test 
	public void testGetPodcastsFromRangeWithUpdateFrequency(){
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("startRow", 1);
		params.put("endRow", 10);
		params.put("updateFrequency", UpdateFrequencyType.WEEKLY.getCode());
		List<Podcast> response = readDao.getPodcastsFromRangeWithUpdateFrequency(params);
		
		Assert.assertTrue(response.size() == 10);
	}	
	
	@Test
	public void testGetNumberOfPodcasts(){
		Integer response = readDao.getNumberOfPodcasts();
		
		Assert.assertTrue(response > 200); 
	}
	
	@Test
	public void testGetNumberOfPodcastsWithUpdateFrequency(){
		Integer response = readDao.getNumberOfPodcastsWithUpdateFrequency(UpdateFrequencyType.WEEKLY.getCode());
		
		Assert.assertTrue(response > 5); 
	}	
	
	@Test
	public void testDistributionOfParameters(){
		Integer totalNumberOfPodcasts = readDao.getNumberOfPodcasts();
		Integer numberOfWorkerThreads  = 5;
		Integer mod = totalNumberOfPodcasts % numberOfWorkerThreads;
		Integer chunkSize = totalNumberOfPodcasts / numberOfWorkerThreads;
		
		for(int i = 0; i < numberOfWorkerThreads; i++ ){
			int startRow = i*chunkSize;			
			if(i == numberOfWorkerThreads -1) {
				chunkSize = chunkSize + mod;
				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize + "]");
			} else {
				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize + "]");	
			}						
		}	 
	}
	
	@Test
	public void testGetPodcastsWithEpisodeWithUpdateFrequency(){
		List<Podcast> podcastsAndEpisodeWithUpdateFrequency = readDao.getPodcastsAndEpisodeWithUpdateFrequency(UpdateFrequencyType.DAILY);
		Assert.assertTrue(podcastsAndEpisodeWithUpdateFrequency.size() > 10 );
	}	
	
	@Ignore @Test
	public void testGenerateSiteMaps() throws MalformedURLException{
		
		File targetDirectory = new File("C:/Podcastpedia/sitemaps");
		//podcasts updated daily		
		generateSiteMapForPodcastsWithFrequency(UpdateFrequencyType.DAILY, targetDirectory, "daily");
		
		//podcasts updated weekly
		generateSiteMapForPodcastsWithFrequency(UpdateFrequencyType.WEEKLY, targetDirectory, "weekly");
		
		//podcasts updated monthly
		generateSiteMapForPodcastsWithFrequency(UpdateFrequencyType.MONTHLY, targetDirectory, "monthly");
		
		//podcasts that don't get updated anymore 
		generateSiteMapForPodcastsWithFrequency(UpdateFrequencyType.TERMINATED, targetDirectory, "terminated");
		
		// generate sitemap index for foo + bar grgrg 
		File outFile = new File("C:/Podcastpedia/sitemaps/sitemap_index.xml");
		SitemapIndexGenerator sig = new SitemapIndexGenerator("http://www.podcastpedia.org", outFile);
		
		//get all the files from the directory
		File[] files = targetDirectory.listFiles();
		for(int i=0; i < files.length; i++){
			boolean isNotSitemapIndexFile = !files[i].getName().startsWith("sitemap_index"); 
			if(isNotSitemapIndexFile){
				SitemapIndexUrl sitemapIndexUrl = new SitemapIndexUrl("http://www.podcastpedia.org/" + files[i].getName(), new Date(files[i].lastModified()));
				sig.addUrl(sitemapIndexUrl);									
			}

		}
		sig.write();		
	}
	
	private void generateSiteMapForPodcastsWithFrequency(UpdateFrequencyType updateFrequency, File targetDirectory, String fileNamePrefix) throws MalformedURLException{
		int nrOfUrls = 0;
		WebSitemapGenerator wsg = WebSitemapGenerator.builder("http://www.podcastpedia.org", targetDirectory).fileNamePrefix(fileNamePrefix).gzip(true).build();
		List<Podcast> podcasts = readDao.getPodcastsAndEpisodeWithUpdateFrequency(updateFrequency);
		for(Podcast podcast : podcasts) {
			String url = "http://www.podcastpedia.org" + "/podcasts/" + podcast.getPodcastId() + "/" + podcast.getTitleInUrl();
			WebSitemapUrl wsmUrl = new WebSitemapUrl.Options(url)
		     .lastMod(podcast.getLastUpdate()).priority(0.9).changeFreq(changeFrequencyFromUpdateFrequency(updateFrequency)).build();			
			wsg.addUrl(wsmUrl);
			nrOfUrls++;
			for(Episode episode : podcast.getEpisodes() ){
				url = "http://www.podcastpedia.org" + "/podcasts/" + podcast.getPodcastId() + "/" + podcast.getTitleInUrl()
						+ "/episodes/" + episode.getEpisodeId() + "/" + episode.getTitleInUrl();
				wsmUrl = new WebSitemapUrl.Options(url)
			     .lastMod(episode.getPublicationDate()).priority(0.8).changeFreq(changeFrequencyFromUpdateFrequency(UpdateFrequencyType.TERMINATED)).build();
				wsg.addUrl(wsmUrl);
				nrOfUrls++;
			}
		}
		
		if(nrOfUrls < 50000){
			wsg.write();			
		} else {
			wsg.write();
			wsg.writeSitemapsWithIndex();				
		}		
	}
	
	private ChangeFreq changeFrequencyFromUpdateFrequency(UpdateFrequencyType updateFrequency) {
	
		ChangeFreq response = null;
		
		if(updateFrequency == UpdateFrequencyType.DAILY) response = ChangeFreq.DAILY;
		if(updateFrequency == UpdateFrequencyType.WEEKLY) response = ChangeFreq.WEEKLY;
		if(updateFrequency == UpdateFrequencyType.MONTHLY) response = ChangeFreq.MONTHLY;
		if(updateFrequency == UpdateFrequencyType.YEARLY) response = ChangeFreq.YEARLY;
		if(updateFrequency == UpdateFrequencyType.TERMINATED) response = ChangeFreq.NEVER;
		
		return response;
			
	}
}
