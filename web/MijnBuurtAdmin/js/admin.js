//at pageload
var adminGemeente;
var adminMail;
var type;
var eventsTemplate;
var statsTemplate;
var dbToolsTemplate;
var finalDatesArray;
if ($.cookie("gemeente") === null || $.cookie("gebruiker") === null) {
    window.location = "index.html";
} else {
    adminGemeente = $.cookie("gemeente");
    adminMail = $.cookie("gebruiker");
}

function initVars()
{
    eventsTemplate =
    '<article id="!eventnr">' +
    '<h1>!eventnr !eventnaam</h1><br/>' +
    '<table>' +
    '<tr>' +
    '<td>Zichtbaar: !zichtbaar</td>' +
    '<td rowspan="3">' +
    '<textarea id="opmerking!eventnr">' +
    '!opmerking' +
    '</textarea>' +
    '</td>' +
    '</tr>' +
    '<tr>' +
    '<td>!straat !nr</td>' +
    '</tr>' +
    '<tr>' +
    '<td>lat: !lat <br />' +
    'lng: !lng' +
    '</td>' +
    '</tr>' +
    '</table>' +            
    '<input type="button" class="accept" onclick="acceptN(!eventnr);" value="Aanvaarden"/>' +
    '<input type="button" class="accept" onclick="adjustN(!eventnr);" value="Aanpassen"/>' +
    '<input type="button" class="reject" onclick="rejectN(!eventnr);" value="Verwerpen"/>' +
    '<input type="button" class="remove" onclick="removeN(!eventnr);" value="Verwijderen"/>' +
    '</article>';
   
    statsTemplate = $("#overview").html();
    dbToolsTemplate = $("#dbTools").html();
    type = "statistics";
}

function createNotification(n) {

    //eventsTemplate kopiëren
    var newNotification = eventsTemplate;
    //waarden vervangen
    var regex = new RegExp('!eventnr', 'g');
    newNotification = newNotification.replace(regex, n.notificatieID);
    newNotification = newNotification.replace('!eventnaam', n.type);
    newNotification = newNotification.replace('!zichtbaar', n.visible);
    newNotification = newNotification.replace('!opmerking', n.description);
    newNotification = newNotification.replace('!straat', n.adres.straat);
    newNotification = newNotification.replace('!nr', n.adres.huisnummer);
    newNotification = newNotification.replace('!lat', n.adres.mapsPos.x);
    newNotification = newNotification.replace('!lng', n.adres.mapsPos.y);
    
    var notificationDOMObject = $(newNotification);

    //Reeds geaccepteerde items groenkleuren en hun acceptknop verwijderen
    if (n.visible == true) {
        notificationDOMObject.addClass("visibleArticle");
    }
    $('#notifications').append(notificationDOMObject);
}

window.onload = function () {
    initVars();

    $("#userId").text(adminMail);
    $('#gemeente').text(adminGemeente);
    loadStats();
    loadNotifications();
    loaddbTools();
    

}
function loadNotifications() {
    if (type == "statistics") {
        return;
    }
    $('#notifications').empty();
    console.info("loading notifications");
    var url = "http://localhost:8080/onzebuurt/resources/admin/" + type + "/" + adminGemeente;
    console.info(url);
    var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status == 200) {
            var notifications = JSON.parse(request.responseText);
            
            for (var i = 0; i < notifications.length; i++) {
                createNotification(notifications[i]);
                console.info(notifications[i].toString());
            }
        } else {
            console.error("Notificaties gefaald: " + request.status);
        }
    };
    request.open("GET", url);
    request.send(null);
}
function loaddbTools(){
    console.info("loading dbTools info (dates)");
    var url = "http://localhost:8080/onzebuurt/resources/admin/dateRange/" + adminGemeente;
    console.info(url);
    var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status == 200) {
            var dates = JSON.parse(request.responseText);
            finalDatesArray = dates;
            console.log("Fetched dates array: " + dates);
            builddbTools(dates);   
        } else {
            console.error("Notificaties gefaald: " + request.status);
        }
    };
    request.open("GET", url);
    request.send(null);
}
function builddbTools(dateArray){
    $("#dbTools").empty();
    var newdbTools = dbToolsTemplate;
    var datesCount = dateArray.length;
    console.info("Datescount: " + datesCount);
    newdbTools = newdbTools.replace("!dateCount", datesCount - 1);  
    $("#dbTools").html(newdbTools);    
    
    //add slider functionality
    $("#dbToolsRangeSlider").change(function(){
       $("#dbToolsDate").text(finalDatesArray[$("#dbToolsRangeSlider").val()]); 
    });
}
function loadStats(){

    console.info("loading stats");
    var url = "http://localhost:8080/onzebuurt/resources/admin/stats/" + adminGemeente
    console.info(url);  
    var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status == 200) {
            var statistics = JSON.parse(request.responseText);
            //stats invoeren
            writeStats(statistics);
        } else {
            console.error("Notificaties gefaald: " + request.status);
        }
    };
    request.open("GET", url);
    request.send(null);
}
function writeStats(s){
    $('#overview').empty();
    
    var totalNotifications = s.unpublishedCount + s.publishedCount;
    console.info("Totaal notifications: " + totalNotifications);
    
    var newStats = statsTemplate;
    //datum
    newStats = newStats.replace('!lastNotification',s.lastNotification);
    //percentages (moeten eerst gebeuren)
    newStats = newStats.replace('!publishedPerc',(s.publishedCount/totalNotifications)*100);
    newStats = newStats.replace('!unpublishedPerc',(s.unpublishedCount/totalNotifications)*100);
    newStats = newStats.replace('!dangerousSituationPerc',(s.dangerousSituationCount/totalNotifications)*100);
    newStats = newStats.replace('!neighboorhoodActionPerc',(s.neighbourhoodActionCount/totalNotifications)*100);
    newStats = newStats.replace('!roadsurfacePerc',(s.roadsurface/totalNotifications)*100);
    newStats = newStats.replace('!litterPerc',(s.litterCount/totalNotifications)*100);
    newStats = newStats.replace('!neighboorhoudEventPerc',(s.neighbourhoodEventCount/totalNotifications)*100);
    
        
    //waarden
    newStats = newStats.replace('!published',s.publishedCount);
    newStats = newStats.replace('!unpublished',s.unpublishedCount);
    newStats = newStats.replace('!dangerousSituation',s.dangerousSituationCount);
        newStats = newStats.replace('!neighboorhoodAction',s.neighbourhoodActionCount);
    newStats = newStats.replace('!roadsurface',s.roadsurface);
    newStats = newStats.replace('!litter',s.litterCount);
    newStats = newStats.replace('!neighboorhoudEvent',s.neighbourhoodEventCount);
    
    
    $('#overview').html(newStats);
    
}

