package org.podcastpedia.common.types;

public enum SearchTargetType {
	
	/** look in podcasts */
    PODCASTS("podcasts"),      
   
    /** look in episodes */
    EPISODES("episodes");
   
    /** The target type string. */
    private String value;
   
    /**
     * Instantiates a new search target type .
     *
     * @param value the value
     */
    SearchTargetType(String value) {
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
}
