package org.podcastpedia.web.api;

import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;

/**
 * Registers the components to be used by the JAX-RS application
 * 
 * @author ama
 * 
 */
public class JaxRsApplication extends ResourceConfig {

	/**
	 * Register JAX-RS application components.
	 */
	public JaxRsApplication() {
		
        packages("org.podcastpedia.web.api");

		// register features
		EncodingFilter.enableFor(this, GZipEncoder.class);		
		
	}
}
