function popUp(e){$(".popupMenu").hide(EASETIME,EASEOUTMETHOD);$(".subMenu").hide(EASETIME,EASEOUTMETHOD);if($(e).is(":visible")){$(e).hide(EASETIME,EASEINMETHOD)}else{$(e).show(EASETIME,EASEOUTMETHOD)}}function showSubMenu(e){if($(e).is(":visible")){$(e).hide(EASETIME,EASEINMETHOD)}else{$(e).show(EASETIME,EASEOUTMETHOD)}}function showVolatile(){if($(window).width()<325||$(window).width()<500){$(".volatile").fadeOut(300)}else{$(".volatile").fadeIn(300)}}function setTweet(){var e=new google.maps.Geocoder;e.geocode({latLng:getCurrentLatLng()},function(e,t){if(t==google.maps.GeocoderStatus.OK){if(e[1]){$("#tweetText").val("Gebruikt MijnBuurt in "+e[1].formatted_address)}else{alert("No results found")}}else{alert("Geocoder failed due to: "+t)}})}var EASEINMETHOD="easeInBack";var EASEOUTMETHOD="easeOutBack";var EASETIME=300;$(window).resize(function(){showVolatile()});window.onload(function(){showVolatile()})