package org.podcastpedia.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class TestUtilsImpl implements TestUtils{

	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	public Integer getNumberOfCategories() {
		String sql = "select count(*) from categories";
		Integer response = jdbcTemplate.queryForInt(sql);
		
		return response; 
	}

}
