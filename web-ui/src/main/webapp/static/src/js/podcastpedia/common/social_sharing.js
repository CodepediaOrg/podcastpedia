$('.podcast_wrapper').on('click', '.icon-share-podcast', function (e) {

  var currentDiv=$(this).closest("div.podcast_wrapper");
  var lastEpisodesDiv = currentDiv.find("div.last_episodes")
  var podcastUrl= currentDiv.find("span.podcast_url").text();
  var podcastTitle= currentDiv.find("span.podcast_title").text();

  var lastEpisodesAreShown = lastEpisodesDiv.hasClass("shown");

  if(lastEpisodesAreShown){
    lastEpisodesDiv.css("margin-bottom","70px");
  } else {
    currentDiv.find("div.pod_desc").css("margin-bottom","70px");
    currentDiv.find("div.pod_desc_bigger").css("margin-bottom","70px");
  }

  //the share button is being replaced with social media buttons
  $(e.target).remove();
  var socialAndDownload = currentDiv.find("div.social_and_download_podcast");
  socialAndDownload.prepend(
    "<div class='share_buttons'>"
    + "<div class='fb_like'>"
      //+ " <div class='fb-like' data-href='"+ podcastUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
      + "<div class='fb-share-button' data-href='" + podcastUrl
        + "' data-layout='button' data-mobile-iframe='true'></div>"
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

bindDynamicSocialSharingEpisodes();
function bindDynamicSocialSharingEpisodes(){
  $('.item_wrapper').on('click', '.icon-share-episode', function (e) {
    var currentDiv=$(this).closest("div.item_wrapper");
    var itemUrl= currentDiv.find("span.item_url").text();
    var itemTitle= currentDiv.find("span.item_sharing_title").text();

    //player not shown widen the distance to insert the sharing buttons
    currentDiv.find("div.ep_desc").css("margin-bottom","75px");
    currentDiv.find("div.ep_desc_bigger").css("margin-bottom","75px");
    //currentDiv.find("div").css("margin-bottom","55px");

    //the share button is being replaced with social media buttons
    $(e.target).remove();
    var socialAndDownload = currentDiv.find("div.social_and_download");
    socialAndDownload.prepend(
      "<div class='share_buttons'>"
      + "<div class='fb_like'>"
        //+ " <div class='fb-share-button' data-href='"+ itemUrl + " data-send='false' data-layout='button' data-mobile-iframe='true'></div>"
      + "<div class='fb-share-button' data-href='" + itemUrl
      + "' data-layout='button' data-mobile-iframe='true'></div>"
      + "</div> "
      + "<div class='twitter_share'> "
      + " <a href='//twitter.com/intent/tweet' class='twitter-share-button' data-url='" + itemUrl + "' data-text='"+ itemTitle +"' data-via='podcastpedia'>Tweet</a>"
      + "</div>"
      + "<div class='google_share'> "
      + "  <div class='g-plusone' data-size='medium' data-annotation='none' data-href='" + itemUrl + "'></div>"
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
      + " <div class='fb-share-button' data-href='"+ currentEpUrl
        + "' data-layout='button' data-mobile-iframe='true'></div>"
      + "</div> "
      + "<div class='twitter_share'> "
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
    $.getScript("//connect.facebook.net/en_US/all.js#xfbml=1&version=v2.6", function () {
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
      $.getScript('//apis.google.com/js/platform.js');
    }
  }
}
