package org.podcastpedia.web.startpage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.util.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;


/**
 * Controller class that maps to feeds service generation, for both the start page feeds, and search results generated
 * feeds.  
 * 
 * @author amasia
 *
 */
@Controller
@RequestMapping("/feeds")
public class StartPageFeedsController implements MessageSourceAware {
	
	private static String[] preferredLanguages = {"en", "fr", "de" };
	
	private MessageSource messageSource;
	
	@Autowired
	private ConfigService configService;
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	private static final Integer NUMBER_OF_PODCASTS_IN_CHART = 5;
	
	@Autowired
	private StartPageService startPageService;// this has to be changed in the interface 
	
	/**
	 * Returns top rated list of podcasts to be generated as an atom feed.  
	 * Request comes from start page. 
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
    @RequestMapping("most_popular.atom")
    public String getTopRatedPodcastsAtomFeed(WebRequest request, Model model) {
    	Locale locale = LocaleContextHolder.getLocale();
        model.addAttribute("list_of_podcasts", getTopRatedPodcastsForLocale(locale));
        model.addAttribute("feed_id", "tag:podcastpedia.org,2013-04-30:most_popular");
        model.addAttribute("feed_title", messageSource.getMessage("podcasts.most_popular.feed_title", null, locale));
        model.addAttribute("feed_description", messageSource.getMessage("podcasts.most_popular.feed_description", null, locale));      
        model.addAttribute("feed_link", configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "topRatedPodcastsAtomFeedView";
    }
    
    /**
     * Returns a list of top rated podcasts to be generated as an rss feed.
     * Request comes from start page. 
     * 
     * @param model
     * @return
     */
    @RequestMapping("most_popular.rss")
    public String getTopRatedPodcastsRssFeed(Model model) {
    	
    	Locale locale = LocaleContextHolder.getLocale();
        
        model.addAttribute("list_of_podcasts", getTopRatedPodcastsForLocale(locale));
        model.addAttribute("feed_title", messageSource.getMessage("podcasts.most_popular.feed_title", null, locale));
        model.addAttribute("feed_description", messageSource.getMessage("podcasts.most_popular.feed_description", null, locale));
        model.addAttribute("feed_link", configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "topRatedPodcastsRssFeedView";
    }    
    
    private List<Podcast> getTopRatedPodcastsForLocale(Locale locale){

    	String  language = locale.getLanguage();
		List<String> preferredLanguagesList =  Arrays.asList(preferredLanguages);
		
        List<Podcast> topRatedPodcasts;
		if(preferredLanguagesList.contains(language)){        
			topRatedPodcasts = startPageService.getTopRatedPodcastsWithLanguage(LanguageCode.get(language), NUMBER_OF_PODCASTS_IN_CHART);
		} else {
			topRatedPodcasts = startPageService.getTopRatedPodcasts(NUMBER_OF_PODCASTS_IN_CHART);	
		}   
		
		return topRatedPodcasts;
    }
    /**
     * Returns list of recommended podcasts to be generated as a rss feed.
     * Request comes from start page. 
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("recommended.atom")
    public String getRecommendedPodcastsAtomFeed(WebRequest request, Model model) {
        List<Podcast> recommendedList = new ArrayList<Podcast>();
        recommendedList = startPageService.getRecommendedPodcasts();
         
        model.addAttribute("list_of_podcasts", recommendedList);
        model.addAttribute("feed_id", "tag:podcastpedia.org,2013-04-30:recommended");
        model.addAttribute("feed_title", messageSource.getMessage("podcasts.recommended.feed_title", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_description", messageSource.getMessage("podcasts.recommended.feed_description", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_link", configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "recommendedPodcastsAtomFeedView";
    }
    
    /**
     * Returns list of recommended podcasts to be generated as a rss feed.
     * Request comes from start page. 
     * 
     * @param request
     * @param model
     * @return
     */    
    @RequestMapping("recommended.rss")
    public String getRecommendedPodcastsRssFeed(Model model) {
        List<Podcast> recommendedList = new ArrayList<Podcast>();
        recommendedList = startPageService.getRecommendedPodcasts();
        
        model.addAttribute("list_of_podcasts", recommendedList);
        model.addAttribute("feed_title", messageSource.getMessage("podcasts.recommended.feed_title", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_description", messageSource.getMessage("podcasts.recommended.feed_description", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_link", configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "recommendedPodcastsRssFeedView";
    } 
    
    /**
     * Returns list of newest podcasts to be generated as a atom feed.
     * Request comes from start page. 
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("newest.atom")
    public String getNewestPodcastsAtomFeed(WebRequest request, Model model) {
    	Locale locale = LocaleContextHolder.getLocale();         
        model.addAttribute("list_of_podcasts", getNewestPodcastsForLocale(locale));
        model.addAttribute("feed_id", "tag:podcastpedia.org,2013-04-30:last_updated");
        model.addAttribute("feed_title", messageSource.getMessage("podcasts.newest.feed_title", null, locale));
        model.addAttribute("feed_description", messageSource.getMessage("podcasts.newest.feed_description", null, locale));
        model.addAttribute("feed_link",  configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "newestPodcastsAtomFeedView";
    }
    
    /**
     * Returns list of newest podcasts to be generated as a rss feed.
     * Request comes from start page. 
     * 
     * @param model
     * @return
     */
    @RequestMapping("newest.rss")
    public String getNewestPodcastsRssFeed(Model model) {
    	Locale locale = LocaleContextHolder.getLocale();        
        model.addAttribute("list_of_podcasts", getNewestPodcastsForLocale(locale));
        model.addAttribute("feed_title",  messageSource.getMessage("podcasts.newest.feed_title", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_description",  messageSource.getMessage("podcasts.newest.feed_description", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_link",  configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "newestPodcastsRssFeedView";
    }    
    
    private List<Podcast> getNewestPodcastsForLocale(Locale locale){

    	String  language = locale.getLanguage();
		List<String> preferredLanguagesList =  Arrays.asList(preferredLanguages);
		
        List<Podcast> lastUpdatedPodcasts;
		if(preferredLanguagesList.contains(language)){        
			lastUpdatedPodcasts = startPageService.getLastUpdatedPodcasts(LanguageCode.get(language));
		} else {
			lastUpdatedPodcasts = startPageService.getLastUpdatedPodcasts();	
		}   
		
		return lastUpdatedPodcasts;
    }    
    /**
     * Returns list of random podcasts to be generated as a atom feed.
     * Request comes from start page. 
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("random.atom")
    public String getRandomPodcastsAtomFeed(WebRequest request, Model model) {
        List<Podcast> randomPodcasts = new ArrayList<Podcast>();
        randomPodcasts = startPageService.getRandomPodcasts(NUMBER_OF_PODCASTS_IN_CHART);
         
        model.addAttribute("list_of_podcasts", randomPodcasts);
        model.addAttribute("feed_id", "tag:podcastpedia.org,2013-04-30:random_podcasts");
        model.addAttribute("feed_title", messageSource.getMessage("podcasts.random.feed_title", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_description", messageSource.getMessage("podcasts.random.feed_description", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_link",  configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "randomPodcastsAtomFeedView";
    }
    
    /**
     * Returns list of random podcasts to be generated as a rss feed.
     * Request comes from start page. 
     * 
     * @param model
     * @return
     */
    @RequestMapping("random.rss")
    public String getRandomPodcastsRssFeed(Model model) {
        List<Podcast> randomPodcasts = new ArrayList<Podcast>();
        randomPodcasts = startPageService.getRandomPodcasts(NUMBER_OF_PODCASTS_IN_CHART);
        
        model.addAttribute("list_of_podcasts", randomPodcasts);
        model.addAttribute("feed_title",  messageSource.getMessage("podcasts.random.feed_title", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_description",  messageSource.getMessage("podcasts.random.feed_description", null, LocaleContextHolder.getLocale()));
        model.addAttribute("feed_link",  configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "randomPodcastsRssFeedView";
    }        
        
    /**
     * Returns list of newest podcasts to be generated as a atom feed.
     * Request comes from start page. 
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("new-entries.atom")
    public String getNewEntriesAtomFeed(WebRequest request, Model model) {
    	Locale locale = LocaleContextHolder.getLocale();         
        model.addAttribute("list_of_podcasts", startPageService.getNewEntries());
        model.addAttribute("feed_id", "tag:podcastpedia.org,2013-04-30:last_updated");
        model.addAttribute("feed_title", messageSource.getMessage("podcasts.new_entries.feed_title", null, locale));
        model.addAttribute("feed_description", messageSource.getMessage("podcasts.new_entries.feed_description", null, locale));
        model.addAttribute("feed_link",  configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "newEntriesPodcastsAtomFeedView";
    }
    
    /**
     * Returns list of newest podcasts to be generated as a rss feed.
     * Request comes from start page. 
     * 
     * @param model
     * @return
     */
    @RequestMapping("new-entries.rss")
    public String getNewEntriesRssFeed(Model model) {
    	Locale locale = LocaleContextHolder.getLocale();        
        model.addAttribute("list_of_podcasts", startPageService.getNewEntries());
        model.addAttribute("feed_title",  messageSource.getMessage("podcasts.new_entries.feed_title", null, locale));
        model.addAttribute("feed_description",  messageSource.getMessage("podcasts.new_entries.feed_description", null, locale));
        model.addAttribute("feed_link",  configService.getValue("HOST_AND_PORT_URL"));
        model.addAttribute("HOST_AND_PORT_URL", configService.getValue("HOST_AND_PORT_URL"));
        
        return "newEntriesPodcastsRssFeedView";
    }   
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public void setStartPageService(StartPageService startPageService) {
		this.startPageService = startPageService;
	}
}
