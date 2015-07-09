package org.podcastpedia.admin.insert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.podcastpedia.admin.dao.InsertDao;
import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.admin.util.PodcastAndEpisodeAttributesService;
import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.PodcastCategory;
import org.podcastpedia.common.domain.PodcastTag;
import org.podcastpedia.common.domain.Tag;
import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.springframework.beans.factory.annotation.Autowired;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;


public class InsertServiceImpl implements InsertService{

	private static final int TIMEOUT_SECONDS = 10;

	private static Logger LOG = Logger.getLogger(InsertServiceImpl.class);
	   
	@Autowired
	private ReadDao readDao; 
	
	@Autowired 
	private InsertDao insertDao;
	
	@Autowired
	private PodcastAndEpisodeAttributesService podcastAndEpisodeAttributesService;	
	
	@Autowired
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;     
	
	/**
	 * Adds podcast to the database 
	 * @throws IOException 
	 */
   	// @Transactional(rollbackFor={Exception.class, IOException.class, FeedException.class}) TODO uncomment when migrating to MySql 5.6 so that transactions are supported 
	public int addPodcast(Podcast podcast) throws Exception{
	   
		LOG.debug("invoked" + getClass().getSimpleName() + "." 
				+ Thread.currentThread().getStackTrace()[1].getMethodName() + "to add new podcast to database ");
	
		podcast.setAvailability(HttpStatus.SC_OK);
		
		//extend with attributes right from the insertion in the database 
		try {
		    //first set etag and last-modified from the http header for this podcast - will spare effort by refresh/update if they did not modify 
		    this.setHeaderFieldAttributes(podcast); 
		    
			//set other attributes from the feed (like title, description, imageUrl etc.)
			podcastAndEpisodeAttributesService.setPodcastFeedAttributes(podcast, false);
			
			//podcastId is automatically generatd by mybatis 
			insertPodcast(podcast);
			int podcastId = podcast.getPodcastId();
						
			insertPodcastCategories(podcast);
			insertTags(podcast);			
			insertEpisodesForPodcast(podcast);
							
			return podcastId; 
			
		} catch(IOException e){
			LOG.error(" IOException has been thrown for podcast " + podcast.getUrl(), e);		
			throw e; 
			
		} catch (FeedException e) {
			LOG.error(" FeedException has been thrown when setting attributes for podcast " + podcast.getUrl(), e);
			throw e; 
			
		} catch (Exception e) {
			LOG.error("Unknown exception has occured for podcast " + podcast.getUrl(), e);
			throw e;
		}
		
	}

	/**
	 * Inserts tags associated with the podcast in the database
	 * 
	 * @param podcast
	 */
	private void insertTags(Podcast podcast) {
		
		/* words/group of words are separated by commas - we'll split that and eliminate the spaces before and after them */
		String [] tags = podcast.getTagsStr().trim().split("\\s*,\\s*");		
 
		Set<String> uniqueTags = new LinkedHashSet<String>(Arrays.asList(tags));
		for(String tagName : uniqueTags){
			//tags should be case sensitive . e.g. in German you say Computer and in English computer -but trimmed should be; They are trimmed also by insertion
			Tag tag = readDao.getTagByName(tagName.trim());
			if(null != tag) {
				insertDao.insertPodcastTag(new PodcastTag(podcast.getPodcastId(), tag.getTagId()));
			} else {
				tag = new Tag();
				//make a trim on the tags to insert, to read after that properly
				tag.setName(tagName.trim());
				insertDao.insertTag(tag);
				insertDao.insertPodcastTag(new PodcastTag(podcast.getPodcastId(), tag.getTagId()));
			}
		}
	}

	private void insertPodcast(Podcast podcast) {
		java.util.Date now = new java.util.Date();			
		podcast.setLastUpdate( now );
		podcast.setInsertionDate( now );			
		insertDao.addPodcast(podcast);
	}

	private void insertPodcastCategories(Podcast podcast) {
		PodcastCategory podcastCategory = new PodcastCategory();
		podcastCategory.setPodcastId(podcast.getPodcastId());
		for(Integer i : podcast.getCategoryIDs()){
			podcastCategory.setCategoryId(i); 
			insertDao.insertPodcastCategory(podcastCategory); 
		}
	}	
	
	/**
	 * Sets the header fields attributes "etag" and "last-modified" (if necessary) for a given podcast 
	 *    
	 * @param podcast
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws DateParseException 
	 * @throws Exception 
	 */
	private void setHeaderFieldAttributes(Podcast podcast) throws ClientProtocolException, IOException{
   	    
		HttpHead headMethod = null;					
		headMethod = new HttpHead(podcast.getUrl());
		
	    RequestConfig requestConfig = RequestConfig.custom()
	            .setSocketTimeout(TIMEOUT_SECONDS * 1000)
	            .setConnectTimeout(TIMEOUT_SECONDS * 1000)
	            .build();		
		
	    CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingHttpClientConnectionManager)
                .build();

