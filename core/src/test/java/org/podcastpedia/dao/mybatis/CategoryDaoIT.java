package org.podcastpedia.dao.mybatis;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.common.domain.Category;
import org.podcastpedia.core.categories.CategoryDao;
import org.podcastpedia.test.util.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-spring-context.xml") // the Spring context file
public class CategoryDaoIT {
	
	private static Logger LOG = Logger.getLogger(SearchDaoIT.class);
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private TestUtils testUtils; 
	
	@Test
	public void testGetAllCategories() throws Exception {
		LOG.debug(" \n\n------ executing CategoryDaoTest.testGetAllCategories -------");	
		List<Category> results = categoryDao.getAllCategories();
		LOG.debug("Number of categories found - " + results.size());
		Integer numberOfCategories = testUtils.getNumberOfCategories();
		
		assert results.size() == numberOfCategories;
	}		
		
}
