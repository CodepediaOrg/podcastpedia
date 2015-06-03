package org.podcastpedia.common.types;

import java.util.HashMap;
import java.util.Map;

/**
 * contains the subject topics a visitor might contact podcastmania for...
 * 
 * @author amasia
 *
 */
public enum ContactTopicType {
	
	GENERAL_QUESTION("general_question"),
	COPYRIGHT_PROBLEM("copyright_problem"),
	ERROR_INDICATION("error_indication"),
	ADVICE("advice");
	
	private final String name;
	//constructor
	private ContactTopicType(String name) {
		this.name = name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
	// Implementing a fromString method on an enum type
	private static final Map<String, ContactTopicType> stringToEnum
		= new HashMap<String, ContactTopicType>();
	
	static { // Initialize map from constant name to enum constant
		for (ContactTopicType op : values())
			stringToEnum.put(op.toString(), op);
	}
	// Returns Operation for string, or null if string is invalid
	public static ContactTopicType fromString(String symbol) {
		return stringToEnum.get(symbol);
	}
	
}
