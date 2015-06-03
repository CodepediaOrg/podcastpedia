package org.podcastpedia.common.types;

/**
 * Specifies state of feed for podcast
 * 
 * @author adrian
 *
 */
public enum SetHeaderAttributesResultType {

	FEED_NOT_MODIFIED(304),
	FEED_MODIFIED(1), 
	ERROR(-1);

	/** The set header attributes result type value */
	private int value;
	/**
	 * Instantiates a new SetHeaderAttributesResultType
	 * @param value
	 */
	SetHeaderAttributesResultType(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	
}
