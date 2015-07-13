<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

 <script type="text/javascript">
 var strings = new Array();
 strings['recaptcha.instructions_visual'] = "<spring:message code='recaptcha.instructions_visual' javaScriptEscape='true'/>";
 strings['recaptcha.instructions_audio'] = "<spring:message code='recaptcha.instructions_audio' javaScriptEscape='true'/>"; 
 strings['recaptcha.play_again'] = "<spring:message code='recaptcha.play_again' javaScriptEscape='true'/>";
 strings['recaptcha.cant_hear_this'] = "<spring:message code='recaptcha.cant_hear_this' javaScriptEscape='true'/>";
 strings['recaptcha.visual_challenge'] = "<spring:message code='recaptcha.visual_challenge' javaScriptEscape='true'/>";
 strings['recaptcha.audio_challenge'] = "<spring:message code='recaptcha.audio_challenge' javaScriptEscape='true'/>";
 strings['recaptcha.refresh_btn'] = "<spring:message code='recaptcha.refresh_btn' javaScriptEscape='true'/>"; 
 strings['recaptcha.help_btn'] = "<spring:message code='recaptcha.help_btn' javaScriptEscape='true'/>";
 strings['recaptcha.incorrect_try_again'] = "<spring:message code='recaptcha.incorrect_try_again' javaScriptEscape='true'/>";

 var RecaptchaOptions = {
    custom_translations : {		 
         instructions_visual :  strings['recaptcha.instructions_visual'] ,
         instructions_audio : strings['recaptcha.instructions_audio'],
         play_again : strings['recaptcha.play_again'],
         cant_hear_this : strings['recaptcha.cant_hear_this'],
         visual_challenge : strings['recaptcha.visual_challenge'],
         audio_challenge : strings['recaptcha.audio_challenge'],
         refresh_btn : strings['recaptcha.refresh_btn'],
         help_btn : strings['recaptcha.help_btn'],
         incorrect_try_again : strings['recaptcha.incorrect_try_again']
 	},		 
    theme : 'clean'
 };
 
 </script>