package org.podcastpedia.core.tags;

import org.podcastpedia.common.domain.Tag;

import java.util.List;

public interface TagService {
	
	/**
	 * Returns current page of tags ordered by number of podcasts 
	 * 
	 * @param page
	 * @param nrTagsPerPage
	 * @return
	 */
	public List<Tag> getTagsOrderedByNumberOfPodcasts(Integer page, Integer nrTagsPerPage);

	/**
	 * Returns a list of tags for a given query (used in autocomplete function) 
	 * 
	 * @param query
	 * @return
	 */
	public List<Tag> getTagList(String query);	
}
