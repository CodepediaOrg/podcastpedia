<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:forEach items="${episodes}" var="episode" varStatus="loop">
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
            <c:url var="episodeURL" value="/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}"/>
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
            <a class="icon-download-ep btn-share" href="${episode.mediaUrl}" download><spring:message code="global.dwnld.s" text="Download last episode"/></a>
            <span class="item_url">https://www.podcastpedia.org/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}</span>
            <span class="item_sharing_title">${episode.title}</span>
            <span class="item_media_url">${episode.mediaUrl}</span>
        </div>
    </div>
</c:forEach>
