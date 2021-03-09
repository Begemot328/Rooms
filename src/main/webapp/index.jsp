<%@ page session="true" %>
<html>
<head>
    <script src="room.js"></script>
</head>
<body>
<h2>Hello World!</h2>

<h4 id="status"></h4>
<var hidden id="var1">1</var>
<var hidden id="var2">2</var>
<var hidden id="initial_status">true</var>
<button onclick="turnOn()" id="on">
    ON
</button>
<button onclick="turnOff()" id="off">
    OFF
</button>
</body>
</html>
