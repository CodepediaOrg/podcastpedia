package org.podcastpedia.admin.util.forms;

public class PodcastByFeedUrlForm {
	boolean isFeedLoadedFromLocalFile;
	
	private String feedUrl;

	public String getFeedUrl() {
		return feedUrl;
	}

	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	}

	public boolean getIsFeedLoadedFromLocalFile() {
		return isFeedLoadedFromLocalFile;
	}

	public void setIsFeedLoadedFromLocalFile(boolean isFeedLoadedFromLocalFile) {
		this.isFeedLoadedFromLocalFile = isFeedLoadedFromLocalFile;
	}
		
}
