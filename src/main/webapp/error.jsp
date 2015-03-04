<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="style/index.css">
    <link rel="stylesheet" type="text/css" href="style/error.css">
    <meta charset="utf-8">
    <title>Error</title>
</head>
<body>
<div class="container">
    <h1>Railway Company</h1>
    <jsp:include page="/navbar.jsp" flush="true"/>
    <div class="row">
        <div class="span12 img-rounded" id="jumbotron"></div>
    </div>
    <div class="well">
        <h2>Error</h2>

        <p>You have stopped the train on the railway dead end. Please, try again from main page and turn the
            switch to another position.</p>
        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Try again</a>
        <a href="mailto:grisha.lyullin@gmail.com" class="btn">Contact us</a>
    </div>
    <jsp:include page="/footer.jsp"/>
</div>
</body>
</html>