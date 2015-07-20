<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="error_page_div">
	<div id="defaultErrorText" class="bg_color common_radius shadowy common_mar_pad">
		Dear visitor, the episode you selected is no longer available.  
		Please visit
			<c:url value="/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}" var="urlPodcast" />
			<a href="${urlPodcast}"> <c:out value="${episode.podcast.title}"/> </a> 
        for the latest episodes of the desired podcast.  
           
		If you think this is an error please inform us with the <a href="<c:url value="/contact?topic=error_indication"/>">Error indication form</a>. Thank you.
	</div>
</div>
	
	
