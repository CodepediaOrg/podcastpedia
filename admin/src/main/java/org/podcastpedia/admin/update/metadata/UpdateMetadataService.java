package org.podcastpedia.admin.update.metadata;

import org.podcastpedia.common.domain.Podcast;

public interface UpdateMetadataService {
	/**
	 * Given a podcast from a form only the metadata (language, tags, categories
	 * will be updated)
	 * 
	 * @param podcast
	 */
	public void updatePodcastMetadata(Podcast podcast);
}
