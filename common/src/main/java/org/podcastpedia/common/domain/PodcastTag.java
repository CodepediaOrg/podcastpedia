package org.podcastpedia.common.domain;

public class PodcastTag {

	private int podcastId;
	
	private long tagId;

	public PodcastTag(){}
	
	public PodcastTag(int podcastId, long tagId) {
		this.podcastId = podcastId;
		this.tagId = tagId;
	}
	
	public int getPodcastId() {
		return podcastId;
	}

	public void setPodcastId(int podcastId) {
		this.podcastId = podcastId;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}
		
}
