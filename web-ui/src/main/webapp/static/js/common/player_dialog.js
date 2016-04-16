$(document).ready(function() {

  /* ***** Setup jwplayer in jquery dialog part ******
    when modify make sure to update the implementation in podcast detail's main.js too
   */
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

});
