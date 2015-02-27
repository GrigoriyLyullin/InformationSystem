<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="style/style.css">
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

    <div class="row">
        <div class="span12">
            <div class="navbar navbar-inverse">
                <nav class="navbar-inner">
                    <ul class="nav">
                        <li><a href="#">Search train</a></li>
                        <li><a href="/station_schedule">Train schedule</a></li>
                        <li><a href="#">Buy Tickets</a></li>
                        <li class="divider-vertical"></li>
                        <c:if test="${empty sessionScope.authorizationId}">
                            <li><a href="/login">Sign in</a></li>
                        </c:if>
                        <c:if test="${not empty sessionScope.authorizationId}">
                            <li><a href="#">${sessionScope.userName} ${sessionScope.userSurname}</a></li>
                            <li><a href="/logout">Sign out</a></li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="span12 img-rounded" id="jumbotron">
            <c:if test="${empty sessionScope.authorizationId}">
                <div class="hero-unit" id="form-signin">
                    <form class="form-signin" onsubmit="return checkForm(this)" action="login" method="post">
                        <div id="form-signin-body">
                            <h2 class="form-signin-heading">Sign in</h2>
                            <input name="login" id="login" type="text" class="input-block-level"
                                   placeholder="Username or Email address">
                            <input name="password" id="password" type="password" class="input-block-level"
                                   placeholder="Password">

                            <div class="alert alert-error" id="loginAlert">
                                <p>Incorrect username or password.</p>
                            </div>
                            <button class="btn btn-medium btn-primary" type="submit">Sign in</button>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>