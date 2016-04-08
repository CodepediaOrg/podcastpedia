<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
		<a href="https://www.podcastpedia.org/feeds/most_popular.rss" target="_blank" class="icon-feed3"></a>
	</div>

	<div id="flags">
		<ul class="pureCssMenu pureCssMenum">
			<li class="pureCssMenui"><a class="pureCssMenui" href="#"><span><spring:message code="your.language"/></span></a>
        <ul class="pureCssMenum">
          <li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=ro">Română</a></li>
          <li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=en">English</a></li>
          <li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=de">Deutsch</a></li>
          <li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=fr">Français</a></li>
          <li class="pureCssMenui"><a class="pureCssMenui" href="${pageUrl}&amp;lang=pl">Polski</a></li>
        </ul>
      </li>
		</ul>
	</div>
</div>

<!-- display login button if not authenticated-->
<sec:authorize access="isAnonymous()">
  <div id="login-button">
    <a href="<c:url value='/login/custom_login'/>"><spring:message code="user.login"/></a>
  </div>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
  <div id="login-button">
    <c:url var="logoutUrl" value="/logout"/><!-- default URL used now by Spring Security 4 -->
    <form id="logout-form" action="${logoutUrl}" method="post">
      <input type="submit" value="<spring:message code="user.logout"/>" />
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </div>
</sec:authorize>

<div class="clear"></div>

<%@ include file="header_common_part_m.jsp" %>






