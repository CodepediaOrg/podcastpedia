<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:choose>
	<c:when test="${numberOfPages >= 2}">
		<c:choose>
			<c:when test="${advancedSearchResult.currentPage == 1}">
				<ul>								
					<li> 
						<c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=2" var="nextPageURL"/>
						<a href="${nextPageURL}">&nbsp;<spring:message code="search.Next"/>&nbsp;</a> 
					</li>						
				</ul>																												
			</c:when>
			<c:when test="${advancedSearchResult.currentPage == numberOfPages}">
				<ul>																
					<li>
						<c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${numberOfPages-1}" var="previousPageURL"/> 
						<a href="${previousPageURL}"> <spring:message code="search.Previous"/>  </a> 
					</li>					
				</ul>																			
			</c:when>
			<c:otherwise>
				<ul>																			
					<li>
						<c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${advancedSearchResult.currentPage-1}" var="previousPageURL"/> 
						<a href="${previousPageURL}"> <spring:message code="search.Previous"/> </a> 
					</li>									
					<li> 
						<c:url value="/search/advanced_search/results?${queryString}&amp;currentPage=${advancedSearchResult.currentPage+1}" var="nextPageURL"/>
						<a href="${nextPageURL}"><spring:message code="search.Next"/> </a>	 
					</li>																					
				</ul>	
			</c:otherwise>
		</c:choose>	
	</c:when>
</c:choose>
