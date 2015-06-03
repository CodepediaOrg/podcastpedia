package org.podcastpedia.common.domain;

/**
 * Class that represents the intersection table for Podcasts and Categories in the m:n relationship. 
 * 
 * @author amasia
 *
 */
public class PodcastCategory {

	int podcastId;
	int categoryId;
	
	public int getPodcastId() {
		return podcastId;
	}
	public void setPodcastId(int podcastId) {
		this.podcastId = podcastId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
