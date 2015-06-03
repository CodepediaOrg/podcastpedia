package org.podcastpedia.common.types;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds codes for application specific errors (mostly business errors)
 * @author adrian
 *
 */
public enum ErrorCodeType {
	
    /** podcast was not found */
	PODCAST_NOT_FOUND(1001),
	
	/** episode was not found in the database */
	EPISODE_NOT_FOUND_ERROR_CODE(1002),

	/** episode not reachable, but present in archive */ 
	EPISODE_FOUND_IN_ARCHIVE_ERROR_CODE(1003),
	
	/** */
	EPISODE_NOT_FOUND_BUT_PODCAST_AVAILABLE(1004);
   
	private int code;
	
	private ErrorCodeType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	private static final Map<Integer,ErrorCodeType> lookup 
    = new HashMap<Integer,ErrorCodeType>();

	static {
	    for(ErrorCodeType s : EnumSet.allOf(ErrorCodeType.class))
	         lookup.put(s.getCode(), s);
	}	
	
    public static ErrorCodeType get(int code) { 
        return lookup.get(code); 
   }
}
