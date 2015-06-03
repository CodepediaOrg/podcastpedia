package org.podcastpedia.admin.util.read;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.common.types.ErrorCodeType;
import org.springframework.beans.factory.annotation.Autowired;


public class ReadServiceImpl implements ReadService {
	
	private static Logger LOG = Logger.getLogger(ReadServiceImpl.class);
	
	@Autowired
	ReadDao readDao;
	
	public List<Category> getAllAvailableCategories() {
		List<Category> response = readDao.getAllCategories();
		
		return response; 
	}

	public List<Podcast> getPodcastsFromRange(Integer startRow, Integer endRow) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("startRow", startRow);
		params.put("endRow", endRow);
		List<Podcast> response = readDao.getPodcastsFromRange(params);
		
		return response;
	}

	public Integer getNumberOfPodcasts() {
		return readDao.getNumberOfPodcasts();
	}

	public Integer getNumberOfPodcastsWithUpdateFrequency(Integer podcastsUpdateFrequencyCode) {
	
		return readDao.getNumberOfPodcastsWithUpdateFrequency(podcastsUpdateFrequencyCode);
	}

	public Integer getPodcastIdForFeedUrl(String feedUrl) {
		return readDao.getPodcastIdForFeedUrl(feedUrl);
	}

	/**
	 * TODO duplicated code with main project 
	 */
	public Podcast getPodcastByFeedUrl(String feedUrl) throws BusinessException {
		   LOG.debug("executing getPodcastById");
		   Podcast response = readDao.getPodcastByURL(feedUrl);

		   //TODO - for performance reasons maybe remove this one and just display friendly error message ???? 
		   if(response == null){
			   BusinessException ex = new BusinessException(ErrorCodeType.PODCAST_NOT_FOUND, "Podcast was not found in the database");
			   throw ex;
		   }
		   
		   
//		   //generate here the feed just to see some attributes TODO - to be removed 
//			URL theURL;
//			try {
//				theURL = new URL(response.getUrl());
//				XmlReader reader = null;		
//				reader = new XmlReader(theURL);
//				response.setPodcastFeed(new SyndFeedInput().build(reader));
//				
//			} catch (MalformedURLException e) { 
//				// TODO Auto-generated catch block
//				e.printStackTrace();			
//			}
//			catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (FeedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//TODO - end of removal code 
			
		   return response; 
	}

	public Podcast getPodcastById(int podcastId) throws BusinessException {
		return readDao.getPodcastById(podcastId);
	}

	public Podcast getPodcastAttributesByFeedUrl(String feedUrl) {
		return readDao.getPodcastAttributesByFeedUrl(feedUrl);
	}

}
