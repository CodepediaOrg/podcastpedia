
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>
<script type="text/javascript">jwplayer.key="fr4dDcJMQ2v5OaYJSBDXPnTeK6yHi8+8B7H3bg==";</script>

<!-- based on number of episodes will proceed further
	if number of episodes > 20 will display 20 per page, if not 10
-->
<div class="results_list">
  <c:forEach items="${episodes}" var="episode" varStatus="loop">
    <c:url var="episodeURL" value="/podcasts/${episode.podcastId}/${podcastTitleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}"/>
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
      <div class="social_and_download">
        <a href="#${2*loop.index}" class="icon-play-episode btn-share">Play</a>
        <a href="#${2*loop.index + 1}" class="icon-share-episode btn-share">Share</a>
        <a class="icon-download-ep btn-share" href="${episode.mediaUrl}" download>
          <spring:message code="global.dwnld.s" text="Download last episode"/>
        </a>
        <span class="item_url">https://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}</span>
        <span class="item_sharing_title">${episode.title}</span>
        <span class="item_media_url">${episode.mediaUrl}</span>
      </div>
    </div>
  </c:forEach>
</div>

<div class="clear"></div>
<c:if test="${numberOfEpisodePages > 1}">
	<div class="pagination">
		<ul>
			<c:forEach begin="1" end="${numberOfEpisodePages}" var="i">
				<c:url var="allEpisodesUrl" value="/podcasts/${podcastId}/${podcastTitleInUrl}/episodes/archive/pages/${i}"/>
				<li>
					<c:choose>
						<c:when test="${i==currentPage}">
							<a href="${allEpisodesUrl}" class="currentpage"> ${numberOfEpisodesPerPage*(i-1) + 1} - ${numberOfEpisodesPerPage*i} </a>
						</c:when>
						<c:otherwise>
							<a href="${allEpisodesUrl}"> ${numberOfEpisodesPerPage*(i-1) + 1} - ${numberOfEpisodesPerPage*i} </a>
						</c:otherwise>
					</c:choose>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>
<div class="clear"></div>

<!-- jquery dialogs -->
<div id="media_player_modal_dialog" title="Media player">
  <div id='mediaspace_modal'>Loading...</div>
</div>

<!-- javascript libraries required -->
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="<c:url value="/static/js/podcast/main.js" />"></script>
<script src="//code.jquery.com/ui/1.11.3/jquery-ui.min.js"></script>
