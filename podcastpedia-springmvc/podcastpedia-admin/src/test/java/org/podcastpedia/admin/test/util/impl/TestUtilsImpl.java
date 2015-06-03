package org.podcastpedia.admin.test.util.impl;

import org.podcastpedia.admin.test.util.TestUtils;
import org.podcastpedia.common.domain.Episode;
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

	//TODO implement method with mapping in jdbc from the book 
	public Episode getRandomAvailableEpisodeWithPubDate() {
		String sql = "select podcast_id, " +
					 "	episode_id, " +
					 "	media_url, " +
					 "from" +
					 "	episodes " +
					 "where" +
					 "	is_available=1 " +
					 "and " +
					 "	publication_date is not null";
		
		return null;
	}

}
