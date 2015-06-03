package org.podcastpedia.common.util.config;

import org.podcastpedia.common.types.OrderByOption;

/**
 * This class retrieves data from the configuration table 
 * @author adrian
 *
 */
public interface ConfigService {

	/** returns the default order by option from the database */
	public OrderByOption getDefaultOrderByOptionForSearchBar();
	
	/**
	 * Returns the configuration value for the given property 
	 * @param property
	 * @return
	 */
	public String getValue(String property);
}
