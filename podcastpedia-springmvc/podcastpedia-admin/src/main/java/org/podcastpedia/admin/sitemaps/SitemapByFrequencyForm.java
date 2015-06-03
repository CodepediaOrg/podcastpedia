package org.podcastpedia.admin.sitemaps;

import org.podcastpedia.common.types.UpdateFrequencyType;

public class SitemapByFrequencyForm {
	
	/** update frequency to be considered when selecting podcasts to actualize */
	private UpdateFrequencyType updateFrequency;

	public UpdateFrequencyType getUpdateFrequency() {
		return updateFrequency;
	}

	public void setUpdateFrequency(UpdateFrequencyType updateFrequency) {
		this.updateFrequency = updateFrequency;
	}	

}
