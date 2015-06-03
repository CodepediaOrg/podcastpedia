package org.podcastpedia.web.categories;

import java.util.List;

import org.podcastpedia.common.domain.Category;

public interface CategoryService {

	/**
	 * Returns a list with the categories that have podcasts belong to that
	 * category and orders the result by number of podcasts descending
	 * 
	 * @return
	 */
	public List<Category> getCategoriesOrderedByNoOfPodcasts();
	
}
