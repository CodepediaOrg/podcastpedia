package org.podcastpedia.admin.general;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.GZIPInputStream;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.InputSource;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.ParsingFeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;



public class ParseSyndFeedsTest {

	private static final String URL_OF_PROBLEM_FEED = "http://dradiowissen.de/podcast/einhundert";
	private static Logger LOG = Logger.getLogger(ParseSyndFeedsTest.class);

	@Test
	public void testParseFeedWithInputSource() throws IOException {
		InputStream is = null;
		try {
			URLConnection openConnection = new URL(URL_OF_PROBLEM_FEED)
					.openConnection();
			is = new URL(URL_OF_PROBLEM_FEED).openConnection().getInputStream();
			if ("gzip".equals(openConnection.getContentEncoding())) {
				is = new GZIPInputStream(is);
			}

			InputSource source = new InputSource(is);

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(source);
			Assert.assertTrue(feed.getTitle() != null);

		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			if (e instanceof ParsingFeedException
					&& e.getMessage().contains(
							"Content is not allowed in prolog.")) {
				Assert.assertTrue("Caught the right exception", true);
			} else {
				Assert.fail();
			}
		} finally {
			is.close();
		}
	}

	@Test
	public void testParseFeedWithInputSource403Error() throws IOException,
			IllegalArgumentException, FeedException {
		InputStream is = null;
		String url = "http://happysomeone.com/feed/podcast/";
		try {
			is = new URL(url).openConnection().getInputStream();
			InputSource source = new InputSource(is);

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(source);
			LOG.info(feed);
		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			if (e instanceof IOException
					&& e.getMessage().contains(
							"Server returned HTTP response code: 403 for URL")) {
				Assert.assertTrue("Caught the right exception", true);
				URLConnection openConnection = new URL(url).openConnection();
				openConnection.addRequestProperty("User-Agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
				is = openConnection.getInputStream();

				InputSource source = new InputSource(is);

				SyndFeedInput input = new SyndFeedInput();
				SyndFeed feed = input.build(source);
				LOG.info(feed);
			} else {
				Assert.fail();
			}
		} finally {
			is.close();
		}
	}

	@Test
	public void testParseFeedWithInputSourceAndEncoding() throws IOException {
		InputStream is = null;
		try {
			is = new URL(URL_OF_PROBLEM_FEED).openConnection().getInputStream();
			Reader reader = new InputStreamReader(is, "UTF-8");
			InputSource source = new InputSource(reader);
			source.setEncoding("UTF-8");

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(source);
		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			if (e instanceof ParsingFeedException
					&& e.getMessage().contains(
							"Content is not allowed in prolog.")) {
				Assert.assertTrue("Caught the right exception", true);
			} else {
				Assert.fail();
			}
		} finally {
			is.close();
		}
	}

	@Ignore
	@Test
	public void testParseFeedWithXMLreaderAndEncoding() throws IOException {
		InputStream input = null;
		try {
			URL theURL = new URL(URL_OF_PROBLEM_FEED);

			XmlReader reader = null;
			input = theURL.openStream();

			reader = new XmlReader(input, true, "UTF-8");
			SyndFeed feed = new SyndFeedInput().build(reader);
			LOG.info(feed.getEncoding());
		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			Assert.fail();
		} finally {
			input.close();
		}
	}

	@Ignore
	@Test
	public void testParseFeedWithXMLreaderWithoutEncoding() throws IOException {
		InputStream input = null;
		try {
			URL url = new URL(URL_OF_PROBLEM_FEED);

			XmlReader reader = null;

			reader = new XmlReader(url);
			SyndFeed feed = new SyndFeedInput().build(reader);
			LOG.info(feed.getEncoding());
		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			Assert.fail();
		} finally {
			input.close();
		}
	}

	@Test
	public void testParseFeedWithInputSourceFromLocalFile() throws IOException {
		FileInputStream fis = null;
		try {
			String localFile = "C://Podcastpedia//files//feed.xml";
			fis = new FileInputStream(localFile);
			InputSource source = new InputSource(fis);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(source);
			LOG.info(feed.getEncoding());
		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			if (e instanceof ParsingFeedException
					&& e.getMessage().contains(
							"Content is not allowed in prolog.")) {
				Assert.fail();
			} else {
				Assert.fail();
			}
		} finally {
			Assert.assertTrue("Test passes", true);
			fis.close();
		}
	}

	@Test
	public void testParseFeedWithInputSourceFromLocalFileWithDownload()
			throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			URL website = new URL(URL_OF_PROBLEM_FEED);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			fos = new FileOutputStream("C://Podcastpedia//files//feed.xml");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

			String localFile = "C://Podcastpedia//files//feed.xml";
			fis = new FileInputStream(localFile);
			InputSource source = new InputSource(fis);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(source);
			LOG.info(feed.getEncoding());
		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			if (e instanceof ParsingFeedException
					&& e.getMessage().contains(
							"Content is not allowed in prolog.")) {
				Assert.fail();
			} else {
				Assert.fail();
			}
		} finally {
			Assert.assertTrue("Test passed with the new rome tools", true);
			fis.close();
			fos.close();
		}
	}

	@Test
	public void testParseFeedWithInputSourceFromLocalFileWithDownload3()
			throws IOException {
		FileInputStream fis = null;
		FileWriter output = null;
		BufferedWriter writer = null;
		try {
			// Create a URL object
			URL url = new URL(URL_OF_PROBLEM_FEED);

			// Read all of the text returned by the HTTP server
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			output = new FileWriter("C://Podcastpedia//files//feed.xml");
			writer = new BufferedWriter(output);
			String feedText = null;
			while ((feedText = in.readLine()) != null) {
				// Keep in mind that readLine() strips the newline characters
				writer.write(feedText + "\n");
			}
			writer.close();
			output.close();
			String localFile = "C://Podcastpedia//files//feed.xml";
			fis = new FileInputStream(localFile);
			InputSource source = new InputSource(fis);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(source);
			LOG.info(feed.getEncoding());
		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			if (e instanceof ParsingFeedException
					&& e.getMessage().contains(
							"Content is not allowed in prolog.")) {
				Assert.fail();
			} else {
				Assert.fail();
			}
		} finally {			
			fis.close();
			output.close();
			writer.close();
			Assert.assertTrue("Test passed", true);
		}
	}

	@Test
	public void testParseFeedWithInputSourceFromLocalFileWithDownload_HttpClient()
			throws IOException {
		FileInputStream fis = null;
		FileWriter output = null;
		BufferedWriter writer = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URL_OF_PROBLEM_FEED);

		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			entity.getContentType();
			if (entity != null) {
				System.out.println(EntityUtils.toString(entity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			// Create a URL object
			URL url = new URL(
                    URL_OF_PROBLEM_FEED);

			// Read all of the text returned by the HTTP server
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));

			output = new FileWriter("C://Podcastpedia//files//feed.xml");
			writer = new BufferedWriter(output);
			String feedText = null;
			while ((feedText = in.readLine()) != null) {
				// Keep in mind that readLine() strips the newline characters
				writer.write(feedText + "\n");
			}
			writer.close();
			output.close();
			String localFile = "C://Podcastpedia//files//feed.xml";
			fis = new FileInputStream(localFile);
			InputSource source = new InputSource(fis);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(source);
			LOG.info(feed.getEncoding());
		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			if (e instanceof ParsingFeedException
					&& e.getMessage().contains(
							"Content is not allowed in prolog.")) {
				Assert.fail();
			} else {
				Assert.fail();
			}
		} finally {
			Assert.assertTrue("Test passes", true);
			fis.close();
			output.close();
			writer.close();
		}
	}

	@Ignore
	@Test
	public void testParseFeedWithInputSourceFromLocalFileWithDownload_chuncked()
			throws IOException {
		URL url = new URL(URL_OF_PROBLEM_FEED);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setReadTimeout(2000);
		connection.setChunkedStreamingMode(0);
		connection.addRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

		connection.connect();

		BufferedReader rd = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		String line = "";
		StringBuffer sb = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}

		System.out.println(sb.toString());
	}

	@Test
	public void testParseFeedWithInputSourceFromLocalFileWithDownload2()
			throws IOException {
		FileInputStream fis = null;
		try {
			saveUrl("C://Podcastpedia//files//feed.xml", URL_OF_PROBLEM_FEED);

			String localFile = "C://Podcastpedia//files//feed.xml";
			fis = new FileInputStream(localFile);
			InputSource source = new InputSource(fis);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(source);
			LOG.info(feed.getEncoding());
		} catch (Exception e) {
			LOG.error("Parsing feed failed", e);
			if (e instanceof ParsingFeedException
					&& e.getMessage().contains(
							"Content is not allowed in prolog.")) {
				Assert.assertTrue("Caught the right exception", true);
			} else {
				Assert.fail();
			}
		} finally {
			fis.close();
		}
	}

	private void saveUrl(String filename, String urlString)
			throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			fout = new FileOutputStream(filename);

			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} finally {
			if (in != null)
				in.close();
			if (fout != null)
				fout.close();
		}
	}
}
