package org.podcastpedia.core.episodes;

import org.podcastpedia.common.domain.Episode;

import java.util.List;
import java.util.Map;

/**
 * Interface for database access to get information about episodes
 * 
 */
public interface EpisodeDao {

	/**
	 * Returns an episode based on the given podcastId and episodeId
	 * 
	 * @param input
	 * @return
	 */
	public Episode getEpisodeById(Map<String, Object> input);

	/**
	 * Returns the episodes for podcast, by default ordered by publication date descending, as this is the natural
	 * order in the context of podcasts. 
	 * 
	 * As filtering parameters present in the input map, we can have offset (the point where to start looking)
	 * and limit (how many episodes should be retrieved)
	 * 
	 * @param params
	 * @return
	 */
	public List<Episode> getEpisodesForPodcastId(Map<String, Object> params);		

	public List<Episode> getEpisodesForPodcastName(Map<String, Object> params);
}
