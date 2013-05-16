<<<<<<< .mine
﻿var state = 1;
function go() {
    localStorage.setItem("firstRun", "false");
    location.href = 'main.html';
}
function prevWelcomeScreen() {
    if (state > 0) {
        state--;
        switchState();
    }
}
function nextWelcomeScreen() {
    if (state < 4) {
        state++;
        switchState();
    }
}
function switchState() {
    console.info("New welcome state: " + state);
    if (state == 1) {
        disableMenu();
        $(".arrowLeft").addClass("disabled");

        $("#welcome1").slideDown(300);
        $("#welcome2").slideUp(300);
        $("#welcome3").slideUp(300);
        $("#welcome4").slideUp(300);
        
    } else if (state == 2) {
        enableMenu();
        $(".arrowLeft").removeClass("disabled");

        $("#welcome1").slideUp(300);
        $("#welcome2").slideDown(300);
        $("#welcome3").slideUp(300);
        $("#welcome4").slideUp(300);
    } else if (state == 3) {
        disableMenu();
        $(".arrowLeft").removeClass("disabled");

        $("#welcome1").slideUp(300);
        $("#welcome2").slideUp(300);
        $("#welcome3").slideDown(300);
        $("#welcome4").slideUp(300);
    } else if (state == 4) {
        disableMenu();
        $(".arrowLeft").removeClass("disabled");

        $("#welcome1").slideUp(300);
        $("#welcome2").slideUp(300);
        $("#welcome3").slideUp(300);
        $("#welcome4").slideDown(300);
    }
}
function disableMenu() {
    $("nav > div").addClass("disabled");
}
function enableMenu() {
    $("nav > div").removeClass("disabled");
}

=======
﻿var state = 1;
function go() {
    localStorage.setItem("firstRun", "false");
    location.href = 'main.html';
}
function prevWelcomeScreen() {
    if (state > 0) {
        state--;
        switchState();
    }
}
function nextWelcomeScreen() {
    if (state < 4) {
        state++;
        switchState();
    }
}
function switchState() {
    console.info("New welcome state: " + state);
    if (state == 1) {
        disableMenu();
        $(".arrowLeft").addClass("disabled");

        $("#welcome1").slideDown(300);
        $("#welcome2").slideUp(300);
        $("#welcome3").slideUp(300);
        $("#welcome4").slideUp(300);
        
    } else if (state == 2) {
        enableMenu();
        $(".arrowLeft").removeClass("disabled");

        $("#welcome1").slideUp(300);
        $("#welcome2").slideDown(300);
        $("#welcome3").slideUp(300);
        $("#welcome4").slideUp(300);
    } else if (state == 3) {
        disableMenu();
        $(".arrowLeft").removeClass("disabled");

        $("#welcome1").slideUp(300);
        $("#welcome2").slideUp(300);
        $("#welcome3").slideDown(300);
        $("#welcome4").slideUp(300);
    } else if (state == 4) {
        disableMenu();
        $(".arrowLeft").removeClass("disabled");

        $("#welcome1").slideUp(300);
        $("#welcome2").slideUp(300);
        $("#welcome3").slideUp(300);
        $("#welcome4").slideDown(300);
    }
}
function disableMenu() {
    $("nav > div").addClass("disabled");
}
function enableMenu() {
    $("nav > div").removeClass("disabled");
}>>>>>>> .r67
