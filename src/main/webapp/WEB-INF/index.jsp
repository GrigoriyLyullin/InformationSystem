<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../style/index.css">
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/jquery-1.11.2.min.js"></script>
    <script src="../js/index.js"></script>
    <meta charset="utf-8">
    <title>Railway Company</title>
</head>
<body>
<div class="container">
    <h1>Railway Company</h1>
    <jsp:include page="/WEB-INF/navbar.jsp" flush="true"/>
    <div class="row">
        <div class="span12 img-rounded" id="jumbotron">
            <c:if test="${empty sessionScope.authenticationId}">
                <jsp:include page="/WEB-INF/login.jsp" flush="true"/>
            </c:if>
        </div>
    </div>
    <a name="search_train"></a>
    <jsp:include page="/WEB-INF/search_train.jsp" flush="true"/>
    <a name="schedule_by_station"></a>
    <jsp:include page="/WEB-INF/schedule_by_station.jsp" flush="true"/>
    <a name="about"></a>
    <div class="well">
        <h2>About us</h2>
        <div class="row">
            <div class="span4">
                <h3>Our customers</h3>
                <p>Railway company is proud of the partnership with the Hogwarts School of Witchcraft and Wizardry.
                    It's a tradition that began about thousand years ago, and that will continue tomorrow and
                    beyond. We are also glad to serve the "Polar Express" train to the North Pole.</p>
            </div>
            <div class="span4">
                <h3>Our trains</h3>
                <p>Our trains have a long history so... the fact that our trains is still go this is the real magic. But
                    does not worry, we almost always deliver our passengers to their
                    destination safe and sound.</p>
            </div>
            <div class="span4">
                <h3>Your comfort</h3>
                <p>We are pleased to offer you the best service. On our trains you can buy sweets and drink hot
                    chocolate all the way. We hope that a little trouble on the way (dementors, a little shaking, and so
                    on) do not darken your impressions of the trip.</p>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/footer.jsp" flush="true"/>
</div>
</body>
</html>