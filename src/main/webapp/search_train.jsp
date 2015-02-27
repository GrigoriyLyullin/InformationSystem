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

        <p>Search train from point A to B</p>

        <div>
            <form class="form-horizontal" onsubmit="return checkSearchTrainForm(this)" method="post"
                  action="search_train">
                <div class="input-prepend" id="schedule_by_station">
                    <span class="add-on">From</span>
                    <input id="Station-From-Name" name="Station-From-Name" type="text" class="input-xlarge"
                           placeholder="Station name" value="<c:out value="${sessionScope.stationFromName}"/>">
                    <span class="add-on">To</span>
                    <input id="Station-To-Name" name="Station-To-Name" type="text" class="input-xlarge"
                           placeholder="Station name" value="<c:out value="${sessionScope.stationToName}"/>">
                    <br>
                    <br>
                    <span class="add-on">Date from</span>
                    <input id="dateFrom" name="dateFrom" type="date" class="input-medium" title="Date">
                    <input id="timeFrom" name="timeFrom" type="time" class="input-small" title="Time">
                    <span class="add-on">Date to</span>
                    <input id="dateTo" name="dateTo" type="date" class="input-medium" title="Date">
                    <input id="timeTo" name="timeTo" type="time" class="input-small" title="Time">
                </div>
                <br>
                <br>
                <button type="submit" class="btn btn-success">Search</button>
            </form>
        </div>
        <div class="alert alert-error" id="searchTrainAlert">
            <p>Incorrect input data. Try again.</p>
        </div>

    </div>
</div>
</body>
</html>