package org.podcastpedia.admin.update.feed;

import java.io.IOException;
import java.net.MalformedURLException;

import com.rometools.rome.io.FeedException;

public interface UpdateFeedAttributesService {

	/**
	 * Given the podcast id this function method will update the feed attributes
	 * (title, description, author etc....)
	 * 
	 * @param podcastId
	 * @param isLoadedFromLocalFile
	 * @throws FeedException
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws MalformedURLException
	 */
	public void updateFeedAttributesForPodcastId(Integer podcastId,
			Boolean isLoadedFromLocalFile) throws MalformedURLException,
			IllegalArgumentException, IOException, FeedException;
}
