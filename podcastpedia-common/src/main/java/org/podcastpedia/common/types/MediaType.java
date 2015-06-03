package org.podcastpedia.common.types;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum that lists possible types for podcasts and episodes.
 * @author adrian
 *
 */
public enum MediaType {

    /** Audio */
    Audio(1),
    
	/** Video */
    Video(2),      
   
	/** VideoHD */
    VideoHD(3);
   
	private int code;
	
	private MediaType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	private static final Map<Integer,MediaType> lookup 
    = new HashMap<Integer,MediaType>();

	static {
	    for(MediaType s : EnumSet.allOf(MediaType.class))
	         lookup.put(s.getCode(), s);
	}	
	
    public static MediaType get(int code) { 
        return lookup.get(code); 
   }
}
