package org.podcastpedia.common.controllers.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.podcastpedia.common.types.MediaType;


@SuppressWarnings("unchecked")
public class MediaTypeEditor extends PropertyEditorSupport{
	
	 private Class clazz;
	 
	 public MediaTypeEditor(Class clazz) {
	  this.clazz = clazz;
	 };
	 
	 public String getAsText() {
	  return (getValue() == null ? "" : ((Enum<MediaType>) getValue()).toString());
	 }
	 
	 public void setAsText(String text) throws IllegalArgumentException {
		 setValue(Enum.valueOf(clazz, text));
	 }
}
