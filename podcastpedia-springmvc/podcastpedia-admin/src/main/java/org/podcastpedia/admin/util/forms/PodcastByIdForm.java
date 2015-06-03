package org.podcastpedia.admin.util.forms;

public class PodcastByIdForm {

	int podcastId;
	
	boolean isFeedLoadedFromLocalFile;

	/** string with podcast ids separated by comma (,) */
	String podcastIds;
	
	public int getPodcastId() {
		return podcastId;
	}

	public void setPodcastId(int podcastId) {
		this.podcastId = podcastId;
	}

	public String getPodcastIds() {
		return podcastIds;
	}

	public void setPodcastIds(String podcastIds) {
		this.podcastIds = podcastIds;
	}

	public boolean getIsFeedLoadedFromLocalFile() {
		return isFeedLoadedFromLocalFile;
	}

	public void setIsFeedLoadedFromLocalFile(boolean isFeedLoadedFromLocalFile) {
		this.isFeedLoadedFromLocalFile = isFeedLoadedFromLocalFile;
	}
				
}
