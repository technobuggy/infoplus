
function submitForm() {
    var ontvangenGemeente = "notSet";
    $("#response").text("Inloggen...");
    //fetching values
    var username = $("#userName").val();
    var pass = $("#password").val();
    //debug
    console.log('>'+username+'<');
    console.log('>'+pass+'<');
    //creating uri
    var uri = "http://localhost:8080/onzebuurt/resources/admin/login/{user}/{pass}";
    uri = uri.replace("{user}", username);
    uri = uri.replace("{pass}", pass);
    console.log("uri:\n>" + uri + "<");
    //requesting
    var request = new XMLHttpRequest();
    
    request.onload = function () {
        if (request.status == 200) {
            ontvangenGemeente = "succesfullYetNotSet";
            ontvangenGemeente = request.responseText + "";
            console.log("Request succesfull\n\tresponse: >" + request.responseText + "<");
            if (ontvangenGemeente == "notFound") {
                $("#response").text("De gebruiker bestaat niet of het wachtwoord is fout.");
            } else {
                $("#response").text("Inloggen met gemeente: " + ontvangenGemeente);
                $.cookie("gemeente", ontvangenGemeente);
                $.cookie("gebruiker", username);
                window.location = "admin.html";
            }
        } else {
            $("#response").text("Er kon geen verbinding gemaakt worden met de server.");
            console.log("Request unsuccesfull");
            return;
        }
    };
    request.open("GET", uri);
    request.send(null);
    return;
}