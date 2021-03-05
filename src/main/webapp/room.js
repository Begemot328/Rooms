var socket = new WebSocket("ws://localhost:8081/Rooms_war_exploded/room");
socket.onmessage = onMessage;

function onMessage(event) {
    var node = document.getElementById("status")
    node.innerText = event.data;
}

var isOn = true;

function turnOn() {
    isOn = true;
    socket.send("true")
}

function turnOff() {
    isOn = false;
    socket.send("false")
}