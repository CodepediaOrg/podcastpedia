<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String completeUrl= "" + request.getAttribute("javax.servlet.forward.request_uri");
	String queryString = request.getQueryString();
	queryString = queryString.replaceAll("&lang=\\w{2}","");
	queryString = queryString.replaceAll("&site_preference=\\w{6}","");
	queryString = queryString.replaceAll("&", "&amp;");
	completeUrl += "?" + queryString;
	pageContext.setAttribute("pageUrl", completeUrl.toString());
%>
<div id="logo_and_search">
	<div id="logos">
		<a href="https://twitter.com/podcastpedia" target="_blank" class="icon-twitter"></a>
		<a href="https://www.facebook.com/Podcastpedia" target="_blank" class="icon-facebook"></a>
		<a href="https://google.com/+PodcastpediaOrg" target="_blank" class="icon-google-plus"></a>
		<a href="http://www.podcastpedia.org/feeds/most_popular.rss" target="_blank" class="icon-feed3"></a>
	</div>

	<div id="flags">
		<ul class="pureCssMenu pureCssMenum">
			<li class="pureCssMenui"><a class="pureCssMenui" href="#"><span><spring:message code="your.language"/></span><![if gt IE 6]></a><![endif]><!--[if lte IE 6]><table><tr><td><![endif]-->
			<ul class="pureCssMenum">
				<li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=ro">Română</a></li>
				<li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=en">English</a></li>
				<li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=de">Deutsch</a></li>
				<li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=fr">Français</a></li>
				<li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=pl">Polski</a></li>
			</ul>
			<!--[if lte IE 6]></td></tr></table></a><![endif]--></li>
		</ul>
    <div id="login-button">
      <!-- display login button if not authenticated-->
      <sec:authorize access="isAnonymous()">
        <span><a href="<c:url value='/login/custom_login'/>">Log in</a></span>
      </sec:authorize>
      <!-- display My Podcasts if is authenticated -->
      <sec:authorize access="isAuthenticated()">
        <span><a href="<c:url value='/users/subscriptions'/>">My podcasts</a></span>
        <c:url var="logoutUrl" value="/logout"/><!-- default URL used now by Spring Security 4 -->
        <form action="${logoutUrl}" method="post">
          <input type="submit" value="Log out" />
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
      </sec:authorize>
    </div>
	</div>
</div>
<div class="clear"></div>

<%@ include file="header_common_part_m.jsp" %>






