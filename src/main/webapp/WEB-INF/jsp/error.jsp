<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Error page</title>
</head>
<body>
<h2>Error</h2>
<h3><c:out value="${requestScope.errorMessage}"/></h3>
</body>
</html>
