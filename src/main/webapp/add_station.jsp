<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="style/index.css">
    <link rel="stylesheet" type="text/css" href="style/buy_ticket.css">
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/index.js"></script>
    <meta charset="utf-8">
    <title>Railway Company</title>
</head>
<body>
<div class="container">
    <h1>Railway Company</h1>
    <jsp:include page="/navbar.jsp"/>
    <div class="row">
        <div class="span12 img-rounded" id="jumbotron"></div>
    </div>
    <div class="well">
        <h2>Add station</h2>

        <p>Add station page. Should be available only for employee</p>

        <div id="inputFromAddStation">
            <form class="form-inline" action="${pageContext.request.contextPath}add_station" method="post">
                <div class="input-prepend">
                    <span class="add-on">New station</span>
                    <input name="stationName" type="text" class="input-medium" placeholder="Station name">
                </div>
                <button type="submit" class="btn btn-success">
                    <i class="icon-white icon-plus"></i>
                </button>
                <c:choose>
                    <c:when test="${ empty sessionScope.allStationList}">
                        <a href="${pageContext.request.contextPath}add_station?getAll=true" class="btn btn-primary">
                            <i class="icon-white icon-list"></i>
                        </a>
                    </c:when>
                    <c:when test="${not empty sessionScope.allStationList}">
                        <a href="${pageContext.request.contextPath}add_station?getAll=false"
                           class="btn btn-primary active">
                            <i class="icon-white icon-list"></i>
                        </a>
                    </c:when>
                </c:choose>
            </form>
            <c:choose>
                <c:when test="${sessionScope.invalidStationNameError}">
                    <div class="alert alert-error">
                        <p>Incorrect station name.</p>
                    </div>
                    <c:set scope="session" var="invalidStationNameError" value="false"/>
                </c:when>
                <c:when test="${sessionScope.existStationError}">
                    <div class="alert alert-error">
                        <p>Station with name "${sessionScope.existStationName}" already exist in the database.</p>
                    </div>
                    <c:set scope="session" var="existStationError" value="false"/>
                    <c:set scope="session" var="existStationName" value="false"/>
                </c:when>
            </c:choose>
        </div>

        <c:if test="${not empty sessionScope.allStationList}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Station id</th>
                    <th>Station name</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.allStationList}" var="station">
                    <tr>
                        <td>${station.id}</td>
                        <td>${station.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

    </div>
    <jsp:include page="/footer.jsp"/>
</div>
</body>
</html>

