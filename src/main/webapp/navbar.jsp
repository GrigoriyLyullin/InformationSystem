<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
    <div class="span12">
        <div class="navbar navbar-inverse">
            <nav class="navbar-inner">
                <ul class="nav">
                    <li><a href="#">Search train</a></li>
                    <li><a href="/schedule_by_station">Schedule by station</a></li>
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