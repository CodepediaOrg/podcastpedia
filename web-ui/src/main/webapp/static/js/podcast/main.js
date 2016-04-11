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

          bindDynamiyPlaying();
					bindDynamicSocialSharing();
					bindDynamicSharringCurrentEpisode();
					console.log("success", episodes);
				}
			}
		});

	});

  // same code as the one in player_dialog.js, but have to reload it here because of the "more episodes" possibility -
  bindDynamiyPlaying()
  function bindDynamiyPlaying(){
    $("#media_player_modal_dialog").dialog({autoOpen: false, modal: true});
    $(".icon-play-episode").click(function(){

      var theDiv=$('#media_player_modal_dialog');
      var windowW= $(window).width();
      var dialogW;
      var overlayImage='audio_overlay_jwplayer.png';
      if(windowW > 1600){
        dialogW = dialogW-300;
        overlayImage='audio_overlay_jwplayer_bigger.png';
      } else if(windowW > 1200){
        dialogW = windowW-200;
        overlayImage='audio_overlay_jwplayer.png';
      } else if(windowW > 720){
        dialogW = windowW-100;
        overlayImage='audio_overlay_jwplayer_small.png';
      } else {
        dialogW = windowW-20;
        overlayImage='audio_overlay_jwplayer_smaller.png';
      }
      var dialogH = (dialogW * 10)/16;

      var epMediaUrl = $(this).siblings('.item_media_url').text();
      var epTitle = $(this).siblings('.item_sharing_title').text();

      //setup player
      var playerInstance = jwplayer("mediaspace_modal");
      playerInstance.setup({
        'controlbar': 'bottom',
        'width': '100%',
        'aspectratio': '16:9',
        'file': epMediaUrl,
        'title': epTitle,
        'autostart': true,
        'image': '/static/images/player_overlay/' + overlayImage
      });

      theDiv.dialog("open");
      $(theDiv).dialog({
        autoOpen: false,
        height: dialogH,
        width: dialogW,
        modal: true,
        title: epTitle,
        open: function(){
          playerInstance.play();
        },
        close: function() {
          playerInstance.remove();
        }
      });
    });
  }


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

			episodeDiv +='<div class="social_and_download">';
			episodeDiv +='<a href="#' + parseInt(2*(offset+i)) + '" class="icon-play-episode btn-share">Play </a>';
			episodeDiv +='<a href="#' + parseInt(2*(offset+i)+1) + '" class="icon-share-episode btn-share">Share </a>';
			episodeDiv+= '<a class="icon-download-ep btn-share" href="'+ episode.mediaUrl +'" download>';
			episodeDiv+= 'download&nbsp;';
			episodeDiv +='</a>';
			episodeDiv +='<span class="item_url">' + episodeUrl + '</span>';
      episodeDiv +='<span class="item_sharing_title">' + episode.title + '</span>';
      episodeDiv +='<span class="item_media_url">' + episode.mediaUrl + '</span>';
			episodeDiv +='</div>';

			return episodeDiv;
	}


	bindDynamicSocialSharing();
	function bindDynamicSocialSharing(){
		$('.item_wrapper').on('click', '.icon-share-episode', function (e) {
			var currentDiv=$(this).closest("div.item_wrapper");
			var epUrl= currentDiv.find("span.item_url").text();

			//the share button is being replaced with social media buttons
			$(e.target).remove();
			var socialAndDownload = currentDiv.find("div.social_and_download");
			socialAndDownload.prepend(
       "<div class='share_buttons'>"
        + "<div class='fb_like'>"
        + " <div class='fb-like' data-href='"+ epUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
        + "</div> "
        + "<div class='twitter_share'> "
        + " <a href='//twitter.com/share' class='twitter-share-button' data-url='" + epUrl + "' data-hashtags='podcastpedia'>Tweet</a>"
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
	        $.getScript('//platform.twitter.com/widgets.js');
	    }
	}

	function loadFacebook()
	{
	    if (typeof (FB) != 'undefined') {
	        FB.init({ status: true, cookie: true, xfbml: true });
	    } else {
	        $.getScript("//connect.facebook.net/en_US/all.js#xfbml=1", function () {
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
		        $.getScript('//apis.google.com/js/plusone.js');
		    }
		}
	}

  $("#subscribe-form-subscription-category" ).dialog({
    autoOpen: false,
    height: 220,
    width: 370,
    modal: true,
    buttons: {
      "Submit": function() {
          var inputNewSubscriptionCategory = $("input#newSubscriptionCategory");
          var selectedSubscriptionCategoryValue = $("#subscription_categories option:selected").val();
          var xorInputOrSelectSubscriptionCategory = (selectedSubscriptionCategoryValue != "" && inputNewSubscriptionCategory.val() == "") ||(selectedSubscriptionCategoryValue == "" && inputNewSubscriptionCategory.val() != "");
          if(xorInputOrSelectSubscriptionCategory){
            postSubscribeToSubscriptionCategory();
          } else {
            inputNewSubscriptionCategory.after("<br/><span style='color: red'>Please either add to existing category OR create a new one</span>");
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

  //when clicking on subscribe via email, subscribe via email checkbox from comments is checked
  $(".ask-for-login").click(function(){
    $("#ask-for-login-form" ).dialog("open");
  });

  $("#ask-for-login-form" ).dialog({
    autoOpen: false,
    //height: 200,
    //width: 370,
    modal: true,
    buttons: {
      "Log in": function() {
        var url = window.location.href;
        var arr = url.split("/");
        var protocol= arr[0];
        var hostAndPort=arr[2];
        window.location.href = "//" + hostAndPort + "/login/custom_login";
        //window.location.href = "//podcastpedia.org/login/custom_login";
      },
      Cancel: function() {
        $( this ).dialog( "close" );
      }
    },
    close: function() {

    }
  });

  $("#subscribe-to-podcast").click(function(){
    $("#subscribe-form-subscription-category").dialog("open");
  });
  //when clicking on subscribe via email, subscribe via email checkbox from comments is checked
  //$("#subscribe-to-podcast").click(function(){
  function postSubscribeToSubscriptionCategory(){
    $.post("/users/subscriptions",
      {
        podcastId:  $("input#sub_podcastId").val(),
        _csrf: $( "input[name='_csrf']" ).val(),
        newSubscriptionCategory: $( "input[name='newSubscriptionCategory']" ).val(),
        existingSubscriptionCategory: $( "#subscription_categories" ).val()
      },

      function(data){
        //we have received valid response
        $("#subscribe-to-podcast").css({ 'background-color': '#185B8B' });
        $("#subscribe-to-podcast").css({ 'pointer-events': 'none' });
        $("#subscribe-form-subscription-category").dialog("close");
      });
  }

  //when clicking on subscribe via email, subscribe via email checkbox from comments is checked
  $("#vote-up-podcast").click(function(){
    $.post("/users/votes/podcasts/vote-up",
      {
        podcastId:  $("input#sub_podcastId").val(),
        _csrf: $( "input[name='_csrf']" ).val()
      },

      function(data){
        //we have received valid response
        var upVotes = $("#vote-up-podcast").text();
        $("#vote-up-podcast").text(parseInt(upVotes) + 1);
        $("#vote-up-podcast").css({ 'pointer-events': 'none' });
      });
  });

  $("#vote-down-podcast").click(function(){
    $.post("/users/votes/podcasts/vote-down",
      {
        podcastId:  $("input#sub_podcastId").val(),
        _csrf: $( "input[name='_csrf']" ).val()
      },

      function(data){
        //we have received valid response
        var downVotes = $("#vote-down-podcast").text();
        $("#vote-down-podcast").text(parseInt(downVotes) + 1);
        $("#vote-down-podcast").css({ 'pointer-events': 'none' });
      });
  });

});

