<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="row">
    <div class="span12">
        <div class="navbar navbar-inverse">
            <nav class="navbar-inner">
                <ul class="nav pull-left">
                    <li><a href="${pageContext.request.contextPath}/index#search_train">Search train</a></li>
                    <li><a href="${pageContext.request.contextPath}/index#schedule_by_station">Schedule by station</a>
                    </li>
                    <sec:authorize access="isAnonymous()">
                        <li><a href="${pageContext.request.contextPath}/login?msg=true">Buy ticket</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li><a href="${pageContext.request.contextPath}/buy_ticket">Buy ticket</a></li>
                    </sec:authorize>
                    <li><a href="${pageContext.request.contextPath}/index#about">About us</a></li>
                    <li class="divider-vertical"></li>
                </ul>
                <ul class="nav pull-right">
                    <li class="divider-vertical"></li>
                    <sec:authorize access="isAnonymous()">
                        <li><a href="${pageContext.request.contextPath}/login">Sign in</a></li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <li class="pull-right">
                                <a href="${pageContext.request.contextPath}/employee_page">Control cab</a>
                            </li>
                        </sec:authorize>
                        <li class="pull-right">
                            <a href=""><sec:authentication property="principal.username"/></a>
                        </li>
                        <li class="pull-right">
                            <a href="${pageContext.request.contextPath}/j_spring_security_logout">Sign out</a>
                        </li>
                    </sec:authorize>
                </ul>
            </nav>
        </div>
    </div>
</div>