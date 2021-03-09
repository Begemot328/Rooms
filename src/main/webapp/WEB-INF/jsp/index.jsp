<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Choose a room</title>
</head>
<body>
<h2>Choose a room</h2>

<label for="countrySelect">Countries</label>
<select id="countrySelect" name="countryCode" form="country">
    <c:forEach var="locale" items="${applicationScope.locales}">
        <option id="${locale.country}" value="${locale.country}"><c:out value="${locale.displayCountry}"/></option>
    </c:forEach>
</select>
<form id="country" method="GET" action="${pageContext.request.contextPath}/room">
    <label>
        <input type="checkbox" name="auto" value="auto"/>
    </label>
    <button type="submit">To room</button>
</form>
</body>
</html>
