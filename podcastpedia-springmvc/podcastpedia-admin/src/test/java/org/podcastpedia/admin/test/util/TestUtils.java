package org.podcastpedia.admin.test.util;

import org.podcastpedia.common.domain.Episode;


public interface TestUtils {
	/**
	 * Returns the number of available categories from the database 
	 * @return
	 */
	public Integer getNumberOfCategories();
	
	/**
	 * Returns a random episode that is still reachable and has also a publication date 
	 * @return
	 */
	public Episode getRandomAvailableEpisodeWithPubDate();
}
