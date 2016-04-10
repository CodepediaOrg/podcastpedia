<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>
<script type="text/javascript">jwplayer.key="fr4dDcJMQ2v5OaYJSBDXPnTeK6yHi8+8B7H3bg==";</script>

<div id="search_feed_link">
	<c:url value="/static/images/feed-icon-14x14.png" var="urlFeedPic"/>
	<c:url value="/static/images/atomfeed14.png" var="urlFeedPicAtom"/>
	<c:url value="/feeds/search/results.rss?${queryString}&amp;currentPage=${advancedSearchResult.currentPage}" var="rssURL"/>
	<c:url value="/feeds/search/results.atom?${queryString}&amp;currentPage=${advancedSearchResult.currentPage}" var="atomURL"/>
	<spring:message code="search.subscribe_via_feed"/>
	<a href="${atomURL}" target="_blank" class="icon-feed-atom">Atom</a>
	<a href="${rssURL}" target="_blank" class="icon-feed-rss">RSS</a>
  <input type="hidden" name="queryString" id="queryString-data-id" value="${queryString}"/>
  <input type="hidden" name="currentPage" id="currentPage-data-id" value="${advancedSearchResult.currentPage}"/>
</div>
<div class="clear"></div>

<div class="results_list">
  <c:forEach items="${advancedSearchResult.podcasts}" var="podcast" varStatus="loop">
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
            <c:set var="podcast_link" value="https://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>
          </c:when>
          <c:otherwise>
            <c:set var="podcast_link" value="https://www.podcastpedia.org/${podcast.identifier}"/>
          </c:otherwise>
        </c:choose>
        <span class="podcast_url">${podcast_link}</span>
        <span class="podcast_title">${podcast.title}</span>
        <a href="#${2*loop.index+1}" class="icon-last-episodes icon-arrow-down btn-share"><spring:message code="user.last_episodes"/></a>

        <c:if test="${podcast.updateFrequency == 'DAILY' || podcast.updateFrequency == 'WEEKLY' || podcast.updateFrequency == 'MONTHLY'}">
            <!-- if not authenticated will be asked to log in -->
            <sec:authorize access="isAnonymous()">
              <a href="#${10*loop.index+1}" class="btn-share ask-for-login" style="background-color: orangered"><spring:message code="user.subscribe" text="Subscribe"/></a>
            </sec:authorize>
            <!-- if authenticated can subscribe automatically -->
            <sec:authorize access="isAuthenticated()">
              <a href="#${10*loop.index+1}" id="subscribe-to-podcast${loop.index}" class="btn-share subscribe-to-podcast-cls"><spring:message code="user.subscribe" text="Subscribe"/></a>
              <input type="hidden" name="${_csrf.parameterName}${loop.index}" value="${_csrf.token}" />
            </sec:authorize>
        </c:if>

        <input type="hidden" name="podcastId" value="${podcast.podcastId}"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </div>

      <div class="clear"></div>

      <div class="last_episodes not_shown">
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
            <div class="social_and_download">
              <a href="#${10*loop.index + 2*loopEpisodes.index}" class="icon-play-episode btn-share">Play</a>
              <a href="#${10*loop.index + 2*loopEpisodes.index + 1}" class="icon-share-episode btn-share">Share</a>
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
    </div>
  </c:forEach>
</div>

<div class="pagination pagination_bottom">
  <%@ include file="pagination_page_podcasts.jsp" %>
</div>
<div class="clear"></div>

<!-- jquery dialogs -->
<div id="ask-for-login-form" title="Sign in">
  <p><spring:message code="user.login.perform_operation" text="Please log in"/></p>
</div>

<div id="media_player_modal_dialog" title="Media player">
  <div id='mediaspace_modal'>Loading...</div>
</div>


<sec:authorize access="isAuthenticated()">
  <c:forEach items="${advancedSearchResult.podcasts}" var="podcast" varStatus="loop">
    <div id="subscribe-form-subscription-category${loop.index}" class="subscribe-form-subscription-category-cls" title="<spring:message code="user.subscriptions.select_category.title" text="Select category"/>">
      <form class="vertical_style_form">
        <sec:authorize access="isAuthenticated()">
          <p>
            <select class="form_input" id="subscription_categories${loop.index}">
              <option value="" label="<spring:message code="user.subscriptions.select_category.existing" text="Existing category"/>"/>
              <c:forEach items="${subscriptionCategories}" var="subscriptionCategory">
                <option value="${subscriptionCategory}">${subscriptionCategory}</option>
              </c:forEach>
            </select>
          </p>
        </sec:authorize>
        <div class="label_above_elements">
          <label for="newPlayist" class="label">
            <spring:message code="user.subscriptions.select_category.new" text="Add new category"/>
          </label>
        </div>
        <p>
          <input name="newSubscriptionCategory${loop.index}" class="form_input" id="newSubscriptionCategory${loop.index}" style='width:200px'/>
          <input type="hidden" name="podcastId${loop.index}" id="sub_podcastId${loop.index}" value="${podcast.podcastId}"/>
        </p>
      </form>
    </div>
  </c:forEach>
</sec:authorize>

<!-- javascript libraries required -->
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="//code.jquery.com/ui/1.11.3/jquery-ui.min.js"></script>
<script src="<c:url value="/static/js/search/podcasts-results.js" />"></script>

