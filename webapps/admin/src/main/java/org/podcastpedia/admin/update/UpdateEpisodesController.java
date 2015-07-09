package org.podcastpedia.admin.update;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.util.forms.PodcastByFeedUrlForm;
import org.podcastpedia.admin.util.forms.PodcastByIdForm;
import org.podcastpedia.admin.util.read.ReadService;
import org.podcastpedia.admin.util.restclient.RestClient;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rometools.rome.io.FeedException;

@Controller
@RequestMapping("/admin/update/episodes") 
public class UpdateEpisodesController {

	protected static Logger LOG = Logger.getLogger(UpdateEpisodesController.class);
	
	@Autowired
	private UpdateService updateService;
	
	@Autowired
	private ReadService readService;	
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
	private ConfigBean configBean;

	@RequestMapping(method = RequestMethod.GET)
	public String prepareForms(Model model) {

		model.addAttribute("updatePodcastByIdForm", new PodcastByIdForm());
		model.addAttribute("updatePodcastByFeedUrlForm",
				new PodcastByFeedUrlForm());

		return "individual_podcast_episodes_update_def";
	}
	
	  /**
	   * Given the podcast id the episodes of the podcast will be updated 
	   * 
	   * @param updatePodcastByIdForm
	   * @param bindingResult
	   * @param model
	   * @return
	   * @throws IllegalArgumentException
	   * @throws FeedException
	   * @throws IOException
	   */
	  @RequestMapping(value="podcasts_by_ids", method=RequestMethod.POST)
	  public String updatePodcastsByIds(@ModelAttribute("updatePodcastByIdForm") PodcastByIdForm updatePodcastByIdForm,
			  				BindingResult bindingResult, ModelMap model) throws IllegalArgumentException, FeedException, IOException {
		  
		  if(bindingResult.hasErrors()) {
			  return "redirect:/admin";
		  }
		  
		  LOG.debug("------ updatePodcastById : update and its episodes from DB by podcast's id -----");
		  String[] podcastIds = updatePodcastByIdForm.getPodcastIds().split(",");

		  for (String podcastId : podcastIds) {
			  Podcast podcast = new Podcast();
			  podcast.setPodcastId(Integer.valueOf(podcastId.trim()));
			  long start = System.currentTimeMillis();
			  updateService.updatePodcastById(podcast, true, updatePodcastByIdForm.getIsFeedLoadedFromLocalFile());
			  long elapsedTime = System.currentTimeMillis() - start;
			  LOG.info("It took " + elapsedTime + " miliseconds for podcastid " + podcast.getPodcastId());
		  }
		  		  
		  restClient.invokeRefreshAllCaches();
		  		  
		  return "redirect:/admin/update/episodes";
	  }	  		
	  
	  @RequestMapping(value="podcast_by_feedUrl", method=RequestMethod.POST)
	  public String updatePodcastByFeedUrl(@ModelAttribute("updatePodcastByFeedUrlForm") PodcastByFeedUrlForm updatePodcastByFeedUrlForm,
			  				BindingResult bindingResult, ModelMap model) throws IllegalArgumentException, FeedException, IOException {
		  if(bindingResult.hasErrors()) {
			  return "redirect:/admin";
		  }
		  
		  LOG.debug("------ executing updatePodcastByFeedUrl -----");
		  Integer podcastIdForFeedUrl = readService.getPodcastIdForFeedUrl(updatePodcastByFeedUrlForm.getFeedUrl());
		  Podcast podcast = new Podcast();
		  podcast.setPodcastId(podcastIdForFeedUrl);
		  updateService.updatePodcastById(podcast, true, false);

		  restClient.invokeRefreshAllCaches();	  
		  
		  return "redirect:/admin/update/episodes";
	  }	  
	  	  	 
}
