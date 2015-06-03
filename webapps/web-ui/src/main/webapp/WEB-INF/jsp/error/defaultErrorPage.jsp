<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="error_page_div">
	<div id="defaultErrorText" class="bg_color common_radius shadowy common_mar_pad">
		Unknown error. Please inform us about it with the <a href="<c:url value="/contact?topic=error_indication"/>">Error indication form</a>.
		<br/>We will analyze this and fix it as soon as possible. Thank you for your understanding.  
	</div>
</div>
	
	
