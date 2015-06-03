package org.podcastpedia.common.controllers.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.podcastpedia.common.types.UpdateFrequencyType;


@SuppressWarnings("unchecked")
public class UpdateFrequencyTypeEditor extends PropertyEditorSupport{
	
	 private Class clazz;
	 
	 public UpdateFrequencyTypeEditor(Class clazz) {
	  this.clazz = clazz;
	 };
	 
	 public String getAsText() {
	  return (getValue() == null ? "" : ((Enum<UpdateFrequencyType>) getValue()).toString());
	 }
	 
	 public void setAsText(String text) throws IllegalArgumentException {
		 setValue(Enum.valueOf(clazz, text));
	 }
}
