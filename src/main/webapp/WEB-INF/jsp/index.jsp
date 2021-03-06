<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/datepicker.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-typeahead.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-dateFormat.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.session.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
    <meta charset="utf-8">
    <title>Railway Company</title>
</head>
<body onload="init_index_page();">
<div class="container">

    <sec:authorize access="isAnonymous()">
        <c:set value="anonymous" var="authorizeAccess"/>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <c:set value="authenticated" var="authorizeAccess"/>
    </sec:authorize>

    <input type="hidden" name="authorize-access" value="${authorizeAccess}">
    <input type="hidden" name="context-path" value="${pageContext.request.contextPath}">

    <h1>Railway Company</h1>
    <jsp:include page="/WEB-INF/jsp/navbar.jsp" flush="true"/>
    <div class="row">
        <div class="span12 img-rounded" id="jumbotron">
            <sec:authorize access="isAnonymous()">
                <div class="hero-unit" id="form-signin">
                    <jsp:include page="login_form.jsp" flush="true"/>
                </div>
            </sec:authorize>
        </div>
    </div>
    <a name="search_train"></a>
    <jsp:include page="/WEB-INF/jsp/search_train.jsp" flush="true"/>
    <a name="schedule_by_station"></a>
    <jsp:include page="/WEB-INF/jsp/schedule_by_station.jsp" flush="true"/>
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
    <jsp:include page="/WEB-INF/jsp/footer.jsp" flush="true"/>
</div>
</body>
</html>