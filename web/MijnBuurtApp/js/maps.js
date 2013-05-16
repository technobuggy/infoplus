console.info('maps.js 2.41');
//attributes
var MARKERICON = "img/icon/marker/";
var homeLocation;
var map;
var mapOptions;
var lastmarker;
var clickedLatLng;
var clickedGeoObject;
var fileName;
//map canvas
var mapCanvas = $("#map_canvas");
$("#map_preloader").show(0);
$("#map_loadStatus").text("Verbinding maken met google");
//functions
function initialize() {
    
    //de google verbinding is geladen
    $("#map_loadStatus").text("Map laden");
    if (getLatLng("homeAdress") == null) {
        var lat = parseFloat(localStorage.getItem("latitude"));
        var long = parseFloat(localStorage.getItem("longitude"));
        console.log("latitude test 2 = " + lat);
        console.log("longitude test 2= " + long);
        if (lat ==NaN & long == NaN){
            homeLocation = new google.maps.LatLng(50.937595, 4.033542);
        }  else {
            homeLocation = new google.maps.LatLng(lat, long);
        }
    } else {
        homeLocation = getLatLng("homeAdress");
    }

    mapOptions = {
        zoom: 18,
        center: homeLocation,
        disableDoubleClickZoom: mapCanvas.hasClass("noDoubleClick"),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }

    map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
    
    if (!($("#map_canvas").hasClass("noMarkers"))) {
        loadMarkers();
    }
    if ($('#notificatieMenu').length != 0){ //als er een notificatiemenu is
        google.maps.event.addListener(map,'click',function(event) {
            console.log("Registered latlng: " + event.latLng);
            replaceDiv('#notificatieMenu','#map_canvas');
            clickedLatLng = event.latLng;  
            clickedGeoObject = reverseGeocode(event.latLng);
        })
    }
    
    //de preloader wegdoen wanneer de map _zichtbaar_ geladen is
    google.maps.event.addListenerOnce(map, 'idle', function () {
        $("#map_preloader").hide(300);
    });
    

}

//maps asynchroon laden
function loadMarkers() {
    console.info("loading markers");
    $("#map_loadStatus").text("Notificaties laden");
    var url = "http://localhost:8080/onzebuurt/resources/notifications";
    var request = new XMLHttpRequest();
    request.onload = function() {
        if (request.status == 200) {
            
            var notifications = JSON.parse(request.responseText);
            
            for (var i = 0; i < notifications.length; i++){
                //statustext updaten
                $("#map_loadStatus").text("Notificatie " +
                    (i+1) +
                    " van " +
                    (notifications.length) +
                    " laden");
                //marker toevoegen
                createMarker(notifications[i]);
            }
        } else {
            console.error("Notificaties gefaald: " + request.status);
        }
    };
    request.open("GET", url);
    request.send(null);

}

function createMarker(notification) {
    //latlng
    var position = new google.maps.LatLng(
        notification.adres.mapsPos.x,
        notification.adres.mapsPos.y
        );
    //andere attributen
    //var title = getReadableType(notification.eventtype);
    var title = notification.type;
    //console.log(ophalenAantalFeedback(notification));
    var appreciation = ophalenAantalFeedback(notification);
    console.log(appreciation);
    var description = notification.description;
    var icon = MARKERICON + notification.type + ".png";
    var afbeelding = "<img src=http://localhost:8080/onzebuurt/resources/images/" + notification.afbeelding + " />";
    //marker
    var marker = new google.maps.Marker({
        position: position,
        map: map,
        title: title,
        animation: google.maps.Animation.DROP,
        icon: icon
    });
    //infovenster inner html
    var infoContent = "<div class='infoWindow'><table><tr><td><h2>" + title + "</h2></td>" +
    "<td class='appreciation'>" +
    "<img src='img/icon/buttons/good.png' alt='V' style='float:left'>" +
    "<div class='appreciationValue'>" + appreciation + "</div>" +
    "<img src='img/icon/buttons/plus.png' alt='X' onclick='like()' style='float:right'>" +
    "</td></table><hr /><p>" + description +
    "</p>  + </div>";
    //console.log(infoContent);
    
    //infovenster
    var markerInfo = new google.maps.InfoWindow({
        content: infoContent + afbeelding,
        position: position
    });
   
    
    
    //openen onclick
    google.maps.event.addListener(marker, "click", function () {

        if (markerInfo.getMap() != null) {
            if (markerInfo) markerInfo.close();
        } else {
            //markerInfo.close();
    
            markerInfo.open(map, marker);
            lastmarker = marker;
            localStorage.setItem("notificatieid", notification.notificatieID);
            //marker.openInfoWindowHtml(markerInfo);
            //lastMarker = marker;
        }
    });

}

