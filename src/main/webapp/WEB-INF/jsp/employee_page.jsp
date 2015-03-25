<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/employee_page.css">
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
    <jsp:include page="/WEB-INF/jsp/add_station.jsp"/>
    <jsp:include page="/WEB-INF/jsp/add_train.jsp"/>
    <jsp:include page="/WEB-INF/jsp/add_schedule.jsp"/>
    <jsp:include page="/WEB-INF/jsp/view_trains.jsp"/>
    <jsp:include page="/WEB-INF/jsp/view_passengers.jsp"/>
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</div>
</body>
</html>
