var timerPopup;
var currentObject;

function hidePopup() {
    clearTimeout(timerPopup);
    currentObject.hide();
}

function hidePopupTimer(object) {
    currentObject = object;
    timerPopup = setTimeout('hidePopup()', 2000);
}