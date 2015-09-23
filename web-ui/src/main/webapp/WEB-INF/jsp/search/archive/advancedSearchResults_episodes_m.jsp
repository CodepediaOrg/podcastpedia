<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>

<div id="search_feed_link">
	<c:url value="/static/images/feed-icon-14x14.png" var="urlFeedPic"/>
	<c:url value="/static/images/atomfeed14.png" var="urlFeedPicAtom"/>
	<c:url value="/feeds/search/episodes.rss?${queryString}&amp;currentPage=${advancedSearchResult.currentPage}" var="rssURL"/>
	<c:url value="/feeds/search/episodes.atom?${queryString}&amp;currentPage=${advancedSearchResult.currentPage}" var="atomURL"/>
	<spring:message code="search.subscribe_via_feed"/>
	<a href="${atomURL}" target="_blank" class="icon-feed-atom">Atom</a>
	<a href="${rssURL}" target="_blank" class="icon-feed-rss">RSS</a>
  <input type="hidden" name="queryString" id="queryString-data-id" value="${queryString}"/>
  <input type="hidden" name="currentPage" id="currentPage-data-id" value="${advancedSearchResult.currentPage}"/>
</div>
<div class="clear"></div>

<div class="results_list">
	<c:forEach items="${advancedSearchResult.episodes}" var="episode" varStatus="loop">
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
				<a class="icon-download-ep btn-share" href="${episode.mediaUrl}" target="_blank"><spring:message code="global.dwnld.s" text="Download last episode"/></a>
				<span class="item_url">http://www.podcastpedia.org/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}</span>
			</div>
			<div class="clear"></div>
		</div>
	</c:forEach>
</div>

<button type="button" id="more-results" style="display: block;width:100%;border-radius:5px;height:30px;font-family:arial,sans-serif; font-size:1.5em;color=#4f6a87" class="shadowy"><strong><spring:message code="global.more" text="More"/> &gt; </strong></button>

<!--  format to display results " 1 PREVIOUS 4 5 6 7 8 NEXT 20 " -->
<div class="pagination pagination_bottom">
	<%@ include file="pagination_page_episodes.jsp" %>
</div>
<div class="clear"></div>

<!-- javascript libraries required -->
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="<c:url value="/static/js/search/more-episodes.js" />"></script>

