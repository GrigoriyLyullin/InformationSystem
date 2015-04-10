<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
<div class="well">

    <div class="header">
        <h2>Schedule by station</h2>
    </div>

    <div id="inputFromSearchTrain">
        <form class="form-inline" method="get" action="${pageContext.request.contextPath}/schedule_by_station">
            <div class="input-prepend">
                <span class="add-on">Station</span>
                <input id="schedule-by-station-name" name="schedule-by-station-name" type="text" class="input-large"
                       placeholder="Station name" value="<c:out value="${sessionScope.scheduleByStationName}"/>"
                       autocomplete="off" data-provide="typeahead">
            </div>
            <button type="submit" class="btn btn-primary" id="btn-schedule-by-station">
                <i class="icon-white icon-search"></i>
            </button>
        </form>
    </div>

    <c:choose>
        <c:when test="${not empty requestScope.scheduleByStationDataList}">
            <table class="body-table table table-bordered">
                <thead>
                <tr>
                    <th>Train number</th>
                    <th>Departure</th>
                        <%--<th>Stations</th>--%>
                    <th>Buy ticket</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.scheduleByStationDataList}" var="scheduleData">
                    <tr>
                        <td>${scheduleData.trainNumber}</td>
                        <td>
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${scheduleData.timeDeparture}"/>
                        </td>
                            <%--<td>--%>
                            <%--TODO Вывести список станций, через которые следует поезд--%>
                            <%--</td>--%>
                        <td>
                            <sec:authorize access="isAnonymous()">
                                <a href="${pageContext.request.contextPath}/login"
                                   class="btn disabled btn-block btn-buy btn-buy-disabled">Buy</a>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <form action="${pageContext.request.contextPath}/buy_ticket/step_1" method="post">
                                    <input type="hidden" name="trainNumber"
                                           value="${scheduleData.trainNumber}">
                                    <input type="hidden" name="dispatchStation"
                                           value="<c:out value="${requestScope.stationName}"/>">
                                    <input type="hidden" name="departureDate"
                                           value="<fmt:formatDate pattern="yyyy-MM-dd"
                                           value="${scheduleData.timeDeparture}"/>">
                                    <input type="hidden" name="departureTime"
                                           value="<fmt:formatDate pattern="HH:mm"
                                           value="${scheduleData.timeDeparture}"/>">
                                    <button type="submit" class="btn btn-block btn-success btn-buy">Buy</button>
                                </form>
                            </sec:authorize>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="centered-button">
                <c:if test="${requestScope.scheduleByStationStartNumber ne 0}">
                    <form class="centered-button-form" method="get"
                          action="${pageContext.request.contextPath}/schedule_by_station/previous">
                        <input type="hidden" name="schedule-by-station-name"
                               value="<c:out value="${sessionScope.scheduleByStationName}"/>">
                        <input type="hidden" name="schedule-by-station-start-number"
                               value="${requestScope.scheduleByStationStartNumber}">
                        <button class="btn" id="schedule-by-station-previous"><i class="icon-arrow-left"></i></button>
                    </form>
                </c:if>
                <c:if test="${requestScope.scheduleByStationStartNumber lt requestScope.scheduleByStationMaxSize}">
                    <form class="centered-button-form" method="get"
                          action="${pageContext.request.contextPath}/schedule_by_station/next">
                        <input type="hidden" name="schedule-by-station-name"
                               value="<c:out value="${sessionScope.scheduleByStationName}"/>">
                        <input type="hidden" name="schedule-by-station-start-number"
                               value="${requestScope.scheduleByStationStartNumber}">
                        <button class="btn" id="schedule-by-station-next"><i class="icon-arrow-right"></i></button>
                    </form>
                </c:if>
            </div>
        </c:when>
        <c:when test="${empty requestScope.scheduleDataList}">
            <c:if test="${requestScope.scheduleByStationNotFound}">
                <div class="alert alert-error">
                    <p>Station "${sessionScope.scheduleByStationName}" was not found.</p>
                </div>
            </c:if>
        </c:when>
    </c:choose>

</div>