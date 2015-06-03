package org.podcastpedia.common.util.config;

/**
 * Interface that simulates a properties file, only the data is persisted in the database
 * and can be dynamically modified. It will be cached eventually. 
 * 
 * @author amacoder
 *
 */
public interface ConfigBean {

	/** Given a <code>property</code> name the return is the value as <code>String</code> */
	public String get(String property);

}
