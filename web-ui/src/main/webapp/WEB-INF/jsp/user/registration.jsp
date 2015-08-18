<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<%@ include file="/WEB-INF/jsp/common/recaptcha_options.jsp" %>

<c:if test="${thank_you_message != null }">
  <div id="thank_you_message" class="common_radius bg_color shadowy common_mar_pad">
    <spring:message code="contact.thank_you" text="Thank you for your message. We will get back to you as soon as possible."/>
  </div>
</c:if>

<div id="contact_form_wrapper" class="bg_color border_radius shadowy common_mar_pad">
  <h2 class="title_before_form">Registration </h2>
  <hr class="before_form_header_line"/>

  <c:url value="/contact.html" var="addComment_url"/>
  <form:form name="contact_form" class="vertical_style_form"
             method="POST" modelAttribute="user" action="${addComment_url}">

    <div id="label_above_elements">
      <label for="name" class="label">
        <spring:message code="registration.display-name" text="Display name - optional"/>
      </label>
    </div>
    <p>
      <form:input path="displayName" id="displayName" class="form_input"/>
    </p>

    <div id="label_above_elements">
      <label for="email" class="label">
        <spring:message code="registration.label.email" text="Email - required, verified but never shown"/>
      </label>
    </div>
    <p>
      <form:input path="email" id="email" class="form_input"/>
      <form:errors path="email" cssClass="error_form_validation"/>
    </p>

    <div id="label_above_elements">
      <label for="topic" class="label">
        <spring:message code="contact.topic" text="Topic"/>
      </label>
    </div>
    <p>
      <form:select path="topic" multiple="false" id="topic" class="form_input">
        <c:forEach items="${topics}" var="topic">
          <form:option value="${topic}">
            <spring:message code="${topic}"/>
          </form:option>
        </c:forEach>
      </form:select>
    </p>

    <div id="label_above_elements">
      <label for="message" class="label">
        <spring:message code="contact.your_message" text="Your message"/>
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
        ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LcW3OASAAAAAKEJTHMmp_bo5kny4lZXeDtgcMqC",
          "6LcW3OASAAAAAKVX2duVsSy2uMMHL105-jPDrHMD", false);
        out.print(c.createRecaptchaHtml(null, null));
      %>
    </div>

    <spring:message var="send_mess_btn" code="contact.send_message" text="Post comment"/>
    <input type="submit" value="${send_mess_btn}"  id="send_message" class="submit_form_button"/>
    <div class="clear"></div>
  </form:form>
</div>
