package org.podcastpedia.admin.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.io.MalformedByteSequenceException;
import org.xml.sax.InputSource;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.ParsingFeedException;
import com.rometools.rome.io.SyndFeedInput;

public class SyndFeedServiceImpl implements SyndFeedService {
	
	private static Logger LOG = Logger.getLogger(SyndFeedServiceImpl.class);
	
	private static final String SERVER_RETURNED_HTTP_RESPONSE_CODE_403_FOR_URL = "Server returned HTTP response code: 403 for URL";	
	
	@Override
	public SyndFeed getSyndFeedForUrl(String url) throws MalformedURLException,
			IOException, IllegalArgumentException, FeedException {

		SyndFeed feed = null;
		InputStream is = null;

		try {

			URLConnection openConnection = new URL(url).openConnection();
			openConnection
					.addRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			is = openConnection.getInputStream();
			if ("gzip".equals(openConnection.getContentEncoding())) {
				is = new GZIPInputStream(is);
			}
			InputSource source = new InputSource(is);
			SyndFeedInput input = new SyndFeedInput();
			feed = input.build(source);

		} catch (ParsingFeedException e) {
			LOG.error("************* ParsingFeedException *************\n ", e);
			throw e;
		} catch (MalformedByteSequenceException e) {
			LOG.error(
					"************* MalformedByteSequenceException *************\n ",
					e);
			throw e;
		} catch (IOException e) {
			if (e.getMessage().contains(
					SERVER_RETURNED_HTTP_RESPONSE_CODE_403_FOR_URL)) {

				URLConnection openConnection = new URL(url).openConnection();
				openConnection
						.addRequestProperty("User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				is = openConnection.getInputStream();
				if ("gzip".equals(openConnection.getContentEncoding())) {
					is = new GZIPInputStream(is);
				}
				InputSource source = new InputSource(is);

				SyndFeedInput input = new SyndFeedInput();
				feed = input.build(source);
			} else {
				LOG.error("************* IOException *************\n ", e);
				throw e;
			}
		} finally {
			if (is != null)
				is.close();
		}

		return feed;
	}
}
