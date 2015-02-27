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
            <h2>Schedule by station</h2>
        </div>

        <p>Search schedule by station</p>

        <div>
            <form class="form-inline" onsubmit="return checkScheduleByStationForm(this)" method="post"
                  action="schedule_by_station">
                <div class="input-prepend" id="schedule_by_station">
                    <span class="add-on">Station</span>
                    <input id="Station-Name" name="Station-Name" type="text" class="input-large"
                           placeholder="Station name" value="<c:out value="${sessionScope.stationName}"/>">
                    <%--<span class="add-on">When</span>--%>
                    <%--<input id = "date" name="date" type="date" class="input-medium" title="Date">--%>
                    <%--<input id = "time" name="time" type="time" class="input-small" title="Time">--%>
                </div>
                <button type="submit" class="btn btn-success">Search</button>
            </form>
        </div>
        <div class="alert alert-error" id="scheduleAlert">
            <p>Incorrect input data. Try again.</p>
        </div>
        <c:choose>
            <c:when test="${not empty sessionScope.scheduleList}">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Train number</th>
                        <th>Arrival time</th>
                        <th>Departure time</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.scheduleList}" var="schedule">
                        <tr>
                            <td>${schedule.trainNumber}</td>
                            <td>${schedule.timeArrival}</td>
                            <td>${schedule.timeDeparture}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:set value="empty" scope="session" var="scheduleList"/>
            </c:when>
            <c:when test="${empty sessionScope.scheduleList}">
                <c:if test="${sessionScope.stationNotFound}">
                    <div class="alert alert-error" id="stationNotFoundAlert">
                        <p>Station "${sessionScope.stationName}" was not found.</p>
                    </div>
                    <c:set value="false" scope="session" var="stationNotFound"/>
                </c:if>
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>