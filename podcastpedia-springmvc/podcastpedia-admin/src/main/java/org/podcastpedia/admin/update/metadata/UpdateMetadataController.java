package org.podcastpedia.admin.update.metadata;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.util.forms.PodcastByFeedUrlForm;
import org.podcastpedia.admin.util.read.ReadService;
import org.podcastpedia.admin.util.restclient.RestClient;
import org.podcastpedia.common.controllers.propertyeditors.MediaTypeEditor;
import org.podcastpedia.common.controllers.propertyeditors.UpdateFrequencyTypeEditor;
import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.Tag;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/admin/update/metadata") 
public class UpdateMetadataController {

	protected static Logger LOG = Logger.getLogger(UpdateMetadataController.class);
	
	@Autowired
	private UpdateMetadataService updateMetadataService;
		
	@Autowired
	private ReadService readService;	
	
	@Autowired
	private RestClient restClient;
	
	@Autowired
	private ConfigBean configBean;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {	   
	  binder.registerCustomEditor(UpdateFrequencyType.class,
	    new UpdateFrequencyTypeEditor(UpdateFrequencyType.class));
	  binder.registerCustomEditor(MediaType.class,
			    new MediaTypeEditor(MediaType.class));
	 }	

	/**
	 * load podcast
	 */
	/** This creates the view for the admin home page */
	@RequestMapping(method = RequestMethod.GET)
	public String preparePodcastSelectionForms(Model model) {

		model.addAttribute("updatePodcastByFeedUrlForm",
				new PodcastByFeedUrlForm());

		return "update_podcast_metadata_def";
	}	

	/** This creates the view for the admin home page */
	@RequestMapping(method = RequestMethod.POST)
	public String postToBiggerForm(
			@ModelAttribute("updatePodcastByFeedUrlForm") PodcastByFeedUrlForm podcastByFeedUrlForm,
			Model model, final RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("updatePodcastOwnMetadataByFeedUrlForm",
				podcastByFeedUrlForm);
		return "redirect:/admin/update/metadata/details";
	}	
	
	/** This creates the form for updating categories and tags 
	 * @throws BusinessException */
	@RequestMapping(value = "details", method=RequestMethod.GET)
	public String prepareUpdateForm(
			    @ModelAttribute("updatePodcastOwnMetadataByFeedUrlForm") PodcastByFeedUrlForm updatePodcastOwnMetadataByFeedUrlForm,
				@ModelAttribute("podcast") Podcast podcast,
				BindingResult bindingResult, 
				ModelMap model
			) throws BusinessException{
		
		String feedUrl = updatePodcastOwnMetadataByFeedUrlForm.getFeedUrl();
		
		//get current data for podcast
		Integer podcastId = readService.getPodcastIdForFeedUrl(feedUrl);
		podcast = readService.getPodcastById(podcastId);
		
		//set category ids
		if(podcast.getCategories() != null && podcast.getCategories().size()>0){
			List<Integer> categoryIDs = new ArrayList<Integer>();
			for(Category category : podcast.getCategories()){
				categoryIDs.add(category.getCategoryId());
			}
			podcast.setCategoryIDs(categoryIDs );
		}
		
		//set the tags string url 
		if(podcast.getTags() != null && podcast.getTags().size() > 0){
			StringBuffer tagsStr = new StringBuffer();
			for(Tag tag : podcast.getTags()){
				tagsStr.append(tag.getName() + ",");
			}
			podcast.setTagsStr(tagsStr.toString().substring(0, tagsStr.length()-1));
		}
		
		model.addAttribute("podcast", podcast);
		model.addAttribute("updateFrequencies", UpdateFrequencyType.values());
		model.addAttribute("mediaTypes", MediaType.values());
		model.addAttribute("languageCodes", LanguageCode.values());
		  
		//we need to be able to select the categories the podcast belongs to 
		model.addAttribute("allCategories", readService.getAllAvailableCategories()); 
			
		return "updateForm_def"; //for the time being this is the home page, it shouldn't be like that 
	}	
	
	@RequestMapping(value = "details", method=RequestMethod.POST)
	public String updateOwnMetadata(@ModelAttribute("podcast") Podcast podcast, BindingResult bindingResult) {

		  if(bindingResult.hasErrors()) {
			  return "updateForm_def";
		  }

		  updateMetadataService.updatePodcastMetadata(podcast);
		  		  		  
		  return "redirect:/admin/update/metadata";
	  }		
		   	  	  	  
}
