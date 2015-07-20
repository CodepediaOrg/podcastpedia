package org.podcastpedia.test;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class JavaGeneralTest {
	
	private static Logger LOG = Logger.getLogger(JavaGeneralTest.class);
	
	@Test
	public void testModOperator(){
		
		int numberPerPage = 10;
		int numberOfResults = 13;
		int dividedBy = numberOfResults/numberPerPage;
		
		int numberOfPages = 1 + dividedBy;
		
		assertTrue("Two pages ", 2 == numberOfPages);
	}
	
	@Test
	public void testGetUrlQuery() throws MalformedURLException{
		URL url = new URL("http://www.someurl.com?someparam1=10&someparam2=10&currentPage=1");
		String query = url.getQuery();
		Assert.assertTrue(query.equals("someparam1=10&someparam2=10&currentPage=1"));
		query = query.substring(0, query.lastIndexOf("&currentPage="));
		Assert.assertTrue(query.equals("someparam1=10&someparam2=10"));
//		String regex = "/&currentPage(\\=[^&]*)?(?=&|$)|^currentPage(\\=[^&]*)?(&|$)/";
//		query = query.replaceAll(regex, "");
//		Regex
//		Assert.assertTrue(query.equals("someparam1=10&someparam2=10"));
		
	}
	
	@Test
	public void testIntegerRounding(){
		Float f = new Float(20.6);
		
		LOG.debug("rounding up?  " + f.intValue());
		Assert.assertTrue("should be rounded to 21", Math.round(f) == 21);
		
		f = new Float(19.49f);
		Assert.assertTrue("should be rounded to 19", Math.round(f) == 19);
		LOG.debug("rounding down? " + f.intValue());
	}
	
	
	
}
