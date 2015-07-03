package org.podcastpedia.core.tags;

import org.podcastpedia.common.domain.Tag;

import java.util.List;
import java.util.Map;

public interface TagDao {
	
	/**
	  * Returns all tags ordered by number of podcasts descending  
	  * @param params 
	  * @return
	  */
	public List<Tag> getTagsOrderedByNumberOfPodcasts(Map<String, Integer> params);

	/**
	 * Returns list of tags (select from tags like '%query')
	 * 
	 * @param query
	 * @return
	 */
	public List<Tag> getTagList(String query);
	
}
