package org.podcastpedia.admin.httpclient;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.admin.dao.ReadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
public class HttpClientTest {

	   private static final int TIMEOUT = 1;

	private static Logger LOG = Logger.getLogger(HttpClientTest.class);	
	
//	   @Autowired
//	   private HttpClient httpClient;
	   
//	   @Autowired
//	   private MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager; 
	   
	   @Autowired
	   ReadDao readDao;
	   
	   @Autowired
		private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
	   
	   @Test
	   public void testCallGoogleWithHeadMethod() throws ClientProtocolException, IOException  {
		   
		   HttpHead method = new HttpHead("http://www.google.de/");
		   CloseableHttpClient httpClient = buildHttpClient();
		   try{
			   	HttpResponse httpResponse = httpClient.execute(method);
				
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				
				assertTrue("Google is reachable", statusCode == HttpStatus.SC_OK);
		   } finally {
	   	      // Release the connection.
	   	      method.releaseConnection();			   
		   }
	   }

	private CloseableHttpClient buildHttpClient() {
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(TIMEOUT * 1000)
		        .setConnectTimeout(TIMEOUT * 1000)
		        .build();		   
		CloseableHttpClient httpClient = HttpClientBuilder
		        .create()
		        .setDefaultRequestConfig(requestConfig)
		        .setConnectionManager(poolingHttpClientConnectionManager)
		        .build();
		return httpClient;
	}	   
	   
	   @Test
	   public void testCallUnencodedUrl() throws ClientProtocolException, IOException{

		   HttpHead method = null;
		   
		   try{
			   method = new HttpHead("http://www.vibefm.ro/vibe-sets/DjOptick-Nextlevel-VibeFmRomania-24.05.2012Dandy[HU]Rhadow[RO].mp3");
			   CloseableHttpClient httpClient = buildHttpClient();
			   
			   httpClient.execute(method);
				
	  	    } catch(IllegalArgumentException e){
	  	    	assertTrue(e.getMessage(), true);
	  	    } catch(Exception e){
	  	    	LOG.debug(e.getMessage());
	  	    } finally {
	  	    	  if(method != null){
			   	      // Release the connection.
			   	      method.releaseConnection();	  	    		  
	  	    	  }
	 	    }	
	   }	   
	   
	   @Test 
	   public void testSomeEpisodeUrls(){
		   String episodeMediaUrl = "http://mp3-download.swr.de/swr2/wissen/sendungen/2012/07/swr2wissen_20120714_weltregierung_ra10.12844s.mp3";
		   
	        //get a http client
		   HttpHead method = new HttpHead(episodeMediaUrl);
		   CloseableHttpClient httpClient = buildHttpClient();
   	      
   	      // Execute the method.
   	      try {
   	    	  HttpResponse httpResponse = httpClient.execute(method);
			  int statusCode = httpResponse.getStatusLine().getStatusCode();
	   	      LOG.info("status code " + statusCode);
			  assertTrue("Episode is reachable", statusCode == HttpStatus.SC_OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}

	   }
	   
	   @Test 
	   public void testSomeEpisodeUrls2(){	
		   
		   String episodeMediaUrl = "http://podcastdownload.npr.org/anon.npr-podcasts/podcast/510289/138620280/npr_138620280.mp3";
		   
	        //get a http client
		   HttpHead method = new HttpHead(episodeMediaUrl);
		   CloseableHttpClient httpClient = buildHttpClient();
   	      
   	      // Execute the method.
   	      try {
   	    	  HttpResponse httpResponse = httpClient.execute(method);
			  int statusCode = httpResponse.getStatusLine().getStatusCode();
	   	      LOG.info("status code " + statusCode);
			  assertTrue("Episode is reachable", statusCode == HttpStatus.SC_OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}

	   }
	   
	   @Ignore @Test 
	   public void testSomeEpisodeUrls3(){
		   String episodeMediaUrl = "http://feeds.ringier.tv/~r/ges/~5/tnPgFI0GJcQ/simvid_1.mp4";
		   
	        //get a http client
		   HttpHead method = new HttpHead(episodeMediaUrl);
		   CloseableHttpClient httpClient = buildHttpClient();
   	      
   	      // Execute the method.
   	      try {
   	    	  HttpResponse httpResponse = httpClient.execute(method);
			  int statusCode = httpResponse.getStatusLine().getStatusCode();
	   	      LOG.info("status code " + statusCode);
			  assertTrue("Episode is reachable", statusCode == HttpStatus.SC_OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}

	   }   
	   
	   @Ignore @Test 
	   public void testSomeEpisodeUrls4(){
		   String episodeMediaUrl = "http://www.podtrac.com/pts/redirect.mp4/dw.com.com/redir/cnet_2012-03-27-125935.2500.mp4?destUrl=http%3A%2F%2Fcnet.co%2FGRAYbC%3FADPARAMS%3DBRAND%3D47%7CSITE%3D53%7CSP%3D181%7CPOS%3D100%7CNCAT%3D13482%3A13991%3A&ontId=14106&siteId=145&edId=3&asid=50122236&pid=&astId=31&ltype=dl_dlnow";
		   
	        //get a http client
		   HttpHead method = new HttpHead(episodeMediaUrl);
		   CloseableHttpClient httpClient = buildHttpClient();
		   
   	      // Execute the method.
   	      try {
   	    	  HttpResponse httpResponse = httpClient.execute(method);
			  int statusCode = httpResponse.getStatusLine().getStatusCode();
	   	      LOG.info("status code " + statusCode);
			  assertTrue("Episode is reachable", statusCode == HttpStatus.SC_OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Caused by: java.net.URISyntaxException: Illegal character in query at index 39: http://cnet.co/GRAYbC?ADPARAMS=BRAND=47|SITE=53|SP=181|POS=100|NCAT=13482:13991:
		} finally {
			method.releaseConnection();
		}

	   }   		   
}
