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

    <div class="main-header">
        <h2>Railway Company</h2>
    </div>

    <jsp:include page="/navbar.jsp" flush="true"/>

    <div class="row">
        <div class="span12 img-rounded" id="jumbotron">
            <div class="hero-unit" id="error">
                <h2>Error</h2>

                <p>You have stopped the train on the railway dead end. Please, try again from main page and turn the
                    switch to another position.</p>
                <a href="/">
                    <button class="btn">Main page</button>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>