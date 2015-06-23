<!DOCTYPE HTML>
<%@ include file="/WEB-INF/template/includes.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<html>
	<head>
		<meta name="google-site-verification" content="ZkFgaVcUEQ5HhjAA8-LOBUfcOY8Fh2PqiBqvM2xcFk0" />
		<title><tiles:insertAttribute name="title" ignore="true"/></title>
		<meta name="description" content="<tiles:insertAttribute name="page_description" ignore="true"/>">
		<link href="<c:url value="/static/css/podcastpedia.min.css?v=1.5"/>" rel="stylesheet" type="text/css"/>
		<link rel="icon" href="<c:url value="/static/images/favicon.ico"/>" type="image/x-icon" />
		<link rel="shortcut icon" href="<c:url value="/static/images/favicon.ico"/>" type="image/x-icon" />
		<meta charset="utf-8">
		<meta property="og:image" content="<tiles:insertAttribute name="og_image" ignore="true"/>" />
		<meta property="og:title" content="<tiles:insertAttribute name="og_title" ignore="true"/>" />
		<meta property="og:description" content="<tiles:insertAttribute name="og_desc" ignore="true"/>"/>
		<link rel="stylesheet" href="<tiles:insertAttribute name="jquery_ui_css" ignore="true"/>" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">				 				
	</head>
    <body id="<tiles:insertAttribute name="body-id" />">	
	    <!-- TOP LEFT RIBBON: START COPYING HERE -->
	    <div class="github-fork-ribbon-wrapper left">
	        <div class="github-fork-ribbon">
	            <a href="https://github.com/podcastpedia/podcastpedia-web" target="_blank">Fork me on GitHub</a>
	        </div>
	    </div>    
    	<div id="banner">			
			<tiles:insertAttribute name="header" />
		</div>
		<div class="clear"></div>
		<tiles:insertAttribute name="navigation_bar" />	
		<div class="clear"></div>			
		<div id="page">
			<tiles:insertAttribute name="content" />
		</div>		
		<div class="clear"></div>
		<div id="footer_wrapper">
			<tiles:insertAttribute name="footer" />
		</div>			
	</body>
</html>
