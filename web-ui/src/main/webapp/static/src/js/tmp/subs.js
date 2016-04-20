$(function (){

  /*
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
   */

  bindShowLastEpisodes();
  //delegate again...
  function bindShowLastEpisodes(){
    $('.podcast_wrapper').delegate('.icon-last-episodes', 'click',  function () {
      var currentDiv=$(this).closest("div.podcast_wrapper");
      var lastEpisodesDiv = currentDiv.find("div.last_episodes");
      var lastEpisodesButton = currentDiv.find("a.icon-last-episodes");
      if(lastEpisodesDiv.hasClass('not_shown')){
        lastEpisodesDiv.removeClass('not_shown').addClass('shown');
        lastEpisodesButton.removeClass('icon-arrow-down').addClass('icon-arrow-up');
      } else {
        lastEpisodesDiv.removeClass('shown').addClass('not_shown');
        lastEpisodesButton.removeClass('icon-arrow-up').addClass('icon-arrow-down');
      }
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
          //+ " <div class='fb-like' data-href='"+ epUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
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

  bindDynamicSocialSharingPodcasts();
  function bindDynamicSocialSharingPodcasts(){
    $('.podcast_wrapper').on('click', '.icon-share-podcast', function (e) {
      var currentDiv=$(this).closest("div.podcast_wrapper");
      var podcastUrl= currentDiv.find("span.podcast_url").text();
      var podcastTitle= currentDiv.find("span.podcast_title").text();

      currentDiv.find("div.last_episodes").css("margin-bottom","70px");
      currentDiv.find("div.pod_desc").css("margin-bottom","55px");
      currentDiv.find("div.pod_desc_bigger").css("margin-bottom","55px");

      //the share button is being replaced with social media buttons
      $(e.target).remove();
      var socialAndDownload = currentDiv.find("div.social_and_download_podcast");
      socialAndDownload.prepend(
        "<div class='share_buttons'>"
        + "<div class='fb_like'>"
          //+ " <div class='fb-like' data-href='"+ podcastUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
        + " <div class='fb-share-button' data-href='"+ podcastUrl + " data-send='false' data-layout='button' data-mobile-iframe='true'></div>"
        + "</div> "
        + "<div class='twitter_share'> "
        + " <a href='//twitter.com/intent/tweet' class='twitter-share-button' data-url='" + podcastUrl + "' data-text='"+ podcastTitle +"' data-via='podcastpedia'>Tweet</a>"
        + "</div>"
        + "<div class='google_share'> "
        + "  <div class='g-plusone' data-size='medium' data-annotation='none' data-href='" + podcastUrl + "'></div>"
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

  // ***** Podcast subscribtion part ******
  $("#ask-for-login-form").dialog({autoOpen: false, modal: true});
  //when clicking on subscribe via email, subscribe via email checkbox from comments is checked
  $(".ask-for-login").click(function(){
    var theDiv = $("#ask-for-login-form");
    theDiv.dialog("open");
    $(theDiv).dialog({
      autoOpen: false,
      height: 200,
      width: 370,
      modal: true,
      buttons: {
        "Log in": function() {
          var url = window.location.href;
          var arr = url.split("/");
          var protocol= arr[0];
          var hostAndPort=arr[2];
          window.location.href = "//" + hostAndPort + "/login/custom_login";
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      },
      close: function() {

      }
    });
  });


  $('div.subscribe-form-subscription-category-cls').dialog({autoOpen: false, modal: true});
  $(".subscribe-to-podcast-cls").click(function(){
    //var theDiv=$(this).next("div.subscribe-form-subscription-category");
    var divId=this.id.replace('subscribe-to-podcast','');
    var theDiv=$('#subscribe-form-subscription-category' + divId);
    theDiv.dialog("open");
    $(theDiv).dialog({
      autoOpen: false,
      height: 220,
      width: 370,
      modal: true,
      buttons: {
        "Submit": function() {
          var inputNewSubscriptionCategory = $("input#newSubscriptionCategory" + divId);
          var selectedSubscriptionCategoryValue = $("#subscription_categories"+ divId + " option:selected").val();
          var xorInputOrSelectSubscriptionCategory = (selectedSubscriptionCategoryValue != "" && inputNewSubscriptionCategory.val() == "") ||(selectedSubscriptionCategoryValue == "" && inputNewSubscriptionCategory.val() != "");
          if(xorInputOrSelectSubscriptionCategory){
            postSubscribeToSubscriptionCategory(divId);
          } else {
            inputNewSubscriptionCategory.after("<br/><span style='color: red'>Please either add to existing category OR create a new one</span>");
            return false;
          }
        },
        Cancel: function() { $( this ).dialog( "close" ); }
      },
      close: function() {
      }
    });
  });

  //when clicking on subscribe via email, subscribe via email checkbox from comments is checked
  //$("#subscribe-to-podcast").click(function(){
  function postSubscribeToSubscriptionCategory(divId){
    $.post("/users/subscriptions",
      {
        podcastId:  $("input#sub_podcastId" + divId).val(),
        _csrf: $( "input[name='_csrf']" ).val(),
        newSubscriptionCategory: $( "#newSubscriptionCategory" + divId).val(),
        existingSubscriptionCategory: $( "#subscription_categories" + divId).val()
      },

      function(data){
        //we have received valid response
        $("#subscribe-to-podcast"+divId).css({ 'background-color': '#185B8B' });
        $("#subscribe-to-podcast"+divId).css({ 'pointer-events': 'none' });
        $("#subscribe-form-subscription-category"+divId).dialog("close");
      });
  }

});
