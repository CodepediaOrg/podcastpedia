package org.podcastpedia.admin.sitemaps;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.SitemapIndexGenerator;
import com.redfin.sitemapgenerator.SitemapIndexUrl;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

public class SitemapServiceImpl implements SitemapService {
	
	@Autowired
	private ConfigBean configBean;
	
	@Autowired
	private ReadDao readDao; 

	/**
	 * Creates sitemap for podcasts/episodes with update frequency 
	 *  
	 * @param  updateFrequency  update frequency of the podcasts
	 * @param  sitemapsDirectoryPath the location where the sitemap will be generated 
	 */
	public void createSitemapForPodcastsWithFrequency(
			UpdateFrequencyType updateFrequency, String sitemapsDirectoryPath)  throws MalformedURLException {
		
		//number of URLs counted 
		int nrOfURLs = 0;
		
		File targetDirectory = new File(sitemapsDirectoryPath);	
		int fileCounter = 0;
		WebSitemapGenerator wsg = WebSitemapGenerator.builder("http://www.podcastpedia.org", targetDirectory)
									.fileNamePrefix("sitemap_" + updateFrequency.toString()) // name of the generated sitemap 
									.gzip(true) //recommended - as it decreases the file's size significantly 
									.build();

		//reads reachable podcasts with episodes from Database with   
		List<Podcast> podcasts = readDao.getPodcastsAndEpisodeWithUpdateFrequency(updateFrequency);
		
		for(Podcast podcast : podcasts) {
			String url;
			WebSitemapUrl wsmUrl; 
			
			if(podcast.getIdentifier()!=null){
				url= "http://www.podcastpedia.org/" + podcast.getIdentifier();
				wsmUrl = new WebSitemapUrl.Options(url)
								.lastMod(podcast.getPublicationDate()) // date of the last published episode
								.priority(0.9) //high priority just below the start page which has a default priority of 1 by default
								.changeFreq(changeFrequencyFromUpdateFrequency(updateFrequency))
								.build();
				wsg.addUrl(wsmUrl);
				nrOfURLs++;				
			} else {
				url = "http://www.podcastpedia.org" + "/podcasts/" + podcast.getPodcastId() + "/" + podcast.getTitleInUrl();
				wsmUrl = new WebSitemapUrl.Options(url)
			     							.lastMod(podcast.getPublicationDate()) // date of the last published episode
			     							.priority(0.9) //high priority just below the start page which has a default priority of 1 by default
			     							.changeFreq(changeFrequencyFromUpdateFrequency(updateFrequency))
			     							.build();			
				wsg.addUrl(wsmUrl);
				nrOfURLs++;				
			}
			

			
			for(Episode episode : podcast.getEpisodes() ){
				url = "http://www.podcastpedia.org" + "/podcasts/" + podcast.getPodcastId() + "/" + podcast.getTitleInUrl()
						+ "/episodes/" + episode.getEpisodeId() + "/" + episode.getTitleInUrl();

				//build websitemap url 
				wsmUrl = new WebSitemapUrl.Options(url)
			     				.lastMod(episode.getPublicationDate()) //publication date of the episode 
			     				.priority(0.8) //high priority but smaller than podcast priority 
			     				.changeFreq(changeFrequencyFromUpdateFrequency(UpdateFrequencyType.TERMINATED)) // 
			     				.build();
				wsg.addUrl(wsmUrl);
				nrOfURLs++;
			}
			
			//after we are through with one podcast we test if we have passed the limit
			if(nrOfURLs <= 30000){
				continue;
			} else {
				wsg.write();
				nrOfURLs = 0; 
				fileCounter++;
				wsg = WebSitemapGenerator.builder("http://www.podcastpedia.org", targetDirectory)
						.fileNamePrefix("sitemap_" + updateFrequency.toString()+"_" + fileCounter) // name of the generated sitemap 
						.gzip(true) //recommended - as it decreases the file's size significantly 
						.build();
				
			}
		}
		
		// One sitemap can contain a maximum of 50,000 URLs.
		if(nrOfURLs > 0){
			wsg.write();			
		} 
	}

	/**
	 * Creates a sitemap index from all the files from the specified directory
	 * excluding files generated in JUnit tests and sitemap_index.xml files
	 * 
	 * @param  sitemapsDirectoryPath the location where the sitemap index will be generated 
	 */
	public void createSitemapIndexFile(String sitemapsDirectoryPath) throws MalformedURLException {
		
		File targetDirectory = new File(sitemapsDirectoryPath);
		// generate sitemap index for foo + bar grgrg 
		File outFile = new File(sitemapsDirectoryPath + "/sitemap_index.xml");
		SitemapIndexGenerator sig = new SitemapIndexGenerator("http://www.podcastpedia.org", outFile);

		//get all the files from the specified directory
		File[] files = targetDirectory.listFiles();
		for(int i=0; i < files.length; i++){
			if(isNotSitemapIndexFile(files, i)){
				SitemapIndexUrl sitemapIndexUrl = new SitemapIndexUrl("http://www.podcastpedia.org/" + files[i].getName(),
																		new Date(files[i].lastModified()));
				sig.addUrl(sitemapIndexUrl);									
			}

		}
		sig.write();	
	}

	private boolean isNotSitemapIndexFile(File[] files, int i) {
		return !files[i].getName().equals("sitemap_index.xml");
	}
	
	/**
	 * Maps podcast update frequency to ChangeFreq  
	 * 
	 * @param updateFrequency update frequency of the podcasts
	 * @return 
	 */
	private ChangeFreq changeFrequencyFromUpdateFrequency(UpdateFrequencyType updateFrequency) {
		
		ChangeFreq response = null;
		
		if(updateFrequency == UpdateFrequencyType.DAILY)  response = ChangeFreq.DAILY;
	    else if(updateFrequency == UpdateFrequencyType.WEEKLY) response = ChangeFreq.WEEKLY;
	    else if(updateFrequency == UpdateFrequencyType.MONTHLY) response = ChangeFreq.MONTHLY;
	    else if(updateFrequency == UpdateFrequencyType.YEARLY) response = ChangeFreq.YEARLY;
	    else if(updateFrequency == UpdateFrequencyType.TERMINATED) response = ChangeFreq.NEVER;
	    else if(updateFrequency == UpdateFrequencyType.UNKNOWN) response = ChangeFreq.MONTHLY;
	    else response = ChangeFreq.MONTHLY;
		
		return response;
			
	}	

}
