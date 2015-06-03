<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">   
	$(document).ready(function() {
		      
    	//init subscriptionfileds fields
    	var subscriptionEmail = $("input#sub_email");
    	
    	//when clicking on subscribe via email, subscribe via email checkbox from comments is checked 
    	$("#subscribeItAnchor").click(function(){
    		$("#subscribe-form" ).dialog("open");
    	});
    	
    	 $("#subscribe-form" ).dialog({
    		 autoOpen: false,
    		 height: 200,
    		 width: 470,
    		 modal: true,
    		 buttons: {
    		 	"Subscribe": function() {
      		        var bValid=true;
      		        subscriptionEmail.removeClass( "input_in_error" );
      		      	$("#subscription_email_err_mess").remove();
      		        bValid = bValid && checkLength( subscriptionEmail, "email", 6, 80 );
      		        bValid = bValid && checkRegexp( subscriptionEmail, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "e.g. test@podcastpedia.org");  		        
      		        if(bValid){    		 		
    		 			postSubscription();
      		        } else {
      		        	subscriptionEmail.after("<br/><span id='subscription_email_err_mess' class='error_form_validation'><spring:message code='invalid.required.email'/></span>");
      		        	return false; 
      		        }
    		 	 },
	    		 Cancel: function() { $( this ).dialog( "close" ); }
   			 },    		 
    		 close: function() {
    			 //we reset the values 
    			 subscriptionEmail.val("").removeClass("input_in_error");
    			 $("#subscription_email_err_mess").remove();    			 
    		 }
    	});
    	 
		$( "#dialog-subscribed" ).dialog({
			 autoOpen: false,
     		 height: 150,
    		 width: 450,
  			 modal: true
  			 /* when I add button it doesn't show correctly investigate why...
 	  		 buttons: {
 	  			'Ok': function() {
 	  				$( this ).dialog( "close" );
 	  			}
 	  		 }	
			*/
  		});    	 
		
        function postSubscription(){
  		  // Call a URL and pass two arguments
  		  // Also pass a call back function
  		  // See http://api.jquery.com/jQuery.post/
  		  // See http://api.jquery.com/jQuery.ajax/
  		  // You might find a warning in Firefox: Warning: Unexpected token in attribute selector: '!'
  		  // See http://bugs.jquery.com/ticket/7535
  		  // ATTENTION - project name must be included in the path 
  		  $.post("/email-subscription",
  		       {  
  			  		email:  $("input#sub_email").val(),
 		        	podcastId:  $("input#sub_podcastId").val() 
  		        },
  		        function(data){	  		        	
  		        	//we have received valid response 
	  	  			$("#subscribe-form" ).dialog( "close" );
		  	  		$(function() {
		  	  			$( "#dialog-subscribed" ).dialog("open");
		  	  			setTimeout(function() { $( "#dialog-subscribed" ).dialog('close'); }, 5000);  	  			
		  	  		});

  		   		});        	
        }
                
        function checkLength( o, n, min, max ) {
        	if ( o.val().length > max || o.val().length < min ) {
	        	o.addClass( "input_in_error" );        	
	        	return false;
        	} else {
        		return true;
        	}
        }
        
        function checkRegexp( o, regexp, n  ) {
        	if ( !( regexp.test( o.val() ) ) ) {
        		o.addClass( "input_in_error" );
        		return false;
        	} else {
        		return true;
        	}
        }
            	
    });  
</script>	