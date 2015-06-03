package org.podcastpedia.admin.insert;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.Tag;
import org.springframework.social.facebook.api.Facebook;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SocialMediaServiceImpl implements SocialMediaService {
	
	private static Logger LOG = Logger.getLogger(SocialMediaServiceImpl.class);

	public final static int MAX_TWEET_LENGTH = 140;
	public final static int TWEET_URL_LENGTH = 140;
	private static final int COUNTED_URL_LENGTH = 25;

	/*
    private final Facebook facebook;

    @Inject
	public SocialMediaServiceImpl(Facebook facebook){
    	this.facebook=facebook;
    }
	*/

	public void postOnTwitterAboutNewPodcast(Podcast podcast, String urlOnPodcastpedia) {
		if(podcast.getTwitterPage()!=null && podcast.getTwitterPage().length() > 0){
			StringBuffer tweet = new StringBuffer(); 
			
			tweet.append("\"" + podcast.getTitle() + "\""); //append podcast title to the tweet
			tweet.length();
			
			String twitterAccount = podcast.getTwitterPage().substring(podcast.getTwitterPage().lastIndexOf("/") + 1);
			tweet.append(" by @" + twitterAccount);
			
			if(tweet.length() + COUNTED_URL_LENGTH <= MAX_TWEET_LENGTH) {
				//it gets tweeted only if the title plus author and link is not too big
				int tweetLengthBeforeUrlAddition = tweet.length();
				tweetLengthBeforeUrlAddition += COUNTED_URL_LENGTH;
				
				tweet.append("\n"+ urlOnPodcastpedia + "\n\n");

				//now we are adding the tags as long as the tweet's size doesn't jump over the MAX limit   
				for(Tag tag : podcast.getTags()){
					String trimmedTag = tag.getName().replaceAll("\\s+", "");
					if(tweetLengthBeforeUrlAddition + " #".length() + trimmedTag.length() <= MAX_TWEET_LENGTH){
						tweet.append("#" + trimmedTag + " ");//replace
						tweetLengthBeforeUrlAddition += ("#" + trimmedTag + " ").length();
					} else {
						break;
					}
				}
				
				LOG.info(tweet.toString());
				postOnTwiter(tweet);
			}
			
		}

	}

	private void postOnTwiter(StringBuffer tweet) {
		try {
			Twitter twitter = TwitterFactory.getSingleton();
			Status status = twitter.updateStatus(tweet.toString());
			LOG.debug("Successfully updated status to " + status.getText());
		} catch (TwitterException e) {
			LOG.error("ERROR when trying to post on Twitter ", e);
		}
	}

    /*
	@Override
	public void postOnFacebookAboutNewPodcast(Podcast podcast,
			String urlOnPodcastpedia) {
		facebook.feedOperations().updateStatus("test");				
	}
    */
}
