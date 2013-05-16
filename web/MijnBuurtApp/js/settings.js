//attributes
//instelling namen
console.info("Settings.js 1.031")
var ADRESTYPE = "adrestype" //De manier van het invoeren van het adres


function initialize() {
    initializeSearchBar();
    
        
    //mapsview instelling
    if (loadItem("mapsView") != null) {
        $("#mapsView").val(loadItem("mapsView"));
        //Radius instelling
        if (loadItem("notificationRange") != null) {
            var range = loadItem("notificationRange");
            $("#radiusSlider").val(range);
            slideRadius() //label updaten
        }
    else {
        var gebruikersnr = parseFloat(localStorage.getItem("gebruikersnr"));
        var url = "http://localhost:8080/onzebuurt/resources/gebruiker/" + gebruikersnr;
        var request = new XMLHttpRequest();
        request.onload = function() {
        if (request.status == 200) {
            
            var settings = JSON.parse(request.responseText);
            var mapsview = settings.mapsview;
            var radius = settings.radius;
            console.log(mapsview + " " + radius);
            $("#mapsView").val(mapsview);    
            $("#radiusSlider").val(radius); 
            
            
        } else {
            console.error("Settings gefaald: " + request.status);
        }
        };
        request.open("GET", url);
        request.send(null);
    }
    }

    
    
    
    //Thuisadres
    if (loadItem("homeAdressReadable") == null) {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                console.log("Geolocation enabled");
                var tempLatLng = new google.maps.LatLng(
                    position.coords.latitude,
                    position.coords.longtitude);
                console.log("geolocation results:\n\t" +
                    position.coords.latitude + "," +
                    position.coords.longtitude + "\n\t" +
                    position.adress);

                storeLatLng("homeAdress", position.coords);
                $("#homeAdress").text(position.adress);
            });
        } else {
            console.error("No geolocation!");
        }
    } else {
        $("#homeAdress").text(loadItem("homeAdressReadable"));
    }
    
}
function setPosition(position) {


}
function nativeLogin(){
    
}
function initializeSearchBar() {
    var input = $("#homeAdress");
    var autocomplete = new google.maps.places.Autocomplete(input.get(0));
    
}
function flush() {
    if (confirm('Deze actie maakt al uw opgeslagen instellingen ongedaan!')) {
        localStorage.clear();
    }
}
function slideRadius() {
    var tekst = "Notificaties tonen binnen de ";
    var val = $("#radiusSlider").val();
    tekst += val;
    tekst += " km.";
    $('#radiusLabel').text(tekst);
}
function storeSettings() {
    var gebruikersnr = parseFloat(localStorage.getItem("gebruikernr"));
    if(gebruikersnr == 0){
        console.log("U moet zich eerst aanmelden");
    }
    else {
        saveItem("mapsView", $("#mapsView").val());
        saveItem("notificationRange", $("#radiusSlider").val());

        //ingevoerd adres omzetten naar latlng en bewaren
        var geocoder = new google.maps.Geocoder();
        var ingevoerdAdres = $("#homeAdress").val();
        geocoder.geocode({ 'address': ingevoerdAdres }, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                storeLatLng("homeAdress", results[0].geometry.location);
                saveItem("homeAdressReadable", ingevoerdAdres);
                console.info("Thuisadres gewijzigd naar: " + results[0].geometry.location)
            } else {
                alert("Thuislocatie kon niet bewaard worden \n" + status);
            }
        });
        var view = ("mapsView", $("#mapsView").val());
        var radius = ("notificationRange", $("#radiusSlider").val());

        var jsonSetting = {
            mapsview: (view === 'true'),
            radius: parseInt(radius)}
            console.info("Sending JSON object to backend: \n" + JSON.stringify(jsonSetting));
            var xhrfeedback = new XMLHttpRequest();
            xhrfeedback.open("PUT", "http://localhost:8080/onzebuurt/resources/gebruiker/" + gebruikersnr);
            xhrfeedback.setRequestHeader('Content-Type', 'application/json');
            xhrfeedback.send(JSON.stringify(jsonSetting));
    }
        
}

window.onload = initialize;