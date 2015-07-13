package org.podcastpedia.web.contact;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.util.Utilities;
import org.podcastpedia.common.types.ContactTopicType;
import org.podcastpedia.core.contact.ContactForm;
import org.podcastpedia.core.contact.ContactService;
import org.podcastpedia.core.contact.EmailNotificationService;
import org.podcastpedia.core.searching.SearchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.ServletRequest;
import java.beans.PropertyEditorSupport;
import java.util.List;

/**
 * Controller to route the various links present in footer. This will be static information.
 * 
 * @author amasia
 *
 */
@Controller
@RequestMapping("/contact")
public class ContactController {
	
	protected static Logger LOG = Logger.getLogger(ContactController.class);

	@Autowired 
	private ContactService contactService;
		
	@Autowired
	private ContactFormValidator contactFormValidator;
	
	@Autowired
	private EmailNotificationService emailNotificationService;

    @Autowired
    private ReCaptchaImpl reCaptcha;
	
	/**
	 * Add an empty searchData object to the model 
	 */
	@ModelAttribute
	public void addDataToModel(ModelMap model){
		SearchData dataForSearchBar = new SearchData();
		dataForSearchBar.setSearchMode("natural");
		dataForSearchBar.setCurrentPage(1);
		dataForSearchBar.setQueryText(null);
		dataForSearchBar.setNumberResultsPerPage(10);
		model.put("advancedSearchData", dataForSearchBar);
	}	

	@InitBinder    
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(ContactTopicType.class, new PropertyEditorSupport() {
	        @Override
	        public void setAsText(String value) throws IllegalArgumentException {
	            setValue(ContactTopicType.valueOf(value));
	        }

	        @Override
	        public String getAsText() {
	            if(getValue() == null)
	                return "";

	            return ((ContactTopicType) getValue()).name();
	        }
	    });
		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String prepareContactForm(
			@RequestParam(value="topic", required=false) String topic,
			@RequestParam(value="tks", required=false) String isTks,
			@ModelAttribute("contactForm") ContactForm contactForm,
			Model model){
		LOG.debug("------ prepareContactForm : Received request to show contact form -----");
	
		contactForm = new ContactForm();
		List<String> topics = Utilities.getDisplayValues(ContactTopicType.class);
		model.addAttribute("topics", topics);
		model.addAttribute("contactForm", contactForm);
		
		if(topic!=null){
			contactForm.setTopic(topic);
		}
		
		if(isTks !=null){
        	model.addAttribute("thank_you_message", "thank_you");	
		}				
		return "contact_form_def";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String processContactForm(
				@ModelAttribute("contactForm") ContactForm contactForm,
				BindingResult result, 
				Model model,
                @RequestParam("recaptcha_challenge_field") String challangeField,
                @RequestParam("recaptcha_response_field") String responseField,
                ServletRequest servletRequest, SessionStatus sessionStatus
		){
		
		LOG.debug("------ processContactForm : form is being validated and processed -----");
		contactFormValidator.validate(contactForm, result);

        String remoteAddress = servletRequest.getRemoteAddr();
        ReCaptchaResponse reCaptchaResponse = this.reCaptcha.checkAnswer(
                remoteAddress, challangeField, responseField);

		if(!result.hasErrors() && reCaptchaResponse.isValid()){
			contactService.sendContactMessage(contactForm);
        	emailNotificationService.sendContactNotification(contactForm);
            sessionStatus.setComplete();

        	return "redirect:/contact?tks=true";			
		} else {
			List<String> topics = Utilities.getDisplayValues(ContactTopicType.class);
			model.addAttribute("topics", topics);			
			model.addAttribute("contactForm", contactForm);
            if (!reCaptchaResponse.isValid()) {
                result.rejectValue("invalidRecaptcha", "invalid.captcha");
                model.addAttribute("invalidRecaptcha", true);
            }
    		
			return "contact_form_def";	
		}		
		
	}	
	
}
