<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Choose a room</title>
    <script src="${pageContext.request.contextPath}/enabling.js"></script>
</head>
<body>
<h2>Choose a room</h2>
<h3><c:out value="${requestScope.errorMessage}"/></h3>
<label for="countrySelect">Countries:</label>
<form id="country" method="GET" action="${pageContext.servletContext.contextPath}/rooms">
    <div>
        <select id="countrySelect" name="countryCode" form="country">
            <c:forEach var="locale" items="${applicationScope.locales}">
                <option id="${locale.country}" value="${locale.country}"><c:out value="${locale.displayCountry}"/></option>
            </c:forEach>
        </select>

        <input id="auto" type="checkbox" name="auto" value="true" onchange="EnableDisable()"/>
        <label for="auto">auto</label>
        <input id="local" type="checkbox" name="local" value="true" onchange="EnableDisable()"/>
        <label for="local">
            localhost
        </label>
    </div>
    <br/>
    <button type="submit">To room</button>
</form>
</body>
</html>
