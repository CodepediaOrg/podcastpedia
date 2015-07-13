package org.podcastpedia.admin.update;

import java.io.IOException;
import java.net.MalformedURLException;

import org.podcastpedia.common.domain.Podcast;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;

public interface UpdateService {

	/**
	 * Called directly from admin ui or from the update automation process it
	 * updates the episodes of the feed (sets not reachable on corresponding
	 * status, add new episodes), in case of error updates also the availability
	 * status of the podcat
	 * 
	 * @param podcastId
	 * @param isCalledManually
	 * @throws IllegalArgumentException
	 * @throws FeedException
	 * @throws IOException
	 */
	public void updatePodcastById(Podcast podcast, Boolean isCalledManually,
			boolean isFeedLoadedFromLocalFile) throws IllegalArgumentException,
			FeedException, IOException;

	
	/**
	 * Given a local filePath we build a SyndFeed object out of it. 
	 *  
	 * @param filePath
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws FeedException
	 */
	public SyndFeed getSyndFeedFromLocalFile(String filePath)
			throws MalformedURLException, IOException,
			IllegalArgumentException, FeedException;	

}
