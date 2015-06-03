package org.podcastpedia.admin.service;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.sitemaps.SitemapService;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.redfin.sitemapgenerator.WebSitemapGenerator;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class SitemapServiceTest {
	
	private static Logger LOG = Logger.getLogger(SitemapServiceTest.class);
	
		
	@Autowired
	private SitemapService sitemapService;
	
	@Autowired
	private ConfigBean configBean;
	
	@Ignore @Test
	public void testSitemapFilesCreation() throws MalformedURLException{
		
		LOG.debug("----- testing testSitemapFilesCreation -----");
		
		sitemapService.createSitemapForPodcastsWithFrequency(UpdateFrequencyType.MONTHLY, configBean.get("SITEMAPS_DIRECTORY_PATH_TEST"));
		
		sitemapService.createSitemapIndexFile(configBean.get("SITEMAPS_DIRECTORY_PATH_TEST"));

		File targetDirectory = new File(configBean.get("SITEMAPS_DIRECTORY_PATH_TEST"));
		// generate sitemap index for foo + bar grgrg 

		//get all the files from the directory
		File[] files = targetDirectory.listFiles();
		Assert.assertTrue("2 files have been created", files.length == 2);		
		
	}
	
   @Ignore @Test
   public void testBIGSitemaps() throws MalformedURLException{
	   File targetDirectory = new File(configBean.get("SITEMAPS_DIRECTORY_PATH_TEST"));
	   WebSitemapGenerator wsg = WebSitemapGenerator.builder("http://www.example.com", targetDirectory)
				.fileNamePrefix("sitemap_dailyyy").gzip(true)
				.build();
	   for (int i = 0; i < 60000; i++) wsg.addUrl("http://www.example.com/doc"+i+".html");
	   wsg.write();
	   wsg.writeSitemapsWithIndex(); // generate the sitemap_index.xml	   
   }
}
