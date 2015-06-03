package org.podcastpedia.admin.general;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.insert.SocialMediaService;
import org.podcastpedia.admin.util.read.ReadService;
import org.podcastpedia.common.domain.Podcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class TestTwitterPosting {
	
	private static Logger LOG = Logger.getLogger(TestTwitterPosting.class);	
	
	@Autowired
	SocialMediaService socialMediaService;
	
	@Autowired
	ReadService readService;
	
	@Ignore
    @Test
	public void testPostingToTwitter() throws TwitterException{
		Twitter twitter = TwitterFactory.getSingleton();
		String message="\"Another Visit to Transylvania\" by Euromaxx: Lifestyle Europe (DW) \n http://bit.ly/1cHB7MH";
		Status status = twitter.updateStatus(message);
		LOG.debug("Successfully updated status to " + status.getText());
	}
	
	@Ignore @Test
	public void test(){
		Podcast podcast = readService.getPodcastAttributesByFeedUrl("http://www.qdnow.com/grammar.xml");
		socialMediaService.postOnTwitterAboutNewPodcast(podcast, "http://www.podcastpedia.org/podcasts/639/Grammar-Girl-Quick-and-Dirty-Tips-for-Better-Writing");
	}

    @Ignore
    @Test
    public void testPostingToFacebook() throws TwitterException{
        Twitter twitter = TwitterFactory.getSingleton();
        String message="\"Another Visit to Transylvania\" by Euromaxx: Lifestyle Europe (DW) \n http://bit.ly/1cHB7MH";
        Status status = twitter.updateStatus(message);
        LOG.debug("Successfully updated status to " + status.getText());
    }
}
