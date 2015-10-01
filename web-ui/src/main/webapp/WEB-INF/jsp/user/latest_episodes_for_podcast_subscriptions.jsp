<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>

<div class="results_list">
	<c:forEach items="${latestEpisodes}" var="episode" varStatus="loop">
		<c:url var="episodeUrl" value="/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}"/>
	    <div class="bg_color shadowy item_wrapper">
	    	<div class="title-and-pub-date">
				<c:choose>
					<c:when test="${episode.mediaType == 'Audio'}">
						<div class="icon-audio-episode"></div>
					</c:when>
					<c:otherwise>
						<div class="icon-video-episode"></div>
					</c:otherwise>
				</c:choose>
				<a href="${episodeUrl}" class="item_title"> <c:out value="${episode.title}"/> </a>
				<div class="pub_date_media_type">
					<div class="pub_date">
						<fmt:formatDate pattern="yyyy-MM-dd" value="${episode.publicationDate}" />
						<c:choose>
							<c:when test="${episode.isNew == 1}">
								<span class="ep_is_new"><spring:message code="new"/></span>
							</c:when>
						</c:choose>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<hr>
			<div class="ep_desc">
				<a href="${episodeUrl}" class="item_desc">
					${fn:substring(episode.description,0,280)}
				</a>
			</div>
			<div class="ep_desc_bigger">
				<a href="${episodeUrl}" class="item_desc">
					${fn:substring(episode.description,0,600)}
				</a>
			</div>
			<div class="clear"></div>
			<div class="not_shown">
				<c:url var="currentEpisodeURL" value="/podcasts/${episode.podcastId}/${podcast_title_in_url}/episodes/${episode.episodeId}/${episode.titleInUrl}?show_other_episodes=true"/>
				<div id='mediaspace${loop.index}' class="jwp"><spring:message code="ep_details.pl_not_shown_part1" text="If player not shown please"/> <a href="${currentEpisodeURL}"> <spring:message code="global.click_here" text="click here"/> </a></div>

				<!-- switch player CAN or CANNOT be displayed -->
				<c:choose>
					<c:when test="${episode.mediaType == 'Audio'}">
						<script type='text/javascript'>
						  jwplayer('mediaspace${loop.index}').setup({
						    'controlbar': 'bottom',
						    'width': '100%',
						    'aspectratio': '16:5',
						    'file': '${episode.mediaUrl}'
						  });
						</script>
					</c:when>
					<c:otherwise>
						<script type='text/javascript'>
						  jwplayer('mediaspace${loop.index}').setup({
						    'controlbar': 'bottom',
						    'width': '100%',
						    'aspectratio': '16:9',
						    'file': '${episode.mediaUrl}'
						  });
						</script>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="social_and_download">
				<a href="#${2*loop.index}" class="icon-play-episode btn-share">Play</a>
				<a href="#${2*loop.index + 1}" class="icon-share-episode btn-share">Share</a>
				<a class="icon-download-ep btn-share" href="${episode.mediaUrl}" download><spring:message code="global.dwnld.s" text="Download last episode"/></a>
				<span class="item_url">http://www.podcastpedia.org/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}</span>
			</div>
			<div class="clear"></div>
		</div>
	</c:forEach>
</div>

<!-- javascript libraries required -->
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="<c:url value="/static/js/search/load_new_results.js" />"></script>
