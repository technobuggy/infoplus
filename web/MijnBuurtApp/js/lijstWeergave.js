var listViewer = $("#listView");
initializeSearchBar();

/*for (var x = 0; x < 20; x++) {
    addListItem("Dummy " + x, "TestItem", "roadblock");
}*/

function loadMarkers() {
    console.info("loading markers");
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
                addListItem(notifications[i]);
            }
        } else {
            console.error("Notificaties gefaald: " + request.status);
        }
    };
    request.open("GET", url);
    request.send(null);

}
function initializeSearchBar() {
    var input = $("#homeAdress");
    var autocomplete = new google.maps.places.Autocomplete(input.get(0));
    if (loadItem("homeAdressReadable") == null) {
        input.val(loadItem("homeAdressReadable"));
    }
}
function addListItem(notification) {
    //attributen
    var imageName = "roadblock";
    //var title =  getReadableType(notification.type);
    var title = notification.type;
    console.info("type event is : " + notification.type);
    var description = notification.description;
    //creatie    
    var node = $(
                '<div class="lijstItem" style="border-bottom: 1px solid black;"> '+
                    '<table>'+
                        '<tr>'+
                            '<td><img src="img/icon/marker/' + imageName + '.png" /></td>'+
                            '<td>'+
                                '<h1>' + title + '</h1>'+
                                '<p>' + description + '</p>'+
                            '</td>'+
                        '</tr>'+
                    '</table>'+
                '</div>'
    );
    listViewer.append(node);
}
window.onload = loadMarkers();