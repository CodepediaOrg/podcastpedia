<%@ include file="/WEB-INF/template/includes.jsp" %>
	
<div id="nav">
	<ul>						
		<li id="nav-homepage"> 
			<c:url value="/" var="urlHome" />
			<a href="${urlHome}">Home</a> 
		</li>				
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
		<li id="nav-podcasting"> 
			<a href="<c:url value="/podcasting"/>">Podcasting</a> 
		</li>
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
