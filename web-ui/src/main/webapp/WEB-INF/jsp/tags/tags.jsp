<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<div id="all_tags" class="common_radius bg_color shadowy">
	<div id="find_keyword">
		<div class="ui-widget">
			<input name="tagQueryName" id="tagQuery" class="ui-autocomplete-input ui-corner-all" placeholder="<spring:message code="find.keyword" text="Type keyword..."/>">
		</div>
	</div>
	<div id="selected_tags"><span></span></div>
	<div class="clear"></div>
	<hr id="find_kw_sep_line"/>
	<ul>
		<c:forEach items="${tags}" var="tag">
			<li>
				<c:url value="/tags/${tag.tagId}/${tag.name}" var="urlTags" />
					<span><a href="${urlTags}" class="btn-metadata2">${fn:substring(tag.name,0,20)} (${tag.nrOfPodcasts})</a></span>
			</li>
		</c:forEach>
	</ul>
</div>
<div class="clear"></div>
<div class="pagination pagination_bottom">
	<c:choose>
		<c:when test="${page>0}">
			<span id="tags_bk"><a href="<c:url value="/tags/all/${page-1}"/>" class="fw_bk prev-page"><spring:message code="search.Previous"/></a></span>
		</c:when>
	</c:choose>
	<span id="tags_fw"><a href="<c:url value="/tags/all/${page+1}"/>" class="fw_bk next-page" style="margin-left:10px"><spring:message code="search.Next"/></a></span>
</div>
<div class="clear"></div>

<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="//code.jquery.com/ui/1.11.3/jquery-ui.min.js"></script>

<script src="<c:url value="/static/target/js/app.js?v=0.1" />"></script>
