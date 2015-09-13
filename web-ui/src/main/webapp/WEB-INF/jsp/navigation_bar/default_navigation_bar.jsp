<%@ include file="/WEB-INF/template/includes.jsp" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="menu_wrapper">
  <div id="nav">
	<ul>
    <sec:authorize access="isAuthenticated()">
      <li id="nav-mypodcastpedia">
        <a href="#">My podcastpedia</a>
        <ul>
          <li id="nav-my-subscriptions">
            <a href="<c:url value="/users/subscriptions"/>"><spring:message code="user_area.subscriptions"/></a>
          </li>
          <li id="nav-my-latest-episodes">
            <a href="<c:url value="/users/subscriptions/latest-episodes"/>">Last episodes</a>
          </li>
          <li id="my-searches">
            <a href="<c:url value="/how_can_i_help/add_podcast"/>">My searches</a>
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
