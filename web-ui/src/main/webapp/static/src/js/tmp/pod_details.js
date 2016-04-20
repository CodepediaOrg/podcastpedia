
bindShowLastEpisodes();
//delegate again...
function bindShowLastEpisodes(){
  $('.podcast_wrapper').on('click', '.icon-last-episodes', function () {
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

bindDynamicSocialSharingPodcasts();
function bindDynamicSocialSharingPodcasts(){
  $('.podcast_wrapper').on('click', '.icon-share-podcast', function (e) {
    var currentDiv=$(this).closest("div.podcast_wrapper");
    var podcastUrl= currentDiv.find("span.podcast_url").text();
    var podcastTitle= currentDiv.find("span.podcast_title").text();

    currentDiv.find("div.last_episodes").css("margin-bottom","70px");

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