function like(){
    var gebruikernr = parseFloat(localStorage.getItem("gebruikernr"));
    var notificatieid = parseFloat(localStorage.getItem("notificatieid"));
    console.log("gebruikernr : " + gebruikernr + " notificatieid : " + notificatieid);
    if ($("toevoegenButton").is(":visible") == true)
    {
        console.log("ja we zijn er");
    }
    else 
    {
        console.log("waarom toch");
    }
    if (gebruikernr != 0) {
        var jsonFeedback = {
        userID: gebruikernr,
        notID: notificatieid,
        like: true}
        console.info("Sending JSON object to backend: \n" + JSON.stringify(jsonFeedback));
        var xhrfeedback = new XMLHttpRequest();
        xhrfeedback.open("POST", "http://localhost:8080/onzebuurt/resources/feedback");
        xhrfeedback.setRequestHeader('Content-Type', 'application/json');
        xhrfeedback.send(JSON.stringify(jsonFeedback));
        loadMarkers();
        lastmarker.close();
    }
    else {
        console.log("Geen gebruiker aangemeld dus niets wegschrijven.")
    }
    
    
    
    
    //replaceDiv('#map_canvas','#notificatieMenu');
}

function ophalenAantalFeedback(notification){
    var url = "http://localhost:8080/onzebuurt/resources/feedback/" + notification.notificatieID;
    console.log(url);
    var requestFeedback = new XMLHttpRequest();
    var count = 0;
    requestFeedback.open("GET", url, false);
    requestFeedback.send(null);
    var feedback = JSON.parse(requestFeedback.responseText);
    count = feedback.length;
    //console.log(count);
    
    return count;
}

function loadScript() {
    
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "http://maps.googleapis.com/maps/api/js?key=AIzaSyCjEo17JpqymdjvqU2e11hQm2BGkI1I6eg&sensor=true&callback=initialize";
    document.body.appendChild(script);
}

function postNotification(){
    var jsonMapsPos = {
        x: clickedLatLng.lat(),
        y: clickedLatLng.lng()
    }
    var jsonAdres = {
        straat: "notYetImplemented",
        stad: "notYetImplemented", 
        huisnummer: 0,
        mapsPos: jsonMapsPos        
    }
    var jsonNotificatie = {
        eventNr:0,
        eventNaam:$("#notificationType").val(),
        adres: jsonAdres,
        opmerking: $("#notificationInfo").val(),
        radius: 1,
        afbeelding: fileName
    }
    
    console.info("Sending JSON object to backend: \n" + JSON.stringify(jsonNotificatie));
    
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/onzebuurt/resources/notifications");
    xhr.setRequestHeader('Content-Type', 'application/json');
//    xhr.onreadystatechange = function () {
//        if (xhr.readyState == 4 && xhr.status == 200) {
//            alert(xhr.responseText);
//        }
//    }
    xhr.send(JSON.stringify(jsonNotificatie));
    replaceDiv('#map_canvas','#notificatieMenu');
    

}

function returnToCenter() {
    map.panTo(homeLocation);
}

function getCurrentLatLng() {
    return map.getCenter();
}

function reverseGeocode(latlng) {
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({
        'latLng': latlng
    }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                console.info("Succesfully reverse geocoded into:\n" + results[1].formatted_address);
                return results[1].formatted_address;
            } else {
                console.error("Geocoder: No results found");
                return null;
            }
        } else {
            console.error("Geocoder failed due to: " + status);
            return null;
        }
    });
}
function rawReverseGeocode(latlng) {
    var geocoder = new google.maps.Geocoder();
    geocoder.geocode({
        'latLng': latlng
    }, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            if (results[1]) {
                return results;
            } else {
                console.error("Geocoder: No results found");
                return null;
            }
        } else {
            console.error("Geocoder failed due to: " + status);
            return null;
        }
    });
}

function sendFile() {
    document.getElementById("status").innerHTML = "";
    
    var file = document.getElementById("filechooser").files[0];
    var extension = file.name.split(".").pop();
    
    var type;
    if (extension === "jpg" || extension === "jpeg" ||
        extension === "JPG" || extension === "JPEG") {
        type = "image/jpeg";
    } else if (extension === "png" || extension === "PNG") {
        type = "image/png";
    } else {
        document.getElementById("status").innerHTML = "Invalid file type";
        return;
    }
    
    var request = new XMLHttpRequest();
    request.open("POST", "http://localhost:8080/onzebuurt/resources/images");
    request.onload = function() {
        if (request.status === 201) {
            fileName = request.getResponseHeader("Location").split("/").pop();
            
            console.info("File created with name " + fileName);
        } else {
            console.info("Error creating file: (" + request.status + ") " + request.responseText);
        }
    };
    request.setRequestHeader("Content-Type", type);
    request.send(file);
}


  
 

window.onload = loadScript();

