window.fbAsyncInit = function () {
    console.info('Initializing facebook 1.32');
    FB.init({
          appId: '562717817086083', // App ID
          channelUrl: '//infoplus.pieterdb.be/channel.html', // Channel File
          status: true, // check login status
          cookie: true, // enable cookies to allow the server to access the session
          xfbml: true  // parse XFBML
      });

      function subscribed(response) {
          if (response.status == 'connected') {
              console.info("Enabling facebookmenubutton");
              $("#facebookMenuButton").show(300, 'easeInBack');
          }
          console.info("Status of fb session: >" + response.status + "<");
      }
      FB.Event.subscribe('auth.statusChange', subscribed);
  };

// Load the SDK Asynchronously
(function(d){
    var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement('script'); js.id = id; js.async = true;
    js.src = "//connect.facebook.net/en_US/all.js";
    ref.parentNode.insertBefore(js, ref);
}(document));

function loginFB() {
    $("#facebookMenuButton").show(300, 'easeInBack');
    FB.login(function (response) { }, { scope: 'publish_stream' });
}

function sendToFacebook() {
    var data =
    {
        message: $("#notificationInfo").val(),
        name:
            'Er is ' +
            getReadableType($("#notifactionType").val()) +
            '!',
        picture: "http://infoplus.pieterdb.be/mijnBuurt/img/icon/marker/roadblock.png",
    };

    console.info('Feed body: ' + postMessage);
    FB.api('/me/feed', 'post',data, function (response) {
        if (!response) {
            console.error("No response from facebook");
            return false;
        } else if (response.error) {
            console.error("Facebook whines");
            return false;
        }else{
            console.info("Posted on facebook");
            return true;
        }

    });
}