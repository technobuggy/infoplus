//attributes
var ROADBLOCKIMG = "img/icon/marker/roadblock.png";
var homeLocation = new google.maps.LatLng(50.937595, 4.033542);

//functions
resizeMaps();
var mapOptions = {
    center: new google.maps.LatLng(50.937595, 4.033542),
    zoom: 18,
    mapTypeId: google.maps.MapTypeId.ROADMAP
};

var map = new google.maps.Map(document.getElementById("map_canvas"),
    mapOptions);

//testmarker
var marker = new google.maps.Marker({
    position: new google.maps.LatLng(50.937595, 4.033542),
    map: map,
    title: "Hindernis op de baan",
    icon: ROADBLOCKIMG
});
function resizeMaps() {
    $("#map_canvas").height(
        $(window).height() - 150
    );
}
function returnToCenter() {
    map.panTo(new google.maps.LatLng(50.937595, 4.033542));
}

$(window).resize(function () {
    resizeMaps();
});