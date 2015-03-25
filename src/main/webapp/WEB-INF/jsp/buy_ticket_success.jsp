<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/buy_ticket.css">
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
    <meta charset="utf-8">
    <title>Railway Company</title>
</head>
<body>
<div class="container">
    <h1>Railway Company</h1>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
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
            Departure date:
            <fmt:formatDate type="date" dateStyle="short" value="${sessionScope.ticketData.departureDate}"/>
            <br>
        </p>

        <p>
            <b>Passenger information</b><br>
            Name: ${sessionScope.ticketData.passengerData.name} Surname:
            ${sessionScope.ticketData.passengerData.surname}
            Birthdate:
            <fmt:formatDate type="date" dateStyle="short" value="${sessionScope.ticketData.passengerData.birthdate}"/>
            <br>
        </p>
        <a href="${pageContext.request.contextPath}/buy_ticket" class="btn btn-primary">Back</a>
        <a href="${pageContext.request.contextPath}/index" class="btn ">Main page</a>
    </div>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>