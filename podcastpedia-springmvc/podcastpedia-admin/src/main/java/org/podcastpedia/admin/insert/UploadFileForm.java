package org.podcastpedia.admin.insert;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadFileForm
{
  private String name;
  private CommonsMultipartFile fileData;
  private String text;
 
  public String getName()
  {
    return name;
  }
 
  public void setName(String name)
  {
    this.name = name;
  }
 
  public CommonsMultipartFile getFileData()
  {
    return fileData;
  }
 
  public void setFileData(CommonsMultipartFile fileData)
  {
    this.fileData = fileData;
  }

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}
  
}
