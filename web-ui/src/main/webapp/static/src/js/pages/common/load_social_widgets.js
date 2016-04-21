
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