function acceptN(id) {
    console.log("Accepting notificition with id" + id);
    var url = "http://localhost:8080/onzebuurt/resources/admin/accept/" + id;
    console.log("Accept uri: " + url);
    var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status != 204) {
            console.error("Notificaties gefaald: " + request.status);
        }
    };
    request.open("PUT", url);
    request.send(null);
    $("#" + id).slideUp(600);
}

function rejectN(id) {
    console.log("Rejecting notificition with id" + id);
    var url = "http://localhost:8080/onzebuurt/resources/admin/reject/" + id;
    console.log("Reject uri: " + url);
    var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status != 204) {
            console.error("Notificaties gefaald: " + request.status);
        }
    };
    request.open("PUT", url);
    request.send(null);
    $("#" + id).slideUp(600);
}

function removeN(id) {
    console.log("Removing notificition with id" + id);
    var url = "http://localhost:8080/onzebuurt/resources/admin/remove/" + id;
    console.log("Remove uri: " + url);
    var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status != 204) {
            console.error("Notificaties gefaald: " + request.status);
        }
    };
    request.open("DELETE", url);
    request.send(null);
    $("#" + id).slideUp(600);
}
function adjustN(id) {
    var updateString = $("#opmerking" + id).val();

    console.log("Adjusting notificition with id" + id + "to:\n" + updateString);
    var url = "http://localhost:8080/onzebuurt/resources/admin/updateDescription/" + id;
    console.log("ajdust uri: " + url);
    var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status != 204) {
            console.error("Notificaties gefaald: " + request.status);
        }
    };
    request.open("PUT", url);
    request.send("test");
    acceptN(id);
}

function setView(viewID) {
    var slideTime = 600;
    if (viewID == 2) {
        type = "all";
        $("#notifications").slideDown(slideTime);
        $("#overview").slideUp(slideTime);
        $("#dbTools").slideUp(slideTime);
        $("#developer").slideUp(slideTime);
    } else if (viewID == 3) {
        type = "old";
        $("#notifications").slideDown(slideTime);
        $("#overview").slideUp(slideTime);
        $("#dbTools").slideUp(slideTime);
        $("#developer").slideUp(slideTime);
    } else if (viewID == 1) {
        type = "new"
        $("#notifications").slideDown(slideTime);
        $("#overview").slideUp(slideTime);
        $("#dbTools").slideUp(slideTime);
        $("#developer").slideUp(slideTime);
    } else {
        type = "statistics";
        loadStats();
        $("#notifications").slideUp(slideTime);
        $("#overview").slideDown(slideTime);
        $("#dbTools").slideDown(slideTime);
        $("#developer").slideDown(slideTime);
    }
    loadNotifications();
}

function logOff() {
    $.cookie("gemeente",null);
    $.cookie("gebruiker", null);
    window.location = "index.html";
}

function addDummies() {
    if ($("#DummyGemeente").val() == ""){
        $("#DummyGemeente").val(adminGemeente);
    }
    var url = "http://localhost:8080/onzebuurt/resources/admin/createDummy/"
    url += $("#DummyCount").val();
    url += "/"
    url += $("#DummyGemeente").val();
    console.info("Dummy url: " + url);
    var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status == 200 || request.status == 204) {
            location.reload(true);
        } else {
            console.error("Dummies aanmaken gefaald: " + request.status);
        }
    };
    request.open("GET", url);
    request.send(null);
    $("#dummyStatus").text("Dummies worden aangemaakt, even geduld");    
}

function removeDummies(){
    var url = "http://localhost:8080/onzebuurt/resources/admin/removeDummies"
    var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status == 200 || request.status == 204) {
            location.reload(true);
        } else {
            console.error("Dummies verwijderen gefaald: " + request.status);
        }
    };
    request.open("DELETE", url);
    request.send(null);
    $("#dummyStatus").text("Dummies worden verwijderd, even geduld");    
}

function removeBefore(){
    var dateBefore = finalDatesArray[$("#dbToolsRangeSlider").val()];
    var url= "http://localhost:8080/onzebuurt/resources/admin/removeBefore/"+dateBefore;
       var request = new XMLHttpRequest();
    request.onload = function () {
        if (request.status == 200 || request.status == 204) {
            location.reload(true);
        } else {
            console.error("Dummies verwijderen gefaald: " + request.status);
        }
    };
    request.open("DELETE", url);
    request.send(null);
    $("#beforeStatus").text("De opdracht wordt uitgevoerd, even geduld");    
    
}