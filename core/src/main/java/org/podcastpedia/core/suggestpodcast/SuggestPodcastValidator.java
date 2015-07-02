package org.podcastpedia.core.suggestpodcast;

import org.podcastpedia.core.podcasts.PodcastDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuggestPodcastValidator implements Validator{

    private static final int MAX_KEYWORDS_LENGTH = 350;
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String REGEX_COMMA_SEPARATED_WORDS = "^[-\\w\\s]+(?:,[-\\w\\s]+)*$";

    @Autowired
	private PodcastDao podcastDao;
	
	public boolean supports(Class<?> clazz) {		
		return SuggestedPodcast.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {	
		
		SuggestedPodcast suggestedPodcast = (SuggestedPodcast)target;		

		/* validate name */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.name");	
		
		/* validate identifier */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "identifier", "required.identifier");
		verifyIdentifier(errors, suggestedPodcast);
		
		/* validate feed */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "feedUrl", "required.feedUrl");
		
		/* validate email*/		
		if(!isEmailValid(suggestedPodcast.getEmail())){
			errors.rejectValue("email", "invalid.required.email");
		}

		/* validate suggested keywords */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "suggestedTags", "required.keywords");
        Pattern pattern = Pattern.compile(REGEX_COMMA_SEPARATED_WORDS, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher;
        matcher = pattern.matcher(suggestedPodcast.getSuggestedTags());
		if( suggestedPodcast.getSuggestedTags() != null
                && (!matcher.matches()
                    || suggestedPodcast.getSuggestedTags().length() > MAX_KEYWORDS_LENGTH)) {
			errors.rejectValue("suggestedTags", "invalid.suggestedTags");
		}
		/* validate category */
		if(suggestedPodcast.getCategories() == null){
			errors.rejectValue("categories", "required.category");
		}

        /* validate social fan pages */
        if(isInvalidFacebookFanPageUrl(suggestedPodcast)
                ) {
            errors.rejectValue("facebookPage", "invalid.socialFanPage");
        }
        if(isInvalidTwitterFanPageUrl(suggestedPodcast)
                ) {
            errors.rejectValue("twitterPage", "invalid.socialFanPage");
        }

        if(isInvalidGPlusFanPageUrl(suggestedPodcast)
                ) {
            errors.rejectValue("gplusPage", "invalid.socialFanPage");
        }
	}

    private boolean isInvalidTwitterFanPageUrl(SuggestedPodcast suggestedPodcast) {
        return suggestedPodcast.getTwitterPage()!=null
                && !suggestedPodcast.getTwitterPage().trim().equals("")
                && !(suggestedPodcast.getTwitterPage().startsWith("http://www.twitter.com")
                || suggestedPodcast.getTwitterPage().startsWith("https://www.twitter.com")
                || suggestedPodcast.getTwitterPage().startsWith("https://twitter.com")
                || suggestedPodcast.getTwitterPage().startsWith("http://twitter.com"));
    }

    private boolean isInvalidFacebookFanPageUrl(SuggestedPodcast suggestedPodcast) {
        return suggestedPodcast.getFacebookPage()!=null
                && !suggestedPodcast.getFacebookPage().trim().equals("")
                && !(suggestedPodcast.getFacebookPage().startsWith("http://www.facebook.com")
                    || suggestedPodcast.getFacebookPage().startsWith("https://www.facebook.com")
                    || suggestedPodcast.getFacebookPage().startsWith("http://facebook.com")
                    || suggestedPodcast.getFacebookPage().startsWith("https://facebook.com"));
    }

    private boolean isInvalidGPlusFanPageUrl(SuggestedPodcast suggestedPodcast) {
        return suggestedPodcast.getGplusPage()!=null
                && !suggestedPodcast.getGplusPage().trim().equals("")
                && !(suggestedPodcast.getGplusPage().contains("google.com"));
    }

    private void verifyIdentifier(Errors errors,
			SuggestedPodcast suggestedPodcast) {
		if(suggestedPodcast.getIdentifier() != null && !suggestedPodcast.getIdentifier().trim().isEmpty()){
			Integer podcastId = podcastDao.getPodcastIdForIdentifier(suggestedPodcast.getIdentifier().trim());
			if(podcastId != null){
				errors.rejectValue("identifier", "used.identifier");
			}
		}
	}

	private static boolean isEmailValid(String email){	
		
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}
}