		HttpResponse httpResponse = httpClient.execute(headMethod);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
      
		if (statusCode != HttpStatus.SC_OK) {
			LOG.error("The introduced URL is not valid " + podcast.getUrl()  + " : " + statusCode);
		}
      
		//set the new etag if existent
		org.apache.http.Header eTagHeader = httpResponse.getLastHeader("etag");
		if(eTagHeader != null){
			podcast.setEtagHeaderField(eTagHeader.getValue());
		}
      
		//set the new "last modified" header field if existent 
		org.apache.http.Header lastModifiedHeader= httpResponse.getLastHeader("last-modified");
		if(lastModifiedHeader != null) {
			podcast.setLastModifiedHeaderField(DateUtils.parseDate(lastModifiedHeader.getValue()));
			podcast.setLastModifiedHeaderFieldStr(lastModifiedHeader.getValue());
		}	   	      	   	      	   	        	         	      

   	    // Release the connection.
    	headMethod.releaseConnection();	   	       	  		
	}
		
   	/**
   	 * Inserts the episodes of a given podcast in the database
   	 * 
   	 * @param podcast
   	 */
	private void insertEpisodesForPodcast(Podcast podcast){
		
		int i=0; 
		if(null!=podcast.getPodcastFeed()){
			for(SyndEntry entry: (List<SyndEntry>)podcast.getPodcastFeed().getEntries()){
				
				Episode episode = new Episode();
				episode.setPodcastId(podcast.getPodcastId());
				
				//because there is an insertion operation we set them in order they come from the entry list 
				episode.setEpisodeId(i);
				//set attribues of the episode 
				podcastAndEpisodeAttributesService.setEpisodeAttributes(episode, podcast, entry);															
				i++;
				
				//at the beginning all the episodes are marked as available (200)
				episode.setAvailability(org.apache.http.HttpStatus.SC_OK);
				
				//insert the episode in the database
				try {
					//TODO when I move to InnoDB for table and lucene search, rollback the transaction with log message when updating the podcast
					//we add only the episodes that have a media file attached to them 
					boolean episodeMediaCouldBeSet = !episode.getMediaUrl().equals("noMediaUrl");
					if(episodeMediaCouldBeSet){
						insertDao.insertEpisode(episode);
						LOG.info("PodId[" + podcast.getPodcastId().toString() + "] - " + "INSERT EPISODE epId[" + episode.getEpisodeId()
								+ "] - epURL " + episode.getMediaUrl());							
					}
					
				} catch (Exception e) {
					LOG.error("ERROR inserting episode " + episode.getMediaUrl() + " for podcastId[" + episode.getPodcastId() + "]", e);
					continue; //do not mark it as new episode 
				}				
			
			}				
		}		
	}

	public ProposedPodcast getPodcastFromStringLine(String stringLine) throws Exception {
		ProposedPodcast response = new ProposedPodcast();
		Podcast podcast = new Podcast();
		String[] split = stringLine.trim().split(";");
		//set podcast's url
		podcast.setUrl(split[0].trim());
		
		//set podcast's identifier
		podcast.setIdentifier(split[1].trim());
		//set category id based on names
		List<Category> allCategories = readDao.getAllCategories();
		HashMap<String, Integer> categoryIdsForNames = new HashMap<String, Integer>();		
		for(Category c : allCategories){
			categoryIdsForNames.put(c.getName(), c.getCategoryId());
		}		
		
		String categoriesStr = split[2].trim();		

		String[] categoryNames = categoriesStr.split(",");
		List<Integer> podcastCategoryIDs = new ArrayList<Integer>();
		for (String categoryName : categoryNames) {
			Integer categoryIdForName = categoryIdsForNames.get(categoryName.trim());
			if (categoryIdForName == null) {
				throw new Exception("Category name " + categoryName.trim() + "  is wrong");
			} else {
				podcastCategoryIDs.add(categoryIdForName);
			}
		}

		podcast.setCategoryIDs(podcastCategoryIDs);				
		podcast.setLanguageCode(LanguageCode.get(split[3].trim()));
		podcast.setMediaType(MediaType.valueOf(split[4].trim())); 
		podcast.setUpdateFrequency(UpdateFrequencyType.valueOf(split[5].trim()));
		//set podcast's tags
		podcast.setTagsStr(split[6].trim());
		podcast.setFbPage(split[7].trim().isEmpty()?null:split[7].trim());
		podcast.setTwitterPage(split[8].trim().isEmpty()?null:split[8].trim());
		podcast.setGplusPage(split[9].trim().isEmpty()?null:split[9].trim());
		response.setPodcast(podcast);
		
		response.setName(split[10].trim().isEmpty()?null:split[10].trim());
		response.setEmail(split[11].trim().isEmpty()?null:split[11].trim());
		
		return response; 
	}
			
}
