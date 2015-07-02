package org.podcastpedia.core.categories;

import org.podcastpedia.common.domain.Category;

import java.util.List;

public interface CategoryService {

	/**
	 * Returns a list with the categories that have podcasts belong to that
	 * category and orders the result by number of podcasts descending
	 * 
	 * @return
	 */
	public List<Category> getCategoriesOrderedByNoOfPodcasts();
	
}
