var code;
var urlWs;
var socket;
var isOn;
var node;
var var1;
var var2;
var bulb;
var link1;
var link2;
var url;
var context;
var uri;
window.onload = onLoad;

function onLoad() {
    node = document.getElementById("status");
    bulb = document.getElementById("bulb");
    isOn = document.getElementById("initial_status").innerText === "true";
    var1 = document.getElementById("var1").innerText;
    var2 = document.getElementById("var2").innerText;
    link1 = document.getElementById("link1").innerText;
    link2 = document.getElementById("link2").innerText;
    url = document.getElementById("url").innerText;
    uri = document.getElementById("uri").innerText;
    context = document.getElementById("context").innerText;
    bulb.src = selectVar(isOn, link1, link2);

    node.innerText = selectVar(isOn, var1, var2);
    code = document.getElementById("code").innerText;
    urlWs = "ws://" + document.location.host + document.location.pathname + '/' + code;
    socket = new WebSocket(urlWs);
    socket.onmessage = onMessage;
}

function onMessage(event) {
    isOn = event.data === "true";
    node.innerText = selectVar(isOn, var1, var2);
    bulb.src = selectVar(isOn, link1, link2);
}

function turnOn() {
    isOn = true;
    node.innerText = selectVar(isOn, var1, var2);
    bulb.src = selectVar(isOn, link1, link2);
    socket.send("true")
}

function turnOff() {
    isOn = false;
    node.innerText = selectVar(isOn, var1, var2);
    bulb.src = selectVar(isOn, link1, link2);
    socket.send("false")
}

function selectVar(isOn, var1, var2) {
    if (Boolean(isOn)) {
        return var1;
    } else {
        return var2;
    }
}