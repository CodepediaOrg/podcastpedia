<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="footer">
	<c:url value="/contact" var="contactUrl" />
	<a href="<c:url value="/contact"/>"><spring:message code="footer_contact"/></a> |
	
	<a href="<c:url value="/podcasting"/>">Podcasting</a> |	
	
	<a href="<c:url value="/mission"/>"><spring:message code="footer_mission"/></a>	 |
	
	<a href="<c:url value="/how_can_i_help/add_podcast"/>"><spring:message code="header.menu.addPodcast"/></a> |
	
	<a href="<c:url value="/privacy"/>"><spring:message code="footer.privacy" text="Privacy policy"/></a> |	
	
	<a href="<c:url value="/disclaimer"/>"><spring:message code="footer.disclaimer" text="Disclaimer"/></a> |
	
	<a href="http://www.codingpedia.org/ama/story-of-podcastpedia-org/" target="_blank"><spring:message code="footer.story" text="Story of Podcastpedia"/></a>
	 
	<!-- 
	<c:url value="/help.html" var="helpUrl" />
	<a href="${helpUrl}"><spring:message code="footer_help"/></a>  |
	
	<c:url value="/privacy.html" var="privacyUrl" />
	<a href="${privacyUrl}"><spring:message code="footer_privacy"/></a>  |	
	
	<c:url value="/terms.html" var="termsUrl" />
	<a href="${termsUrl}"><spring:message code="footer_terms"/></a>  |
	 -->	
</div>

<div id="footer_copyright">
	<spring:message code="copyright_message"/>
</div>
  
