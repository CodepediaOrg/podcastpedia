package org.podcastpedia.common.types;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum that lists possible types for podcasts and episodes.
 * @author adrian
 *
 */
public enum HttpStatusExtensionType {

    /** Socket timeout exception */
    SOCKET_TIMEOUT_EXCEPTION(1),
    
	/** IO_EXCEPTION */
    IO_EXCEPTION(2),      
   
	/** VideoHD */
    ILLEGAL_ARGUMENT_EXCEPTION(3),
    
    /** general exception */
    EXCEPTION(4),
    
    /** basically if httpStatus is OK (200) and not SC_NOT_MODIFIED = 304, we consider it as modified */
    URL_CONTENT_MODIFIED(5),
    
    /** a more generic status code for podcast in error - this is serious and needs to be investigated */
    PODCAST_IN_ERROR(6),
    
    /** date parse exception */
    DATE_PARSE_EXCEPTION(7),
    
    /** this will be set for episodes that don't have a media attached to them (like audio or video)*/
    NO_MEDIA_URL(8);
   
	private int code;
	
	private HttpStatusExtensionType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	private static final Map<Integer,HttpStatusExtensionType> lookup 
    = new HashMap<Integer,HttpStatusExtensionType>();

	static {
	    for(HttpStatusExtensionType s : EnumSet.allOf(HttpStatusExtensionType.class))
	         lookup.put(s.getCode(), s);
	}	
	
    public static HttpStatusExtensionType get(int code) { 
        return lookup.get(code); 
   }
}
