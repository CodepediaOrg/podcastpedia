package org.podcastpedia.admin.config.impl;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
public class ConfigBeanJdbcImplTest {
	
	private static Logger LOG = Logger.getLogger(ConfigBeanJdbcImplTest.class);	
	
	@Autowired
	private ConfigBean configBean;
	
	@Test
	public void testGetNumberOfWorkerThreadsProperty(){
		Integer numberOfWorkerThreads = Integer.valueOf(configBean.get("NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS"));
		LOG.debug("Current number of working threads to update podcasts is " + numberOfWorkerThreads);
		
		Assert.assertTrue("Should be bigger than 0", numberOfWorkerThreads > 0);
	}
}
