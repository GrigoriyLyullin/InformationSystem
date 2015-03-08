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
    <h1>Railway Company</h1>
    <jsp:include page="/navbar.jsp"/>
    <jsp:include page="/add_station.jsp"/>
    <jsp:include page="/add_train.jsp"/>
    <a href="#view_trains"></a>
    <jsp:include page="/view_trains.jsp"/>
    <jsp:include page="/footer.jsp"/>
</div>
</body>
</html>