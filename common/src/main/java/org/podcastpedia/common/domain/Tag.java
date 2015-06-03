package org.podcastpedia.common.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;

@IgnoreSizeOf
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tag implements Serializable{
	
	private static final long serialVersionUID = -2370292880165225805L;

	/** id of the tag - BIGINT in MySQL DB */
	private long tagId;
	
	/** name of the tag */
	private String name;

	/** number of podcasts the tag is used in */ 
	private Integer nrOfPodcasts;
		
	public Integer getNrOfPodcasts() {
		return nrOfPodcasts;
	}

	public void setNrOfPodcasts(Integer nrOfPodcasts) {
		this.nrOfPodcasts = nrOfPodcasts;
	}

	public long getTagId() {
		return tagId;
	}

	public void setTagId(long tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

}
