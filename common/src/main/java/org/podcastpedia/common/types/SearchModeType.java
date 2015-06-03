package org.podcastpedia.common.types;

public enum SearchModeType {
	
	/** search in natural mode */
    NATURAL_MODE("natural"),      
   
    /** search in boolean mode */
    BOOLEAN_MODE("boolean");
   
    /** The target type string. */
    private String value;
   
    /**
     * Instantiates a new search target type .
     *
     * @param value the value
     */
    SearchModeType(String value) {
            this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
         return value;
    }

	public void setValue(String value) {
		this.value = value;
	} 
    
    
}
