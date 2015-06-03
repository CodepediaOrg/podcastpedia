package org.podcastpedia.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.web.podcasts.PodcastDao;
import org.podcastpedia.web.podcasts.PodcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-spring-context.xml")
public class PodcastServiceImplTest {

	@Autowired
	private PodcastService  podcastService;
	
	@Autowired
	private PodcastDao podcastDao;
		
	@Test
	public void getPodcastById() throws BusinessException{
		Podcast podcast = null;
		podcast  = podcastService.getPodcastById(1);
		
		Assert.assertNotNull(podcast);
		
		podcast  = podcastService.getPodcastById(1);
		
		Assert.assertNotNull(podcast); 
		
	}
	
	
}
