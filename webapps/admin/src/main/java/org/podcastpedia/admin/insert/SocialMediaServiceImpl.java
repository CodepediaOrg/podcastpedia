package org.podcastpedia.admin.insert;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.Tag;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SocialMediaServiceImpl implements SocialMediaService {
	
	private static Logger LOG = Logger.getLogger(SocialMediaServiceImpl.class);

	public final static int MAX_TWEET_LENGTH = 140;
	public final static int TWEET_URL_LENGTH = 140;
	private static final int COUNTED_URL_LENGTH = 25;

	public void postOnTwitterAboutNewPodcast(Podcast podcast, String urlOnPodcastpedia) {
		if(podcast.getTwitterPage()!=null && podcast.getTwitterPage().length() > 0){
			StringBuilder tweet = new StringBuilder();
			
			tweet.append("\"").append(podcast.getTitle()).append("\""); //append podcast title to the tweet
			tweet.length();
			
			String twitterAccount = podcast.getTwitterPage().substring(podcast.getTwitterPage().lastIndexOf("/") + 1);
			tweet.append(" by @").append(twitterAccount);
			
			if(tweet.length() + COUNTED_URL_LENGTH <= MAX_TWEET_LENGTH) {
				//it gets tweeted only if the title plus author and link is not too big
				int tweetLengthBeforeUrlAddition = tweet.length();
				tweetLengthBeforeUrlAddition += COUNTED_URL_LENGTH;
				
				tweet.append("\n").append(urlOnPodcastpedia).append("\n\n");

				//now we are adding the tags as long as the tweet's size doesn't jump over the MAX limit   
				for(Tag tag : podcast.getTags()){
					String trimmedTag = tag.getName().replaceAll("\\s+", "");
					if(tweetLengthBeforeUrlAddition + " #".length() + trimmedTag.length() <= MAX_TWEET_LENGTH){
						tweet.append("#").append(trimmedTag).append(" ");//replace
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

	private void postOnTwiter(StringBuilder tweet) {
		try {
			Twitter twitter = TwitterFactory.getSingleton();
			Status status = twitter.updateStatus(tweet.toString());
			LOG.debug("Successfully updated status to " + status.getText());
		} catch (TwitterException e) {
			LOG.error("ERROR when trying to post on Twitter ", e);
		}
	}

}
