package org.podcastpedia.core.categories;

import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.PodcastCategory;

import java.util.List;


public interface CategoryDao {

	/**
	 * Returns a list with all available categories 
	 * @return
	 */
	public List<Category> getAllCategories();
	
	/**
	 * Returns a list with the categories that have podcasts belong to that category and orders the result
	 * by number of podcasts descending
	 * 
	 * @return
	 */
	public List<Category> getCategoriesOrderedByNoOfPodcasts();	
	
	/**
	 * Adds a new data set in PODCASTS_CATEGORIES table 
	 * @param podcastCategory
	 */
	public void insertPodcastCategory(PodcastCategory podcastCategory);
	
	/**
	 * Returns complete details stored in database for a category given ist name. 
	 * @param categoryName
	 * @return
	 */
	public Category getCategoryByName(String categoryName);
		
}
