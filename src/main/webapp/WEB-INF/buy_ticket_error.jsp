<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <jsp:include page="/WEB-INF/navbar.jsp"/>
    <div class="row">
        <div class="span12 img-rounded" id="jumbotron"></div>
    </div>
    <div class="well">
        <h2>Buy ticket</h2>

        <p>
            <c:choose>
                <c:when test="${sessionScope.buyTicketAlreadyRegistered}">
                    You already registered on this train.
                    <c:set scope="session" var="buyTicketAlreadyRegistered" value="${false}"/>
                </c:when>
                <c:when test="${sessionScope.buyTicketHasNoEmptySeats}">
                    All tickets for this train has been sold.
                    <c:set scope="session" var="buyTicketHasNoEmptySeats" value="${false}"/>
                </c:when>
                <c:when test="${sessionScope.buyTicketSalesStop}">
                    Ticket sales for this train has been stopped (Less than 10 minute to train departure).
                    <c:set scope="session" var="buyTicketSalesStop" value="${false}"/>
                </c:when>
                <c:when test="${sessionScope.buyTicketInvalidData}">
                    You entered incorrect input data. Try again and be careful.
                    <c:set scope="session" var="buyTicketInvalidData" value="${false}"/>
                </c:when>
            </c:choose>
            <br>
            <br>
            <a href="${pageContext.request.contextPath}/buy_ticket" class="btn btn-primary">Back</a>
        </p>
    </div>
    <jsp:include page="/WEB-INF/footer.jsp"/>
</div>
</body>
</html>