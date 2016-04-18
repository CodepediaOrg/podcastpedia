//TODO - before considering this functionality, check out i18n in Javascript, we have here new download etc...
$(function (){
	var moreResultsBtn = $('#more-results');

	//more button functionality - cannot make up my mind...
	$(moreResultsBtn).click(function(){

		var $resultsList=$('.results_list');
		var queryStringDataId=$('#queryString-data-id');
		var queryString=queryStringDataId.val();
		var currentPageDataId=$('#currentPage-data-id');
		var currentPage=parseInt(currentPageDataId.val()) + 1;

		$.ajax({
			headers: {"Accept":"application/json"},
			type: 'GET',
			url: '/api/search-results?' + queryString+ '&currentPage=' + currentPage,
			success: function(result){
				var episodes = result.episodes;
				if(episodes.length == 0){
					moreResultsBtn.attr("disabled","disabled");
				} else {
					currentPageDataId.val(currentPage);//update offset hidden field value
					$.each(episodes, function(i, episode){
						var episodeDiv = buildEpisodeDiv(episode, i, currentPage);
						$resultsList.append(episodeDiv).fadeIn(800);
					});

					bindDynamicPlaying();
					bindDynamicSocialSharing();
					bindDynamicSharringCurrentEpisode();
					console.log("success", episodes);
				}
			}
		});

	});

  // same code as the one in player_dialog.js, but have to reload it here because of the "more episodes" possibility -
  bindDynamicPlaying()
  function bindDynamicPlaying(){
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
    var episodeUrl=getDomainAndPort() + '/podcasts/' + episode.podcastId + '/' + episode.podcast.titleInUrl + '/episodes/' + episode.episodeId + '/' + episode.titleInUrl;
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

  function getDomainAndPort(){
    var url = window.location.href;
    var arr = url.split("/");
    var protocol= arr[0];
    var hostAndPort=arr[2];

    return protocol + "//" + hostAndPort;
  }

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


	bindDynamicSocialSharing();
	function bindDynamicSocialSharing(){
		$('.item_wrapper').on('click', '.icon-share-episode', function (e) {
			var currentDiv=$(this).closest("div.item_wrapper");
			var epUrl= currentDiv.find("span.item_url").text();
      var epTitle= currentDiv.find("span.item_sharing_title").text();

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
         + " <div class='fb-share-button' data-href='"+ epUrl + " data-send='false' data-layout='button' data-mobile-iframe='true'></div>"
					+ "</div> "
					+ "<div class='twitter_share'> "
         + " <a href='//twitter.com/intent/tweet' class='twitter-share-button' data-url='" + epUrl + "' data-text='"+ epTitle +"' data-via='podcastpedia'>Tweet</a>"
					+ "</div>"
					+ "<div class='google_share'> "
					+ "  <div class='g-plusone' data-size='medium' data-annotation='none' data-href='" + epUrl + "'></div>"
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
			var currentEpUrl= currentDiv.find("span.item_url_ep").text();
      var currentEpTitle= currentDiv.find("span.item_title_ep").text();

			//widen the distance to insert the sharing buttons
			$('#current_episode').find(".ep_desc").css("margin-bottom","75px");
			$('#current_episode').find(".ep_desc_bigger").css("margin-bottom","75px");

			//the share button is being replaced with social media buttons
			$(e.target).remove();
			currentDiv.prepend(
				 "<div class='share_buttons'>"
					+ "<div class='fb_like'>"
					+ " <div class='fb-share-button' data-href='"+ currentEpUrl + " data-send='false' data-layout='button' data-mobile-iframe='true'></div>"
					+ "</div> "
					+ "<div class='twitter_share'> "
					//+ " <a href='//twitter.com/share' class='twitter-share-button' data-url='" + currentEpUrl + "' data-hashtags='podcastpedia'>Tweet</a>"
          + " <a href='//twitter.com/intent/tweet' class='twitter-share-button' data-url='" + currentEpUrl + "' data-text='"+ currentEpTitle +"' data-via='podcastpedia'>Tweet</a>"
					+ "</div>"
					+ "<div class='google_share'> "
					+ "  <div class='g-plusone' data-size='medium' data-annotation='none' data-href='" + currentEpUrl + "'></div>"
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
	        $.getScript("//connect.facebook.net/en_US/all.js#xfbml=1&version=v2.6&appId=1440349946220626", function () {
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

});

