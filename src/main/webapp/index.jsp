<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="style/index.css">
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/index.js"></script>
    <meta charset="utf-8">
    <title>Railway Company</title>
</head>
<body>

<div class="container">

    <div class="main-header">
        <h2>Railway Company</h2>
    </div>

    <jsp:include page="/navbar.jsp" flush="true"/>

    <div class="row">
        <div class="span12 img-rounded" id="jumbotron">
            <c:if test="${empty sessionScope.authorizationId}">
                <c:choose>
                    <c:when test="${sessionScope.signUpError}">
                        <jsp:include page="/login_error.jsp" flush="true"/>
                        <c:set var="signUpError" scope="session" value="false"/>
                    </c:when>
                    <c:when test="${sessionScope.signUp}">
                        <jsp:include page="/login.jsp" flush="true"/>
                    </c:when>
                </c:choose>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>