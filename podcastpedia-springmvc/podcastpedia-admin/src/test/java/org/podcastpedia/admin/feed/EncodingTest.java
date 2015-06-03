package org.podcastpedia.admin.feed;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.ParsingFeedException;
import com.rometools.rome.io.SyndFeedInput;


public class EncodingTest {
	
	private static final String FEED_URL = "http://www.deutschlandfunk.de/podcast-pisaplus.1181.de.podcast";
	
	private static final String FEED_URL2 = "http://massage-ausbildung.podspot.de/rss";
	
	private static Logger LOG = Logger.getLogger(EncodingTest.class);
	
	@Test
	public void testParseFeed() throws MalformedURLException, IOException, IllegalArgumentException, FeedException{
		
		LOG.debug(" Executing testParseFeed");
		URLConnection openConnection = new URL(FEED_URL).openConnection();	
		
		InputStream is = new URL(FEED_URL).openConnection().getInputStream();
		if("gzip".equals(openConnection.getContentEncoding())){
			is = new GZIPInputStream(is);
		}
		InputSource source = new InputSource(is);
		source.setEncoding("UTF-8");
		
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(source);
        	    
		is.close(); // we close the stream after building the feed 	
	}	
	
	@Ignore @Test(expected=ParsingFeedException.class)
	public void testParseFeedThrowError() throws MalformedURLException, IOException, IllegalArgumentException, FeedException{
		
		LOG.debug(" Executing testParseFeed");
		
		InputStream is = new URL(FEED_URL).openConnection().getInputStream();
		InputSource source = new InputSource(is);
		source.setEncoding("UTF-8");
		
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(source);
        	    
		is.close(); // we close the stream after building the feed 	
	}

	@Ignore @Test
	public void testParseFeedForceWithoutUTF8() throws MalformedURLException, IOException, IllegalArgumentException, FeedException{

		LOG.debug(" Executing testParseFeed");
		
		InputStream is = new URL(FEED_URL).openConnection().getInputStream();
		InputSource source = new InputSource(is);
		
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(source);
        	    
		is.close(); // we close the stream after building the feed
		LOG.debug("Feed description " + feed.getDescription());		
		Assert.assertTrue("Point reached", true);		
	}
	
	@Ignore @Test
	public void testParseFeedForceUTF8() throws MalformedURLException, IOException, IllegalArgumentException, FeedException{
		
		LOG.debug(" Executing testParseFeed");
		
		InputStream is = new URL(FEED_URL).openConnection().getInputStream();
		Reader reader = new InputStreamReader(is,"UTF-8");
		InputSource source = new InputSource(reader);
		source.setEncoding("UTF-8");
		
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(source);
        	    
		is.close(); // we close the stream after building the feed
		
		LOG.debug("Feed description " + feed.getDescription());
		Assert.assertTrue("Point reached", true);
	}
	
	@Test
	public void testParseFeed_2() throws MalformedURLException, IOException, IllegalArgumentException, FeedException{
		
		LOG.debug(" Executing testParseFeed");
		
		InputStream is = new URL(FEED_URL2).openConnection().getInputStream();
		InputSource source = new InputSource(is);
		source.setEncoding("UTF-8");
		
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(source);
        	    
		is.close(); // we close the stream after building the feed
		
		LOG.debug("Feed description " + feed.getDescription());		
		
		Assert.assertTrue("Point reached", true);			
	}

	@Test
	public void testParseFeedForceWithoutUTF8_2() throws MalformedURLException, IOException, IllegalArgumentException, FeedException{

		LOG.debug(" Executing testParseFeed");
		
		InputStream is = new URL(FEED_URL2).openConnection().getInputStream();
		InputSource source = new InputSource(is);
		
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(source);
        	    
		is.close(); // we close the stream after building the feed
		LOG.debug("Feed description " + feed.getDescription());		
		
		Assert.assertTrue("Point reached", true);		
	}
	
	@Test
	public void testParseFeedForceUTF8_2() throws MalformedURLException, IOException, IllegalArgumentException, FeedException{
		
		LOG.debug(" Executing testParseFeed");
		
		InputStream is = new URL(FEED_URL2).openConnection().getInputStream();
		Reader reader = new InputStreamReader(is,"UTF-8");
		InputSource source = new InputSource(reader);
		source.setEncoding("UTF-8");
		
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(source);
        	    
		is.close(); // we close the stream after building the feed
		
		LOG.debug("Feed description " + feed.getDescription());
		Assert.assertTrue("Point reached", true);
	}
	
}
