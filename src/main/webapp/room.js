var code;
var url;
var socket;
var isOn;
var node;
var var1;
var var2;

window.onload = onLoad;

function onLoad() {
    node = document.getElementById("status");
    isOn = document.getElementById("initial_status").innerText === "true";
    var1 = document.getElementById("var1").innerText;
    var2 = document.getElementById("var2").innerText;
    node.innerText = selectVar(isOn, var1, var2);

    code = document.getElementById("code").innerText;
    url = "ws://localhost:8081/Rooms_war_exploded/room/" + code;
    socket = new WebSocket(url);
    socket.onmessage = onMessage;
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