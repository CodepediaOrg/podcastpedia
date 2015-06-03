package org.podcastpedia.admin.dao.helper;

import java.util.List;

import org.podcastpedia.common.domain.Episode;

public class InputMarkNewEpisodesAsNew {

	private List<Episode> episodes;
	
	private Integer podcastId;

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public Integer getPodcastId() {
		return podcastId;
	}

	public void setPodcastId(Integer podcastId) {
		this.podcastId = podcastId;
	}
	
	
}
