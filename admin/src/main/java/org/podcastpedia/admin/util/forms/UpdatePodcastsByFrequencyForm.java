package org.podcastpedia.admin.util.forms;

import org.podcastpedia.common.types.UpdateFrequencyType;

public class UpdatePodcastsByFrequencyForm {
	
	/** number of working threads that work on the update*/
	private Integer numberOfWorkingThreads;
	
	/** update frequency to be considered when selecting podcasts to actualize */
	private UpdateFrequencyType updateFrequency;

	public Integer getNumberOfWorkingThreads() {
		return numberOfWorkingThreads;
	}

	public void setNumberOfWorkingThreads(Integer numberOfWorkingThreads) {
		this.numberOfWorkingThreads = numberOfWorkingThreads;
	}

	public UpdateFrequencyType getUpdateFrequency() {
		return updateFrequency;
	}

	public void setUpdateFrequency(UpdateFrequencyType updateFrequency) {
		this.updateFrequency = updateFrequency;
	}	

}
