<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="podcastpedia" %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>
<script type="text/javascript">jwplayer.key="fr4dDcJMQ2v5OaYJSBDXPnTeK6yHi8+8B7H3bg==";</script>

<c:choose>
  <c:when test="${empty subscriptionCategories}">
    <div class="bg_color shadowy common_mar_pad common_radius">
      <spring:message code="user.no_subscriptions.welcome"/>
    </div>
  </c:when>
  <c:otherwise>
    <!-- first there is the my subscriptions categories sections -->
    <div id="my_subscription_categories" class="common_radius bg_color shadowy common_mar_pad" style="margin-bottom: 20px">
      <h2 class="title_before_form"><spring:message code="user.subscriptions.categories.title"/></h2>
      <hr class="before_form_header_line"/>
      <ul id="subscription_categories">
        <c:forEach items="${subscriptionCategories}" var="subscriptionCategory">
          <li>
            <c:url value="/users/subscription-categories/${subscriptionCategory}" var="subscriptionCategoryUrl" />
            <a href="${subscriptionCategoryUrl}" class="btn-share"> ${subscriptionCategory} </a>
          </li>
        </c:forEach>
      </ul>
    </div>
    <div class="clear"></div>

    <!-- second part will display the latest updates -->
    <h2 class="title_before_form" style="color:white">Recently updated</h2>
    <podcastpedia:podcasts pageName="user-homepage"/>

  </c:otherwise>
</c:choose>

<!-- jquery dialogs -->
<div id="media_player_modal_dialog" title="Media player">
  <div id='mediaspace_modal'>Loading...</div>
</div>


<!-- javascript libraries required -->
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="//code.jquery.com/ui/1.11.3/jquery-ui.min.js"></script>
<script src="<c:url value="/static/target/js/app.js?v=0.1" />"></script>


