package org.podcastpedia.admin.util.restclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;

public class RestClientImpl implements RestClient {

	private static final int TIMEOUT = 10;

	private static Logger LOG = Logger.getLogger(RestClientImpl.class);

	@Autowired
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager; 

	@Autowired
	private ConfigBean configBean;

	public void invokeRefreshReferenceData() {
		invokeRestOperation(configBean.get("HOST_AND_PORT_URL")
				+ "/rest/caching/update/flush_reference_data_cache");
	}

	public void invokeRefreshNewestAndRecommendedPodcasts() {
		invokeRestOperation(configBean.get("HOST_AND_PORT_URL")
				+ "/rest/caching/update/flush_newest_and_recommended_podcasts_cache");
	}

	public void invokeRefreshAllCaches() {
		invokeRestOperation(configBean.get("HOST_AND_PORT_URL")
				+ "/rest/caching/update/flush_all_caches");
	}

	private void invokeRestOperation(String url) {
	    RequestConfig requestConfig = RequestConfig.custom()
	            .setSocketTimeout(TIMEOUT * 1000)
	            .setConnectTimeout(TIMEOUT * 1000)
	            .build();		
		Credentials credentials = new UsernamePasswordCredentials("rest_user",
				"123rest");
		AuthScope authscope = new AuthScope("localhost", 8080);		
	    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	    credentialsProvider.setCredentials(authscope, credentials);
	    
		CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();

		HttpGet httpget = new HttpGet(url);
		
		LOG.debug("executing request " + httpget.getRequestLine());
		try {
			// use following response handler if you want response as string
			// ResponseHandler<String> responseHandler = new
			// BasicResponseHandler();

			HttpResponse response = httpClient.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode != HttpStatus.SC_OK) {
				LOG.error("Method failed: " + statusCode);
			}
			HttpEntity entity = response.getEntity();
			LOG.info(" Response " + EntityUtils.getContentCharSet(entity));

		} catch (ClientProtocolException e) {
			LOG.error(" ClientProtocolException invoking url " + url + " "
					+ e.getMessage());
		} catch (IOException e) {
			LOG.error(" IOException invoking url " + url + " " + e.getMessage());
		} finally {
			httpget.releaseConnection();
		}

	}

	public void invokeFlushSearchResultsCache() {
		invokeRestOperation(configBean.get("HOST_AND_PORT_URL")
				+ "/rest/caching/update/flush_search_results_cache");
	}

}
