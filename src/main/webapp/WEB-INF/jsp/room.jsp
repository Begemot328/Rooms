<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <script src="../../room.js"></script>
</head>
<body>
<h2><c:out value="${requestScope.countryName}"/> room</h2>

<h4 id="status">Bulb status</h4>
<var hidden id="var1">1</var>
<var hidden id="var2">2</var>
<var hidden id="initial_status"><c:out value="${requestScope.countryCode}"/></var>
<var hidden id="code"><c:out value="${requestScope.countryCode}"/></var>
<button onclick="turnOn()" id="on">
    TURN ON
</button>
<button onclick="turnOff()" id="off">
    TURN OFF
</button>
</body>
</html>
