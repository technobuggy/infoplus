console.info('required.js 1.05');
// Wordt in elke mijnBuurt pagina geladen
//properties
var EASEINMETHOD = "easeInBack";
var EASEOUTMETHOD = "easeOutBack";
var EASETIME = 300;

//functions
fullScreen();

//popupmenu tonen
function popUp(targetMenu) {
    //alle popupmenus sluiten
    $(".popupMenu").hide(EASETIME, EASEOUTMETHOD);
    //alle submenus sluiten
    $(".subMenu").hide(EASETIME, EASEOUTMETHOD);
	if($(targetMenu).is(':visible')) {
	    $(targetMenu).hide(EASETIME, EASEINMETHOD);
	} else {	
	    $(targetMenu).show(EASETIME, EASEOUTMETHOD);
	}
}
function replaceDiv(activeDiv, hideDiv) {
    //alle popupmenus sluiten
    $(".popupMenu").hide(EASETIME, EASEOUTMETHOD);
    //alle submenus sluiten
    $(".subMenu").hide(EASETIME, EASEOUTMETHOD);
    $(activeDiv).slideDown(EASETIME*2, EASEINMETHOD);
    $(hideDiv).slideDown(EASETIME * 2, EASEOUTMETHOD);
    fullScreen();
}
function showSubMenu(targetSubMenu) {
    //alle submenus sluiten
    //$(".subMenu").hide(EASETIME, EASEOUTMETHOD);
    if ($(targetSubMenu).is(':visible')) {
        $(targetSubMenu).hide(EASETIME, EASEINMETHOD);
    } else {
        $(targetSubMenu).show(EASETIME, EASEOUTMETHOD);
    }
}
//Fluid functies
//overbodige zaken weglaten bij een klein scherm
function showVolatile() {
    if ($(window).width() < 325 || $(window).width() < 500) {
        $(".volatile").fadeOut(300);
    } else {
        $(".volatile").fadeIn(300);
    }
}
//Objecten fullscreen height geven
function fullScreen() {
    $(".fullScreen").height(
        $(window).height() - 110);
    $(".semiFullScreen").height(
        $(window).height() - 160);
    
}
//Functies die triggeren als de afmetingen van het scherm veranderen
$(window).resize(function () {
    showVolatile();
    fullScreen();
});
window.onload = function ()
{
    getLocation();
    showVolatile();
    fullScreen();
    
};

//social
//twitter reverse geocoding: huidige latlng waarden omzetten naar een leesbaar adres

function setTweet() {
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({ 'latLng': getCurrentLatLng() }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                $('#tweetText').val('Gebruikt MijnBuurt in ' +
                    results[1].formatted_address
                );

            } else {
                console.error("Geocoder: No results found");
            }
        } else {
            console.error("Geocoder failed due to: " + status);
        }
    });
}

//latlng functies
function storeLatLng(storageKey, latlng) {
    //Latitude waarde apart bewaren
    localStorage.setItem(
        storageKey + '_lat',
        latlng.lat()
    );
    //Longtitude waarde apart bewaren
    localStorage.setItem(
        storageKey + '_lng',
        latlng.lng()
    );
}
function getLatLng(storageKey) {
    if (localStorage.getItem((storageKey + '_lat')) == null) {
        console.error("Er is geen lat waarde voor " + storageKey);
        return null;
    }
    console.info("raw lat value for " + storageKey + ":\t" + localStorage.getItem(storageKey + '_lat'));
    console.info("raw lng value for " + storageKey + ":\t" + localStorage.getItem(storageKey + '_lng'));
    
    var output = new google.maps.LatLng(
        localStorage.getItem(storageKey + '_lat'),
        localStorage.getItem(storageKey + '_lng')
    );
    console.info("returning: " + output);
    return output;
}

//localstorage
function switchOption(option, checkbox) {
    localStorage.setItem(option, checkbox.checked);
    console.info('Changing ' + option + ' to ' + checkbox.checked);
}
function saveItem(option, saveValue) {
    localStorage.setItem(option, saveValue);
}
function loadItem(option){
    var output = localStorage.getItem(option);
    if (output == null) {
        console.error("Option " + option + " wasn't stored in localStorage.");
        return null;
    } else {
        return output;
    }
}
function getBoolean(option) {
    var val = localStorage.getItem(option);
    if (val == null) {
        console.error("Value " + option + " doesn't exist");
        return null;
    } else if (val == "false") {
        return false;
    } else if (val == "true") {
        return true;
    } else {
        console.error("Value " + option + " doesn't seem to be a boolean");
        return null;
    }
}

//Een leesbaar notificatieType doorgeven
function getReadableType(notificationType) {
    if (notificationType == "DangerousSituation") {
        return "een hindernis op de baan";
    } else if (notificationType == "Litter") {
        return "vuilnis op/langs de weg";
    } else if (notificationType == "RoadSurface") {
        return "een probleem met het wegdek";
    } else if (notificationType == "NeighbourhoodEvent") {
        return "een buurtevenement aan de gang";
    } else if (notificationType == "NeighbourhoodAction") {
        return "een buurtactie aan de gang";
    } else {
        console.error("Fout notificatieType doorgegeven: " + notificationType);
        return "een algemeen probleem";
    }
}

function postNotification() {
    $('#sendStatus').fadeIn(EASETIME, EASEOUTMETHOD);
    var postStatusSpan = $("#postStatus");

    postStatusSpan.css("color: cornsilk");
    postStatusSpan.text("Verzenden naar MijnBuurt");

    var statusText = "";
    
    if (localStorage.getItem('postToFacebook') == 'false') {
        console.info('post to facebook disabled');
        return false; //pagina niet herladen indien false maar wel de methode afbreken
    } else {
        console.info('post to facebook enabled');
        postStatusSpan.text("Verzenden naar Facebook");
        if (!sendToFacebook()) {
            statusText = "Controlleer uw facebookgegevens";
        }
    }

    if (statusText == "") {
        statusText = "Notificatie succesvol verzonden!";
        postStatusSpan.css("color: green");
    } else {
        postStatusSpan.css("color: red");
    }
}
    //geolocatie ophalen
    function getLocation(){
        if (navigator.geolocation){
            navigator.geolocation.getCurrentPosition(showPosition);
        }
        else{console.log("Gebruik neker nen deftigen browser noob!");
        }
    }
    
    function showPosition(position){
        var lat = position.coords.latitude; 
        var long =  position.coords.longitude; 
        localStorage.setItem("latitude", lat);
        localStorage.setItem("longitude", long);
        var lat = parseFloat(localStorage.getItem("latitude"));
        var long = parseFloat(localStorage.getItem("longitude"));
        console.log("latitude test 1 = " + lat);
        console.log("longitude test 1= " + long);
    }


