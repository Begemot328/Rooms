var code;
var url;
var socket;
var isOn;
var node;
var var1;
var var2;
var bulb;
var link1;
var link2;
window.onload = onLoad;

function onLoad() {
    node = document.getElementById("status");
    bulb = document.getElementById("bulb");
    isOn = document.getElementById("initial_status").innerText === "true";
    var1 = document.getElementById("var1").innerText;
    var2 = document.getElementById("var2").innerText;
    link1 = document.getElementById("link1").innerText;
    link2 = document.getElementById("link2").innerText;
    bulb.src = selectVar(isOn, link1, link2);

    node.innerText = selectVar(isOn, var1, var2);
    code = document.getElementById("code").innerText;
    url = "ws://localhost:8081/Rooms_war_exploded/room/" + code;
    socket = new WebSocket(url);
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