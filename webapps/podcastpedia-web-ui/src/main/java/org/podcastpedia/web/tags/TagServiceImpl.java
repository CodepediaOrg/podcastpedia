package org.podcastpedia.web.tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.podcastpedia.common.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

public class TagServiceImpl implements TagService {

	@Autowired
	TagDao tagDao;
	
    @Cacheable(value="podcasts", key="T(java.lang.String).valueOf(#page).concat('-').concat(#root.method.name)") 
	public List<Tag> getTagsOrderedByNumberOfPodcasts(Integer page, Integer nrTagsPerPage) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("startPoint", page * nrTagsPerPage);
		params.put("nrTagsPerPage", nrTagsPerPage);	
		
		return tagDao.getTagsOrderedByNumberOfPodcasts(params);
	}

	public List<Tag> getTagList(String query) {
		return tagDao.getTagList(query + "%");
	}
	
	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
    
}
