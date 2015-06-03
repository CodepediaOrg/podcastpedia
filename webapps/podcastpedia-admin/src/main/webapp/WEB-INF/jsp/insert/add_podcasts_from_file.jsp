<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<h1>Add podcasts from file</h1>
	<!-- Adds podcasts from file -->
	<!--  -->
	<c:url value="/admin/insert/upload_file" var="addPodcastsFromFile_url" />
	<form:form modelAttribute="uploadItem" method="post"
		enctype="multipart/form-data" action="${addPodcastsFromFile_url}">
		<p>
			<input name="fileData" type="file"
				placeholder="Select file from file system" />
		</p>
		<p>
			<button type="submit" class="btn btn-default">Submit file</button>
		</p>
	</form:form>

	<p style="margin-top: 100px;">
		<h2>Metadata format</h2>
		<b>The file is a ";" separated file with the following format
		format of the metadata:</b><br> URL(rss or
		atom); identifier_on_podcastpedia; categories(comma separated); lang_code;
		media_type; update_frequency; tags; facebook_fan_page;
		twitter_fan_page; gplus_fan_page; submitter_name; submitter_email <br>
		<b>Example:</b>
		<br> http://aciworldwide.libsyn.com/rss; ACIWorldwide;
		science_technology, internet_computer, money_business; en; Audio;
		MONTHLY;
		atm,banking,bankingtechnology,electronic,financial,global,mobile,payment,paymentsystems,platforms,retail;
		https://www.facebook.com/aciworldwide;
		https://twitter.com/aci_worldwide;
		https://plus.google.com/+Aciworldwideinc/about; ACI Worldwide;
		alexandra.gustin@aciworldwide.com
	</p>
</div>
<!-- /.container -->