var socket = new WebSocket("ws://localhost:8081/Rooms_war_exploded/room/" + "us");
socket.onmessage = onMessage;
window.onload = onLoad;
var isOn;
var node;
var var1;
var var2;

function onLoad() {
    node = document.getElementById("status");
    isOn = document.getElementById("initial_status").innerText === "true";
    var1 = document.getElementById("var1").innerText;
    var2 = document.getElementById("var2").innerText;
    node.innerText = selectVar(isOn, var1, var2);
}

function onMessage(event) {
    isOn = event.data === "true";
    node.innerText = selectVar(isOn, var1, var2);
}

function turnOn() {
    isOn = true;
    node.innerText = selectVar(isOn, var1, var2);
    socket.send("true")
}

function turnOff() {
    isOn = false;
    node.innerText = selectVar(isOn, var1, var2);
    socket.send("false")
}

function selectVar(isOn, var1, var2) {
    if (Boolean(isOn)) {
        return var1;
    } else {
        return var2;
    }
}