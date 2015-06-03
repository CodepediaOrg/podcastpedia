package org.podcastpedia.web.tags;

import java.util.List;
import java.util.Map;

import org.podcastpedia.common.domain.Tag;

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
