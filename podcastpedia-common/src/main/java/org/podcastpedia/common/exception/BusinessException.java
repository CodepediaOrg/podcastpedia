package org.podcastpedia.common.exception;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.ErrorCodeType;

/**
 * Custom exception class to handle different types of application related exceptions
 * (e.g. no podcast or episode found in the database) 
 * 
 * @author ama
 *
 */
public class BusinessException extends Exception{

	private static final long serialVersionUID = -7311925629618239775L;
	
	private ErrorCodeType errorCode;
	
	/** the podcast the error was generated for */
	private Podcast podcast;
	
	/** the episode the error was generated for */
	private Episode episode;
	
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(ErrorCodeType errorCode, String message) {
		super(message);
		this.errorCode = errorCode;		
	}
	
	public BusinessException(Podcast podcast, ErrorCodeType errorCode, String message) {
		super(message);
		this.podcast = podcast; 
		this.errorCode = errorCode;		
	}	
	
	public Podcast getPodcast() {
		return podcast;
	}

	public void setPodcast(Podcast podcast) {
		this.podcast = podcast;
	}

	public Episode getEpisode() {
		return episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}

	public ErrorCodeType getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCodeType errorCode) {
		this.errorCode = errorCode;
	}
		
}
