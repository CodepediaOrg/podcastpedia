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
					bindDynamicSocialSharingEpisodes();
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
        window.location.href = getDomainAndPort() + "/login/custom_login";
      },
      Cancel: function() {
        $( this ).dialog( "close" );
      }
    },
    close: function() {

    }
  });

  function getDomainAndPort(){
    var url = window.location.href;
    var arr = url.split("/");
    var protocol= arr[0];
    var hostAndPort=arr[2];

    return protocol + "//" + hostAndPort;
  }

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

