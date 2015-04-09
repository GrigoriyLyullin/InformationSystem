<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/buy_ticket.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/datepicker.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/style/bootstrap-timepicker.min.css">

    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-typeahead.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-timepicker.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
    <meta charset="utf-8">
    <title>Railway Company</title>
</head>
<body onload="init_buy_ticket_page();">
<div class="container">
    <h1>Railway Company</h1>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp"/>
    <div class="row">
        <div class="span12 img-rounded" id="jumbotron"></div>
    </div>

    <div class="well">
        <h2>Buy ticket</h2>

        <c:choose>
            <c:when test="${not requestScope.success}">
                <div class="row">
                    <c:choose>
                        <c:when test="${requestScope.step == 1}">
                            <jsp:include page="buy_ticket_step_1.jsp"/>
                            <jsp:include page="buy_ticket_step_2_disabled.jsp"/>
                            <jsp:include page="buy_ticket_step_3_disabled.jsp"/>
                        </c:when>
                        <c:when test="${requestScope.step == 2}">
                            <jsp:include page="buy_ticket_step_1_disabled.jsp"/>
                            <jsp:include page="buy_ticket_step_2.jsp"/>
                            <jsp:include page="buy_ticket_step_3_disabled.jsp"/>
                        </c:when>
                        <c:when test="${requestScope.step == 3}">
                            <jsp:include page="buy_ticket_step_1_disabled.jsp"/>
                            <jsp:include page="buy_ticket_step_2_disabled.jsp"/>
                            <jsp:include page="buy_ticket_step_3.jsp"/>
                        </c:when>
                    </c:choose>
                </div>
            </c:when>
            <c:when test="${requestScope.success}">
                <jsp:include page="buy_ticket_success.jsp"/>
            </c:when>
        </c:choose>
    </div>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
