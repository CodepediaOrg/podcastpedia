package org.podcastpedia.admin.insert;

import com.rometools.rome.io.FeedException;
import org.podcastpedia.common.domain.Podcast;

import java.io.IOException;

public interface InsertService {

	/**
	 * Inserts a podcast in the database. This happens based on the feed url provided by the administrator
	 * (based on this several fields are generated), and some other metadata (e.g. language code of podcast)
	 * provided also by the administrator.
	 *
	 * Returns the podcastId of the new inserted podcast.
	 *
	 * @param podcast
	 */
	public int addPodcast (Podcast podcast) throws IOException, FeedException;


	/**
	 * Given a string line it will return the podcast to be added to the database.
	 * Used in the context of inserting the suggested podcast from file
	 *
	 * @param strLine
	 * @return
	 */
	public ProposedPodcast getPodcastFromStringLine(String strLine) throws Exception;
}
