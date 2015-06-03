package org.podcastpedia.common.types;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum that lists order by options when searching for podcasts/episodes. 
 * @author adrian
 *
 */
public enum OrderByOption {
	
	/** Podcasts/episodes that have been recently updated (order by publication_date desc) */
    PUBLICATION_DATE("PUBLICATION_DATE"),
    
	/** Most podcasts/episodes it is based on rating, fb likes, subscriptions */ 
    POPULARITY("POPULARITY"),
    
//	/** Podcasts/episodes with best rating */
//    top_rated("top_rated")    
    
	/** new podcasts added to the database / for episodes is the same as last_updated */
	NEW_ENTRIES("NEW_ENTRIES");
	      
	private String code;
	
	private OrderByOption(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	private static final Map<String,OrderByOption> lookup 
    = new HashMap<String, OrderByOption>();

	static {
	    for(OrderByOption s : EnumSet.allOf(OrderByOption.class))
	         lookup.put(s.getCode(), s);
	}	
	
    public static OrderByOption get(String code) { 
        return lookup.get(code); 
   }
}
