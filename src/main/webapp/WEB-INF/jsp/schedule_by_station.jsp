<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="well">

    <div class="header">
        <h2>Schedule by station</h2>
    </div>

    <jsp:include page="/WEB-INF/jsp/schedule_by_station_simple_form.jsp"/>

    <c:choose>
        <c:when test="${not empty sessionScope.scheduleList}">
            <table class="body-table table table-bordered">
                <thead>
                <tr>
                    <th>Train number</th>
                    <th>Departure</th>
                    <th>Stations</th>
                    <th>Buy ticket</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.scheduleList}" var="train">
                    <tr>
                        <td>${train.trainNumber}</td>
                        <td>
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${train.timeDeparture}"/>
                        </td>
                        <td>
                        </td>
                        <td>
                            <sec:authorize access="isAnonymous()">
                                <a href="${pageContext.request.contextPath}/login"
                                   class="btn disabled btn-block btn-buy btn-buy-disabled">Buy</a>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <form action="${pageContext.request.contextPath}/buy_ticket/step_1" method="post">
                                    <input type="hidden" name="trainNumber" value="${train.trainNumber}">
                                    <input type="hidden" name="dispatchStation"
                                           value="<c:out value="${sessionScope.stationName}"/>">
                                    <input type="hidden" name="departureDate"
                                           value="<fmt:formatDate pattern="yyyy-MM-dd"  value="${train.timeDeparture}"/>">
                                    <input type="hidden" name="departureTime"
                                           value="<fmt:formatDate pattern="HH:mm"  value="${train.timeDeparture}"/>">
                                    <button type="submit" class="btn btn-block btn-success btn-buy">Buy</button>
                                </form>
                            </sec:authorize>
                        </td>
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