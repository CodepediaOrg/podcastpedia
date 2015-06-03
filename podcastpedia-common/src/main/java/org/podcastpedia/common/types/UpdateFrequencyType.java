package org.podcastpedia.common.types;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum with values for frequency update of the podcasts
 * @author adrian
 *
 */
public enum UpdateFrequencyType {
	
	/** podcast is updated daily*/
	DAILY(1),
	
	/** podcast is updated weekly */
	WEEKLY(2),
	
	/** podcast is updated monthly */
	MONTHLY(3),
	
	/** podcast is updated yearyl */
	YEARLY(4),
	
	/** podcast is most likely through, and it will never be updated - but contents are valuable and stored somewhere */
	TERMINATED(5),
	
	/** the update frequency cannot be deducted or is unknown*/
	UNKNOWN(6);
	
	private int code;
	
	private UpdateFrequencyType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	private static final Map<Integer,UpdateFrequencyType> lookup 
    = new HashMap<Integer,UpdateFrequencyType>();

	static {
	    for(UpdateFrequencyType s : EnumSet.allOf(UpdateFrequencyType.class))
	         lookup.put(s.getCode(), s);
	}	
	
    public static UpdateFrequencyType get(int code) { 
        return lookup.get(code); 
   }
    
}
