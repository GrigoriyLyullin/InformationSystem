<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
    <div class="span12">
        <div class="navbar navbar-inverse">
            <nav class="navbar-inner">
                <ul class="nav pull-left">
                    <li><a href="${pageContext.request.contextPath}/index#search_train">Search train</a></li>
                    <li><a href="${pageContext.request.contextPath}/index#schedule_by_station">Schedule by station</a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/buy_ticket">Buy ticket</a></li>
                    <li><a href="${pageContext.request.contextPath}/index#about">About us</a></li>
                    <li class="divider-vertical"></li>
                </ul>
                <ul class="nav pull-right">
                    <li class="divider-vertical"></li>
                    <c:if test="${empty sessionScope.userData}">
                        <li><a href="${pageContext.request.contextPath}/login">Sign in</a></li>
                    </c:if>
                    <c:if test="${not empty sessionScope.userData}">
                        <c:if test="${sessionScope.userData.role == 'ROLE_ADMIN'}">
                            <li class="pull-right">
                                <a href="${pageContext.request.contextPath}/employee_page">Control cab</a>
                            </li>
                        </c:if>
                        <li class="pull-right">
                            <a href="">${sessionScope.userData.username}</a>
                        </li>
                        <li class="pull-right"><a href="${pageContext.request.contextPath}/logout">Sign out</a></li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>