<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<div id="all_categories_with_podcasts" class="common_radius bg_color shadowy">
	<ul>
		<c:forEach items="${allCategories}" var="category">
			<li> 						 				
				<c:url value="/categories/${category.categoryId}/${category.name}" var="urlCategory" />
					<a href="${urlCategory}" class="btn-share"> <spring:message code="${category.name}"/> (${category.numberOfPodcasts})</a>							
			</li>
		</c:forEach>
	</ul>
</div>	
<div class="clear"></div>