$(function (){
	var moreEpisodesBtn = $('#more-episodes');
	moreEpisodesBtn.on('click', function(){

		var $lastEpisodes=$('.results_list');
		var podcastId=$('#sub_podcastId').val();

		var offsetDataId=$('#offset-data-id');
		var offset=parseInt(offsetDataId.val());
		var count = 5;

		$.ajax({
			headers: {"Accept":"application/json"},
			type: 'GET',
			url: '/api/podcasts/'+ podcastId + '/episodes?offset='+ offset + '&count=' + count,
			success: function(episodes){
				if(episodes.length == 0){
					moreEpisodesBtn.attr("disabled","disabled");
          moreEpisodesBtn.remove();
				} else {
					var newoffset = offset + 5;
					offsetDataId.val(newoffset);//update offset hidden field value
					$.each(episodes, function(i, episode){
						var episodeDiv = buildEpisodeDiv(episode, i, offset);
						$lastEpisodes.append(episodeDiv).fadeIn(800);
					});

					bindDynamicPlaying();
					bindDynamicSocialSharing();
					bindDynamicSharringCurrentEpisode();
					console.log("success", episodes);
				}
			}
		});

	});

	function buildEpisodeDiv(episode, i, offset){
		var episodeDiv=""
			+ "<div class='bg_color shadowy item_wrapper'>"
			+ "<div class='title-and-pub-date'>";
			if(episode.mediaType == 'Audio'){
				episodeDiv += "<div class='icon-audio-episode'></div>";
			} else {
				episodeDiv += "<div class='icon-video-episode'></div>";
			}
			var episodeUrl='http://www.podcastpedia.org/podcasts/' + episode.podcastId + '/' + episode.podcast.titleInUrl + '/episodes/' + episode.episodeId + '/' + episode.titleInUrl;
			episodeDiv += '<a href='+ episodeUrl +' class="item_title">' + episode.title + '</a>';

			episodeDiv +='<div class="pub_date">';
        var epPubDate = new Date(episode.publicationDate);
        episodeDiv += epPubDate.getFullYear() + "-" + epPubDate.getMonth() + "-" + epPubDate.getDate();
        if(episode.isNew === 1){
          episodeDiv +='<span class="ep_is_new">&nbsp;new</span>';//TODO see what's up with internationalization
        }
			episodeDiv +='</div>';

			episodeDiv += '<div class="clear"></div>';
			episodeDiv +='</div>';
			episodeDiv +='<hr>';
			episodeDiv +='<div class="ep_desc">';
        episodeDiv += '<a href='+ episodeUrl +' class="item_desc">';
          if(episode.description != null) episodeDiv += episode.description.substring(0,280);
        episodeDiv +='</a>';
			episodeDiv +='</div>';

			episodeDiv +='<div class="ep_desc_bigger">';
        episodeDiv +='<a href='+ episodeUrl +' class="item_desc">';
          if(episode.description != null) episodeDiv += episode.description.substring(0,600);
        episodeDiv +='</a>';
			episodeDiv +='</div>';

			episodeDiv +='<div class="clear"></div>';

			episodeDiv +='<div class="not_shown">';
			episodeDiv +='<div id=mediaspace'+parseInt(offset+i) +'>Flashplayer not supported</div>';


			//change with requireJs sometime soon
			episodeDiv +="<script type='text/javascript'>";
			if(episode.mediaType == 'Audio'){
				episodeDiv +="jwplayer(mediaspace"+ parseInt(offset+i) + ").setup({'controlbar': 'bottom',  'width': '100%',   'aspectratio': '16:5', 'file': '" + episode.mediaUrl+ "'});";
			} else {
				episodeDiv +="jwplayer(mediaspace"+ parseInt(offset+i) + ").setup({'controlbar': 'bottom',  'width': '100%',   'aspectratio': '16:9', 'file': '" + episode.mediaUrl+ "'});";
			}
			episodeDiv +="</script>";
			episodeDiv +='</div>';

			episodeDiv +='<div class="social_and_download">';
			episodeDiv +='<a href="#' + parseInt(2*(offset+i)) + '" class="icon-play-episode btn-share">Play </a>';
			episodeDiv +='<a href="#' + parseInt(2*(offset+i)+1) + '" class="icon-share-episode btn-share">Share </a>';
			episodeDiv+= '<a class="icon-download-ep btn-share" href="'+ episode.mediaUrl +'" target="_blank">';
			episodeDiv+= 'download&nbsp;';
			episodeDiv +='</a>';
			episodeDiv +='<span class="item_url">' + episodeUrl + '</span>';
			episodeDiv +='</div>';

			return episodeDiv;
	}


	bindDynamicPlaying();
	//delegate again...
	function bindDynamicPlaying(){
		$('.item_wrapper').delegate('.icon-play-episode', 'click',  function () {
			var currentDiv=$(this).closest("div.item_wrapper");
			var playerDiv = currentDiv.find("div.not_shown")
			playerDiv.removeClass('not_shown').addClass('shown');
			var jwpId = currentDiv.find("div.jwplayer ").attr("id")

			// if we load the player, the div containing the player will set the distance to the share, play and download buttons
			currentDiv.find("div.ep_desc").css("margin-bottom","5px");

			//and also widen the distance to the share buttonsif they are available
			var shareButtonsDiv = currentDiv.find("div.share_buttons");
			if(shareButtonsDiv.length > 0){
				playerDiv.css("margin-bottom","75px");
			}

			$('html, body').animate({
				scrollTop: currentDiv.offset().top
			}, 150);
			if(typeof jwpId != 'undefined'){
				jwplayer(jwpId).play();
			}
		 });
	}


	bindDynamicSocialSharing();
	function bindDynamicSocialSharing(){
		$('.item_wrapper').on('click', '.icon-share-episode', function (e) {
			var currentDiv=$(this).closest("div.item_wrapper");
			var epUrl= currentDiv.find("span.item_url").text();

			//verify existence of player to manipulate distance to div containing sharing buttons
			var playerDiv = currentDiv.find("div.shown");
			if(playerDiv.length > 0){
				playerDiv.css("margin-bottom","75px");
			} else {
				//player not shown widen the distance to insert the sharing buttons
				currentDiv.find("div.ep_desc").css("margin-bottom","75px");
				currentDiv.find("div.ep_desc_bigger").css("margin-bottom","75px");
				currentDiv.find("div.pod_desc").css("margin-bottom","55px");
				currentDiv.find("div.pod_desc_bigger").css("margin-bottom","55px");
			}
			//the share button is being replaced with social media buttons
			$(e.target).remove();
			var socialAndDownload = currentDiv.find("div.social_and_download");
			socialAndDownload.prepend(
       "<div class='share_buttons'>"
        + "<div class='fb_like'>"
        + " <div class='fb-like' data-href='"+ epUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
        + "</div> "
        + "<div class='twitter_share'> "
        + " <a href='https://twitter.com/share' class='twitter-share-button' data-url='" + epUrl + "' data-hashtags='podcastpedia'>Tweet</a>"
        + "</div>"
        + "<div class='google_share'> "
        + "  <div class='g-plusone' data-size='medium' data-annotation='bubble' data-href='" + epUrl + "'></div>"
          + "</div>"
        + "</div>"
			);

			loadTwitter();
			loadFacebook();
			loadGooglePlus();

		 });
	}

	bindDynamicSharringCurrentEpisode();
	function bindDynamicSharringCurrentEpisode(){
		$('#social_and_download_curr_ep').on('click', '.icon-share-episode', function (e) {
			var currentDiv=$(this).closest("div#social_and_download_curr_ep");
			var epUrl= currentDiv.find("span.item_url_ep").text();

			//widen the distance to insert the sharing buttons
			$('#current_episode').find(".ep_desc").css("margin-bottom","75px");
			$('#current_episode').find(".ep_desc_bigger").css("margin-bottom","75px");

			//the share button is being replaced with social media buttons
			$(e.target).remove();
			currentDiv.prepend(
				 "<div class='share_buttons'>"
					+ "<div class='fb_like'>"
					+ " <div class='fb-like' data-href='"+ epUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
					+ "</div> "
					+ "<div class='twitter_share'> "
					+ " <a href='https://twitter.com/share' class='twitter-share-button' data-url='" + epUrl + "' data-hashtags='podcastpedia'>Tweet</a>"
					+ "</div>"
					+ "<div class='google_share'> "
					+ "  <div class='g-plusone' data-size='medium' data-annotation='bubble' data-href='" + epUrl + "'></div>"
				    + "</div>"
			    + "</div>"
			);

			loadTwitter();
			loadFacebook();
			loadGooglePlus();

		 });

	}

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

    //email subscription part to be moved to separate file
    //init subscriptionfileds fields
    var subscriptionEmail = $("input#sub_email");

    //when clicking on subscribe via email, subscribe via email checkbox from comments is checked
    $("#subscribeItAnchor").click(function(){
        $("#subscribe-form" ).dialog("open");
    });

    $("#subscribe-form" ).dialog({
        autoOpen: false,
        height: 200,
        width: 370,
        modal: true,
        buttons: {
          "Subscribe": function() {
              var bValid=true;
              subscriptionEmail.removeClass( "input_in_error" );
              $("#subscription_email_err_mess").remove();
              bValid = bValid && checkLength( subscriptionEmail, "email", 6, 80 );
              bValid = bValid && checkRegexp( subscriptionEmail, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "e.g. test@podcastpedia.org");
            if(bValid){
                postEmailSubscription();
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

  //when clicking on subscribe via email, subscribe via email checkbox from comments is checked
  $("#ask-for-login").click(function(){
    $("#ask-for-login-form" ).dialog("open");
  });

  //when clicking on subscribe via email, subscribe via email checkbox from comments is checked
  $("#subscribe-to-podcast").click(function(){
    $.post("/users/subscriptions",
      {
        podcastId:  $("input#sub_podcastId").val(),
        _csrf: $( "input[name='_csrf']" ).val()
      },

      function(data){
        //we have received valid response
        $("#subscribe-to-podcast").text("Subscribed")
      });
  });

  $("#ask-for-login-form" ).dialog({
    autoOpen: false,
    //height: 200,
    //width: 370,
    modal: true,
    buttons: {
      "Log in": function() {
        $( this ).dialog("close");
        window.location.href = "http://localhost:8008/login/custom_login";
      },
      Cancel: function() {
        $( this ).dialog( "close" );
      }
    },
    close: function() {

    }
  });

  function postEmailSubscription(){
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

