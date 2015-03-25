<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="well">

    <div class="header">
        <h2>Schedule by station</h2>
    </div>

    <jsp:include page="/WEB-INF/jsp/schedule_by_station_simple_form.jsp"/>

    <c:choose>
        <c:when test="${not empty sessionScope.scheduleList}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Train number</th>
                    <th>Arrival time</th>
                    <th>Departure time</th>
                        <%--<th>Buy ticket</th>--%>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.scheduleList}" var="train">
                    <tr>
                        <td>${train.trainNumber}</td>
                        <td>
                            <fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                            value="${train.timeArrival}"/>
                        </td>
                        <td>
                            <fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                            value="${train.timeDeparture}"/>
                        </td>
                            <%--<td>--%>
                            <%--<c:choose>--%>
                            <%--<c:when test="${empty sessionScope.authorizationId}">--%>
                            <%--<form action="${pageContext.request.contextPath}buy_ticket" method="get">--%>
                            <%--<button type="submit" class="btn disabled btn-block">Buy</button>--%>
                            <%--</form>--%>
                            <%--</c:when>--%>
                            <%--<c:when test="${not empty sessionScope.authorizationId}">--%>
                            <%--<form action="${pageContext.request.contextPath}buy_ticket" method="post">--%>
                            <%--<input type="hidden" name="trainNumber" value="${train.trainNumber}">--%>
                            <%--<input type="hidden" name="timeDeparture" value="${train.timeDeparture}">--%>
                            <%--<button type="submit" class="btn btn-block btn-success">Buy</button>--%>
                            <%--</form>--%>
                            <%--</c:when>--%>
                            <%--</c:choose>--%>
                            <%--</td>--%>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:when test="${empty sessionScope.scheduleList}">
            <c:if test="${sessionScope.stationNotFound}">
                <div class="alert alert-error">
                    <p>Station "${sessionScope.stationName}" was not found.</p>
                </div>
            </c:if>
        </c:when>
    </c:choose>

</div>