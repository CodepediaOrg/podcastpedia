<?xml version="1.0" encoding="UTF-8"?>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/recaptcha_options.jsp" %>

<c:if test="${thank_you_message != null }">
	 <div id="thank_you_message" class="common_radius bg_color shadowy common_mar_pad">
	 	<spring:message code="add_podcast.thank_you" text="Thank you for your suggestion. This will be reviewed before being added to the directory"/>
	 </div>
</c:if>
<div id="add_podcast_wrapper" class="bg_color border_radius shadowy common_mar_pad">
	<h2 class="title_before_form">
		<spring:message code="add_podcast.suggest_header" text="Suggest a podcast"/>
	</h2>
	<hr id="before_form_header_line"/>

	<form:form id="add_podcast_form" class="vertical_style_form"
		method="POST" modelAttribute="addPodcastForm">

		<div class="label_above_elements">
			<label for="name" class="label">
				<spring:message code="label.name" text="Name"/>
			</label>
		</div>
		<div class="error">
			<form:errors path="name" cssClass="error_form_validation"/>
		</div>
		<p>
			<form:input path="name" id="name" class="form_input"/>
		</p>

		<div class="label_above_elements">
			<label for="email" class="label">
				<spring:message code="label.email" text="Email - required, verified but never shown"/>
			</label>
		</div>
		<div class="error">
			<form:errors path="email" cssClass="error_form_validation"/>
		</div>
		<p>
			<form:input path="email" id="email" class="form_input"/>
		</p>

		<div class="label_above_elements">
			<label for="feedUrl" class="label">
				<spring:message code="add_podcast.pod_url" text="URL of podcast"/>
			</label>
		</div>
		<div class="error">
			<form:errors path="feedUrl" cssClass="error_form_validation"/>
		</div>
		<p>
			<form:input path="feedUrl" class="form_input url_in"/>
		</p>

		<div class="label_above_elements">
			<label for="identifier" class="label">
				<spring:message code="label.identifier" text="Identifier - e.g. http://www.podcastpedia.org/AwesomePodcast"/>
			</label>
		</div>
		<div class="error">
			<form:errors path="identifier" cssClass="error_form_validation"/>
		</div>
		<p>
			<form:input path="identifier" id="identifier" class="form_input"/>
		</p>

		<div class="label_above_elements">
			<label for="category" class="label">
				<b><spring:message code="search_form.category" text="Category"/>*</b>
				- <spring:message code="category.tip" text="Category"/>
			</label>
		</div>
		<div class="error">
			<form:errors path="categories" cssClass="error_form_validation"/>
		</div>
		<p>
			<form:select path="categories" multiple="true" id="category" class="form_input">
				<c:forEach items="${allCategories}" var="category">
					<form:option value="${category.name}">
						<spring:message code="${category.name}"/>
					</form:option>
				</c:forEach>
			</form:select>
			<span id="category_comment">
				<spring:message code="search.text.category_ctrl" text="Use Ctrl key to select multiple categories "/>
			</span>
		</p>

		<div class="label_above_elements">
			<label for="language" class="label">
				<b><spring:message code="search_form.language" text="Language"/>*</b>
			</label>
		</div>
		<p>
			<form:select path="languageCode" id="language" class="form_input">
				<c:forEach items="${languageCodes}" var="languageCode">
					<spring:message var="langCodeVar" code="${languageCode}"/>
				    <form:option value="${languageCode.code}" label='${langCodeVar}'/>
				</c:forEach>
			</form:select>
		</p>

		<div class="label_above_elements">
			<label for="mediaType" class="label">
				<b><spring:message code="search_form.mediaType" text="Media Type"/>*</b>
			</label>
		</div>
		<p>
			<form:select path="mediaType" multiple="false" id="mediaType" class="form_input">
				<form:options items="${mediaTypes}" />
			</form:select>
		</p>

		<div class="label_above_elements">
	  		<label for="updateFrequency" class="label">
	  			<b><spring:message code="add_podcast.upd_frq" text="Update frequency"/>*</b>
	  		</label>
	  	</div>
  		  <p>
			<form:select path="updateFrequency" multiple="false" class="form_input">
				<c:forEach items="${updateFrequencies}" var="updateFreq">
					<spring:message var="updateFreqVar" code="${updateFreq}"/>
				    <form:option value="${updateFreq}" label='${updateFreqVar}'/>
				</c:forEach>
			</form:select>
	     </p>

		<div class="label_above_elements">
			<label for="suggestedTags" class="label">
				<b><spring:message code="pod_details.tags" text="Tags"/>*</b> -
				<span>
					<spring:message code="add_podcast.tags.sug" text="please separate by comma"/>
				</span>
			</label>
		</div>
		<div class="error">
			<form:errors path="suggestedTags"  cssClass="error_form_validation"/>
		</div>
		<p>
			<form:input path="suggestedTags" class="form_input url_in"/>
		</p>

		<div class="label_above_elements">
			<img class="recommend_social" alt="Facebook" title="Facebook fan page" src="<c:url value="/static/images/logos/fb_51.png"/>">
            <label for="facebookPage" class="label">
                Fanpage Facebook (complete URL - e.g. <em>https://www.facebook.com/Podcastpedia</em>)
            </label>
		</div>
        <div class="error">
            <form:errors path="facebookPage" cssClass="error_form_validation"/>
        </div>
		<p>
			<form:input path="facebookPage" id="facebookPage"  class="form_input"/>
		</p>

		<div class="label_above_elements">
			<img class="recommend_social" alt="Twitter" title="Twitter fan page" src="<c:url value="/static/images/logos/twitter_51.png"/>">
            <label for="twitterPage" class="label">
				Fanpage Twitter (complete URL - e.g. <em>https://twitter.com/podcastpedia</em>)
			</label>
		</div>
        <div class="error">
            <form:errors path="twitterPage" cssClass="error_form_validation"/>
        </div>
		<p>
			<form:input path="twitterPage" id="twitterPage"  class="form_input"/>
		</p>

		<div class="label_above_elements">
			<img class="recommend_social" alt="Google plus" title="G+ fan page" src="<c:url value="/static/images/logos/gplus_51.png"/>">
			<label for="gplusPage" class="label">
				Fanpage Google Plus (complete URL - e.g. <em>https://google.com/+PodcastpediaOrg</em>)
			</label>
		</div>
        <div class="error">
            <form:errors path="gplusPage" cssClass="error_form_validation"/>
        </div>
		<p>
			<form:input path="gplusPage" id="gplusPage"  class="form_input"/>
		</p>

		<div class="label_above_elements">
			<label for="message" class="label">
				<b><spring:message code="contact.your_message" text="Your message"/></b>
			</label>
			<form:errors path="message" cssClass="error_form_validation"/>
		</div>
		<p id="contact_form_text_message">
			<form:textarea path="message" rows="5" cols="30" class="form_input url_in"/>
		</p>

		<p class="captcha_help_p">
			<spring:message code="add_podcast.spam" text="Help us prevent spam!"/>
			<br/>
			<spring:message code="label.captcha_first_part" text="Please type the two words in the image below - press"/>
			<img id="captcha_help_pic"src="<c:url value='/static/images/recaptcha_refresh.png'/>" alt="refresh" />
			<spring:message code="label.captcha_second_part" text="for a new challenge"/>
		 </p>
		<div id="captcha_paragraph">
			<c:if test="${invalidRecaptcha == true}">
				<span class="error_form_validation"><spring:message code="invalid.captcha" text="Invalid captcha please try again"/></span>
			</c:if>
		    <%
		        ReCaptcha c = ReCaptchaFactory.newSecureReCaptcha("6LcW3OASAAAAAKEJTHMmp_bo5kny4lZXeDtgcMqC",
		        					"6LcW3OASAAAAAKVX2duVsSy2uMMHL105-jPDrHMD", false);
		        out.print(c.createRecaptchaHtml(null, null));
		    %>
		</div>

		<spring:message var="suggest_pod_button" code="add_podcast.sug_btn" text="Suggest podcast"/>
		<input type="submit" value="${suggest_pod_button}"  id="suggest" class="submit_form_button"/>
		<div class="clear"></div>
	</form:form>
  <br><br>
	<span style="color:#A73030;font-weight: bold;">*<spring:message code="required" text="required"/></span>
</div>
