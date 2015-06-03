<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<script type="text/javascript">   
	$(document).ready(function() {	
		
		$('.item_wrapper').on('click', '.icon-share-podcast', function (e) {
			var currentDiv=$(this).closest("div.item_wrapper");
			var epUrl= currentDiv.find("span.item_url").text();
						
			//the share button is being replaced with social media buttons
			$(e.target).remove();
			var socialAndDownload = currentDiv.find("div.social_and_download");
			socialAndDownload.prepend(
				"<div class='fb_like'>"
				+ " <div class='fb-like' data-href='"+ epUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
				+ "</div> "						
				+ "<div class='twitter_share'> "
				+ " <a href='https://twitter.com/share' class='twitter-share-button' data-url='" + epUrl + "' data-hashtags='podcastpedia'>Tweet</a>"	
				+ "</div>"
				+ "<div class='google_share'> "					
				+ "  <div class='g-plusone' data-size='medium' data-annotation='bubble' data-href='" + epUrl + "'></div>"						
			    + "</div>"
			);
			
			loadTwitter();
			loadFacebook();
			loadGooglePlus();
			
		 });
					
		function loadTwitter()
		{
		    if (typeof (twttr) != 'undefined'){
		        twttr.widgets.load();
		    } else {
		        $.getScript('http://platform.twitter.com/widgets.js');
		    }
		}		
		
		function loadFacebook()
		{
		    if (typeof (FB) != 'undefined') {
		        FB.init({ status: true, cookie: true, xfbml: true });
		    } else {
		        $.getScript("http://connect.facebook.net/en_US/all.js#xfbml=1", function () {
		            FB.init({ status: true, cookie: true, xfbml: true });
		        });
		    }
		}		
		
		function loadGooglePlus()
		{
			var gbuttons = $(".g-plusone");
			if (gbuttons.length > 0) {
			    if (typeof (gapi) != 'undefined') {
			        gbuttons.each(function () {
			            gapi.plusone.render($(this).get(0));
			        });
			    } else {
			        $.getScript('https://apis.google.com/js/plusone.js');
			    }
			}
		}		
    });  
</script>