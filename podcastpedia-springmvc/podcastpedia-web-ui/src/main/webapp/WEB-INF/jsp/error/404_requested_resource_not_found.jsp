<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="error_page_div">
	<div id="defaultErrorText" class="bg_color common_radius shadowy common_mar_pad">
		The resource you requested was not found. Please verify the link again and inform us about the problem with the <a href="<c:url value="/contact?topic=error_indication"/>">Error indication form</a>. Thank you.  
	</div>
</div>
	
	
