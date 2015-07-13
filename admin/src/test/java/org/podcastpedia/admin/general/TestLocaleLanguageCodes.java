package org.podcastpedia.admin.general;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.podcastpedia.common.types.LanguageCode;


public class TestLocaleLanguageCodes {
	
	private static Logger LOG = Logger.getLogger(TestLocaleLanguageCodes.class);
	
	@Test
	public void testGetLanguageCodesFromLocale(){
		
		String[] countryCodes = Locale.getISOCountries();
		LOG.debug("Country codes : " + countryCodes.toString());
		Assert.assertTrue("We've got some country codes", countryCodes.length > 0);
		
		String[] isoLanguages = Locale.getISOLanguages();
		LOG.debug("Iso language codes : " + isoLanguages.toString());
		Assert.assertTrue("We've got some language codes", isoLanguages.length > 0);		
	}
	
	@Test
	public void testEnumGetMethod(){
		LanguageCode languageCode = LanguageCode.get("de");
		Assert.assertTrue("verify the code", languageCode.getCode().equals("de"));
	}
}
