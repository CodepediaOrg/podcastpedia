package org.podcastpedia.common.types;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum that lists possible types for podcasts and episodes.
 * @author adrian
 *
 */
public enum LanguageCode {

	/** English */
    en("en"),
    
	/** French */
    fr("fr"),    
    
	/** German */
    de("de"),         
    
	/** Italian */
    it("it"),    
    
    /** Romanian */
    ro("ro"),
    
	/** Spanish */
    es("es"),
    
    /** Portuguese */
    pt("pt");      
    
   
	private String code;
	
	private LanguageCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	private static final Map<String,LanguageCode> lookup 
    = new HashMap<String, LanguageCode>();

	static {
	    for(LanguageCode s : EnumSet.allOf(LanguageCode.class))
	         lookup.put(s.getCode(), s);
	}	
	
    public static LanguageCode get(String code) { 
        return lookup.get(code); 
   }
}
