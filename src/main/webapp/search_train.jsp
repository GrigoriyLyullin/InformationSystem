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

    <div class="well">

        <div class="header">
            <h2>Search train</h2>
        </div>

        <p>Enter the route and the date of the travel to choose the train: </p>

        <c:choose>
            <c:when test="${empty sessionScope.extendedForm || sessionScope.extendedForm == false}">
                <jsp:include page="/search_train_simple_form.jsp" flush="true"/>
            </c:when>
            <c:when test="${sessionScope.extendedForm == true}">
                <jsp:include page="/search_train_extended_form.jsp" flush="true"/>
            </c:when>
        </c:choose>

        <div class="alert alert-error" id="searchTrainAlert">
            <p>Incorrect input data. Try again.</p>
        </div>
        <c:choose>
            <c:when test="${not empty sessionScope.trainList}">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Train number</th>
                        <th>${sessionScope.stationFromName} departure time</th>
                        <th>${sessionScope.stationToName} arrival time</th>
                        <th>Buy ticket</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.trainList}" var="train">
                        <tr>
                            <td>${train.trainNumber}</td>
                            <td>${train.timeArrival}</td>
                            <td>${train.timeDeparture}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty sessionScope.userId}">
                                        <form action="/login" method="get">
                                            <button class="btn disabled btn-block">Buy</button>
                                        </form>
                                    </c:when>
                                    <c:when test="${not empty sessionScope.userId}">
                                        <form>
                                            <input type="hidden" name="trainNumber" value="${train.trainNumber}">
                                            <input type="hidden" name="timeDeparture" value="${train.timeDeparture}">
                                            <button type="submit" class="btn btn-success btn-block">Buy</button>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:set value="empty" scope="session" var="scheduleList"/>
            </c:when>
            <c:when test="${empty sessionScope.trainList}">
                <c:choose>

                    <c:when test="${sessionScope.trainSearchingError}">
                        <div class="alert alert-error" id="trainSearchingErrorAlert">
                            <p>Error! Invalid input data.</p>
                        </div>
                        <c:set value="false" scope="session" var="trainSearchingError"/>
                    </c:when>

                    <c:when test="${sessionScope.trainNotFound}">
                        <div class="alert alert-error" id="trainNotFoundAlert">
                            <p>Train from station "${sessionScope.stationFromName}" to "${sessionScope.stationToName}"
                                was not found.</p>
                        </div>
                        <c:set value="false" scope="session" var="trainNotFound"/>
                    </c:when>

                </c:choose>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>