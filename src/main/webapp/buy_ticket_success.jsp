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
        <h2>Buy ticket</h2>

        <p>
            You successfully purchased a ticket.
        </p>

        <p>
            <b>Ticket information:</b><br>
            Train number: ${sessionScope.ticketData.trainNumber} From station: ${sessionScope.ticketData.stationFrom}
            Departure date: ${sessionScope.ticketData.departureDate}<br>
        </p>

        <p>
            <b>Passenger information</b><br>
            Name: ${sessionScope.ticketData.passengerData.name}" Surname:
            ${sessionScope.ticketData.passengerData.surname}
            Birthdate: ${sessionScope.ticketData.passengerData.birthdate}<br>
        </p>
        <a href="${pageContext.request.contextPath}" class="btn btn-primary">Main page</a>
    </div>
    <jsp:include page="/footer.jsp"/>
</div>
</body>
</html>