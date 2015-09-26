<%@ include file="/WEB-INF/template/includes.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="menu_wrapper">
  <div id="nav">
	<ul>
    <sec:authorize access="isAuthenticated()">
      <li id="nav-mypodcastpedia">
        <a href="#"><spring:message code="user.mypodcastpedia" text="My Podcastpedia"/></a>
        <ul>
          <li id="nav-my-subscriptions">
            <a href="<c:url value="/users/subscriptions"/>"><spring:message code="user.subscriptions"/></a>
          </li>
          <li id="nav-my-latest-episodes">
            <a href="<c:url value="/users/subscriptions/latest-episodes"/>"><spring:message code="user.last_episodes"/></a>
          </li>
          <!-- TO DO
          <li id="my-searches">
            <a href="/users/searches/>">My searches</a>
          </li>
          -->
          <li id="nav-homepage-my-podcastpedia">
            <c:url value="/" var="urlHome" />
            <a href="${urlHome}">Home</a>
          </li>
          <li>
            <c:url var="logoutUrl" value="/logout"/><!-- default URL used now by Spring Security 4 -->
            <form id="logout-form" action="${logoutUrl}" method="post">
              <input type="submit" value="<spring:message code="user.logout"/>" />
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
          </li>
        </ul>
      </li>
    </sec:authorize>
    <sec:authorize access="isAnonymous()">
      <li id="nav-homepage">
        <c:url value="/" var="urlHome" />
        <a href="${urlHome}">Home</a>
      </li>
    </sec:authorize>
		<li id="nav-tags">
			<a href="<c:url value="/tags/all/0"/>"><spring:message code="pod_details.tags"/></a>
		</li>
		<li id="nav-categories">
			<a href="<c:url value="/categories"/>"><spring:message code="header.menu.categories"/></a>
		</li>
		<li id="nav-add-podcast">
			<a href="<c:url value="/how_can_i_help/add_podcast"/>"><spring:message code="header.menu.addPodcast"/></a>
		</li>
		<li id="nav-support">
			<c:url value="/how_can_i_help" var="myHelpUrl" />
			<a href="${myHelpUrl}"><spring:message code="header.menu.howcanihelp"/></a>
		</li>
		<li id="nav-contact">
			<a href="<c:url value="/contact"/>"><spring:message code="header.menu.contact"/></a>
		</li>
    <sec:authorize access="isAnonymous()">
      <li id="nav-podcasting">
        <a href="<c:url value="/podcasting"/>">Podcasting</a>
      </li>
    </sec:authorize>
		<li id="nav-responsive">
			<a href="#"></a>
			<ul>
				<li id="nav-tags-resp">
					<a href="<c:url value="/tags/all/0"/>"><spring:message code="pod_details.tags"/></a>
				</li>
				<li id="nav-categories-resp">
					<a href="<c:url value="/categories"/>"><spring:message code="header.menu.categories"/></a>
				</li>
				<li id="nav-add-podcast-resp">
					<a href="<c:url value="/how_can_i_help/add_podcast"/>"><spring:message code="header.menu.addPodcast"/></a>
				</li>
				<li id="nav-support-resp">
					<c:url value="/how_can_i_help" var="myHelpUrl" />
					<a href="${myHelpUrl}"><spring:message code="header.menu.howcanihelp"/></a>
				</li>
				<li id="nav-contact-resp">
					<a href="<c:url value="/contact"/>"><spring:message code="header.menu.contact"/></a>
				</li>
				<li id="nav-podcasting-resp">
					<a href="<c:url value="/podcasting"/>">Podcasting</a>
				</li>
			</ul>
		</li>
	</ul>
</div>
</div>
