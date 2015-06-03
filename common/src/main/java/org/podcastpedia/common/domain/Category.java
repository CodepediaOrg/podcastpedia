package org.podcastpedia.common.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Category implements Serializable{

	private static final long serialVersionUID = 219264453988823416L;
	
	protected int categoryId;
	protected String name;
	protected String description;
	protected int parentCategoryId;
	protected String parentCategoryName;
	protected int numberOfPodcasts;
	
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getParentCategoryId() {
		return parentCategoryId;
	}
	public void setParentCategoryId(int parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	public String getParentCategoryName() {
		return parentCategoryName;
	}
	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
	public int getNumberOfPodcasts() {
		return numberOfPodcasts;
	}
	public void setNumberOfPodcasts(int numberOfPodcasts) {
		this.numberOfPodcasts = numberOfPodcasts;
	} 
	
	
	
}
