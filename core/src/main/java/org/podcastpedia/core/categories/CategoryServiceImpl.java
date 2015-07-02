package org.podcastpedia.core.categories;

import org.podcastpedia.common.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	@Cacheable(value = "referenceData", key = "#root.method.name")
	public List<Category> getCategoriesOrderedByNoOfPodcasts() {
		return categoryDao.getCategoriesOrderedByNoOfPodcasts();
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

}
