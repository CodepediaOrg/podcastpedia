package org.podcastpedia.admin.sitemaps;

import java.net.MalformedURLException;

import org.podcastpedia.common.types.UpdateFrequencyType;

/**
 * The SitemapService interface provides methods to create sitemaps and sitemap index files
 * 
 * @author ama
 *
 */
public interface SitemapService {

	/**
	 * Creates a sitemap for the podcasts and episodes with the given update frequency
	 * @param frequency
	 */
	public void createSitemapForPodcastsWithFrequency(UpdateFrequencyType frequency, String sitemapsDirectoryPath) throws MalformedURLException;
	
	/**
	 * Creates the sitemap index file
	 */
	public void createSitemapIndexFile(String sitemapsDirectoryPath) throws MalformedURLException;
}
