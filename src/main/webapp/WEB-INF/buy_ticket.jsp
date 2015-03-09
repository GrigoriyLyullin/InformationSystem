<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../style/index.css">
    <link rel="stylesheet" type="text/css" href="../style/buy_ticket.css">
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/jquery-1.11.2.min.js"></script>
    <script src="../js/index.js"></script>
    <meta charset="utf-8">
    <title>Railway Company</title>
</head>
<body>
<div class="container">
    <h1>Railway Company</h1>
    <jsp:include page="/WEB-INF/navbar.jsp"/>
    <div class="row">
        <div class="span12 img-rounded" id="jumbotron"></div>
    </div>
    <div class="well">
        <h2>Buy ticket</h2>

        <p> Enter passenger and train information below. If you do not know all the necessary information for the
            purchase of a ticket you can use
            <a href="${pageContext.request.contextPath}index#search_train">search</a> or
            <a href="${pageContext.request.contextPath}index#schedule_by_station">train schedule by station</a>.
        </p>

        <div id="inputFromBuyTicket">
            <form class="form-inline" method="post" action="${pageContext.request.contextPath}buy_ticket">
                <div class="input-prepend">
                    <span class="add-on">Train number</span>
                    <input id="Train-Number" name="trainNumber" type="text" class="input-medium"
                           placeholder="Train number" value="<c:out value="${sessionScope.trainNumber}"/>">
                    <span class="add-on">From</span>
                    <input id="Station-From-Name" name="stationName" type="text" class="input-medium"
                           placeholder="Station name" value="<c:out value="${sessionScope.stationFromName}"/>">
                </div>
                <div class="input-prepend">
                    <span class="add-on">Departure datetime</span>
                    <input name="departureDate" type="date" class="input-medium" placeholder="dd.mm.yyyy" title="Date">
                    <input name="departureTime" type="time" class="input-small" placeholder="hh:mm" title="Time">
                </div>
                <div class="input-prepend">
                    <span class="add-on">Passenger name</span>
                    <input name="passengerName" type="text" class="input-large" placeholder="Name">
                    <span class="add-on">Surname</span>
                    <input name="passengerSurname" type="text" class="input-large" placeholder="Surname">
                    <span class="add-on">Birthdate</span>
                    <input name="passengerBirthdate" type="date" class="input-medium" placeholder="dd.mm.yyyy">
                </div>
                <button type="submit" class="btn btn-success">Buy</button>
            </form>
        </div>
        <c:if test="${sessionScope.buyTicketIncorrectData}">
            <div class="alert alert-error">
                <p>Incorrect input data. Try again.</p>
            </div>
            <c:set scope="session" var="buyTicketIncorrectData" value="${false}"/>
        </c:if>
    </div>
    <jsp:include page="/WEB-INF/footer.jsp"/>
</div>
</body>
</html>
