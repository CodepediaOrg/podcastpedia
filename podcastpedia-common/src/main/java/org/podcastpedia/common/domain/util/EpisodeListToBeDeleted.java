package org.podcastpedia.common.domain.util;

import java.util.List;

public class EpisodeListToBeDeleted {

	private int podcastId;
	
	private List<Integer> episodesIDs;

	public int getPodcastId() {
		return podcastId;
	}

	public void setPodcastId(int podcastId) {
		this.podcastId = podcastId;
	}

	public List<Integer> getEpisodesIDs() {
		return episodesIDs;
	}

	public void setEpisodesIDs(List<Integer> episodesIDs) {
		this.episodesIDs = episodesIDs;
	}
		
}
