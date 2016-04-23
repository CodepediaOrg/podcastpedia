<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="podcastpedia" %>

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

<podcastpedia:podcasts pageName="search_results_podcasts"/>

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
<script src="<c:url value="/static/target/js/app.js" />"></script>


