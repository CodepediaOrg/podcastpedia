package org.podcastpedia.admin.util;

import java.io.IOException;
import java.net.MalformedURLException;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;

public interface SyndFeedService {

	/**
	 * Given the url it returns the SyndFeed built with rome api
	 * 
	 * @param url
	 * @return
	 */
	public SyndFeed getSyndFeedForUrl(String url) throws MalformedURLException,
			IOException, IllegalArgumentException, FeedException;
}
