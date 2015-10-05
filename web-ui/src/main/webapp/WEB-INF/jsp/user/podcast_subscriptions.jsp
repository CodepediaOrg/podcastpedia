<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>

<div class="results_list">
	<c:forEach items="${subscriptions}" var="podcast" varStatus="loop">
		<div class="bg_color shadowy podcast_wrapper">
	    	<div class="title-and-pub-date">
				<c:choose>
					<c:when test="${podcast.mediaType == 'Audio'}">
						<div class="icon-audio-episode"></div>
					</c:when>
					<c:otherwise>
						<div class="icon-video-episode"></div>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${podcast.identifier == null}">
						<c:set var="urlPodcast" value="/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>
					</c:when>
					<c:otherwise>
						<c:set var="urlPodcast" value="/${podcast.identifier}"/>
					</c:otherwise>
				</c:choose>
				<a class="item_title" href="${urlPodcast}">
					<c:out value="${podcast.title}"/>
				</a>
				<div class="pub_date_media_type">
					<div class="pub_date">
						<fmt:formatDate pattern="yyyy-MM-dd" value="${podcast.publicationDate}" />
					</div>
				</div>
			<div class="clear"></div>
			</div>
			<hr>
			<div class="pod_desc">
				<a class="item_desc" href="${urlPodcast}">
					${fn:substring(podcast.description,0,350)}
				</a>
			</div>
			<div class="pod_desc_bigger">
				<a class="item_desc" href="${urlPodcast}">
					${fn:substring(podcast.description,0,750)}
				</a>
			</div>
			<div class="clear"></div>
			<div class="social_and_download_podcast">
				<a href="#${2*loop.index}" class="icon-share-podcast btn-share">Share</a>
				<c:choose>
					<c:when test="${podcast.identifier == null}">
						<c:set var="podcast_link" value="http://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>
					</c:when>
					<c:otherwise>
						<c:set var="podcast_link" value="http://www.podcastpedia.org/${podcast.identifier}"/>
					</c:otherwise>
				</c:choose>
				<span class="podcast_url">${podcast_link}</span>
        <a href="#${2*loop.index+1}" class="icon-last-episodes btn-share"><spring:message code="user.last_episodes"/></a>
			</div>
      <div class="clear"></div>
      <div class="last_episodes not_shown" style="margin-bottom: 40px;background-color: #2980b9;border-radius: inherit;padding: 8px">
        <h2><spring:message code="user.last_episodes"/></h2>
        <c:forEach items="${podcast.episodes}" var="episode" varStatus="loopEpisodes">
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
              <c:url var="episodeURL" value="/podcasts/${podcast.podcastId}/${podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}"/>
              <a href="${episodeURL}" class="item_title">${episode.title}</a>
              <div class="pub_date">
                <fmt:formatDate pattern="yyyy-MM-dd" value="${episode.publicationDate}" />
                <c:choose>
                  <c:when test="${episode.isNew == 1}">
                    <span class="ep_is_new"><spring:message code="new"/></span>
                  </c:when>
                </c:choose>
              </div>
              <div class="clear"></div>
            </div>
            <hr>
            <div class="ep_desc">
              <a href="${episodeURL}" class="item_desc">
                  ${fn:substring(episode.description,0,280)}
              </a>
            </div>
            <div class="ep_desc_bigger">
              <a href="${episodeURL}" class="item_desc">
                  ${fn:substring(episode.description,0,600)}
              </a>
            </div>
            <div class="clear"></div>
            <div class="not_shown">
              <div id='mediaspace${10*loop.index + loopEpisodes.index}'>Flashplayer not supported</div>
              <!-- switch player CAN or CANNOT be displayed -->
              <c:choose>
                <c:when test="${episode.mediaType == 'Audio'}">
                  <script type='text/javascript'>
                    jwplayer('mediaspace${10*loop.index + loopEpisodes.index}').setup({
                      'controlbar': 'bottom',
                      'width': '100%',
                      'aspectratio': '16:5',
                      'file': '${episode.mediaUrl}'
                    });
                  </script>
                </c:when>
                <c:otherwise>
                  <script type='text/javascript'>
                    jwplayer('mediaspace${10*loop.index + loopEpisodes.index}').setup({
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
              <a href="#${10*loop.index + 2*loopEpisodes.index}" class="icon-play-episode btn-share">Play</a>
              <a href="#${10*loop.index + 2*loopEpisodes.index + 1}" class="icon-share-episode btn-share">Share</a>
              <a class="icon-download-ep btn-share" href="${episode.mediaUrl}" download>
                <spring:message code="global.dwnld.s" text="Download last episode"/>
              </a>
              <span class="item_url">http://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}</span>
            </div>
          </div>
        </c:forEach>
      </div>
			<div class="clear"></div>
		</div>
	</c:forEach>
</div>

<!-- javascript libraries required -->
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="<c:url value="/static/js/user/subscriptions.js" />"></script>

