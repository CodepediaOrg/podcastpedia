package org.podcastpedia.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Wrapper class around episodes that contains also the other episodes (surrounding) episodes
 * @author adrian
 *
 */
public class EpisodeWrapper implements Serializable{

	private static final long serialVersionUID = -8200059651950458609L;

	/** the episode */
	private Episode episode;
	
	/** list with surrounding episodes (ordered by episode id) */
	private List<Episode> lastEpisodes;

	public Episode getEpisode() {
		return episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}

	public List<Episode> getLastEpisodes() {
		return lastEpisodes;
	}

	public void setLastEpisodes(List<Episode> lastEpisodes) {
		this.lastEpisodes = lastEpisodes;
	}
	
}
