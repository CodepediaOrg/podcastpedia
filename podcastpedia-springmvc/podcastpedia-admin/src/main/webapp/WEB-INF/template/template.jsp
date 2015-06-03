<!DOCTYPE HTML>
<%@ include file="/WEB-INF/template/includes.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><tiles:getAsString name="title"/></title>
		<c:url var="cssURL" value="/static/css/style.css"/>
		<link href="${cssURL}" rel="stylesheet" type="text/css"/>
		
	    <!-- Bootstrap -->
	    <link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet">
	
	    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	    <![endif]-->	
	    
	    <link href="<c:url value="/static/css/theme/theme.css"/>" rel="stylesheet">	
	</head>
    <body role="document">				
		<tiles:insertAttribute name="header" />
				
		<tiles:insertAttribute name="content" />
		
	    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<!-- Latest compiled and minified JavaScript -->
		<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	</body>
</html>
