<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="${pageContext.request.contextPath}/resources/js/index.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/index.css">
<div class="well">

    <div class="header">
        <h2>Schedule by station</h2>
    </div>

    <div id="inputFromSearchTrain">
        <form class="form-inline" id="schedule-by-station-form" method="get"
              action="${pageContext.request.contextPath}/schedule_by_station">
            <div class="input-prepend">
                <span class="add-on">Station</span>
                <input id="schedule-by-station-name" name="schedule-by-station-name" type="text" class="input-large"
                       placeholder="Station name" value="<c:out value="${sessionScope.scheduleByStationName}"/>"
                       autocomplete="off" data-provide="typeahead">
            </div>
            <button type="submit" class="btn btn-primary" id="btn-schedule-by-station">
                <i class="icon-white icon-search"></i>
            </button>
            <p class="hidden">Invalid station name</p>
        </form>
    </div>

    <sec:authorize access="isAnonymous()">
        <c:set value="anonymous" var="authorizeAccess"/>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <c:set value="authenticated" var="authorizeAccess"/>
    </sec:authorize>

    <input type="hidden" name="authorize-access" value="${authorizeAccess}">
    <input type="hidden" name="context-path" value="${pageContext.request.contextPath}">

    <div id="schedule-by-station-table-ajax-preloader">
        <img src="${pageContext.request.contextPath}/resources/img/preloader.gif"/>
    </div>

    <div>
        <div id="schedule-by-station-table-ajax"></div>
        <div id="schedule-by-station-table-ajax-empty" class="alert alert-error"></div>
        <div class="centered-button">
            <a href="#" class="btn centered-button-form" id="schedule-by-station-ajax-previous">
                <i class="icon-arrow-left"></i>
            </a>
            <a href="#" class="btn centered-button-form" id="schedule-by-station-ajax-next">
                <i class="icon-arrow-right"></i>
            </a>
        </div>
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
    </c:choose>
    <c:choose>
        <c:when test="${requestScope.scheduleByStationInvalidStationName}">
            <div class="alert alert-error">
                <p>Invalid station name "${sessionScope.scheduleByStationName}".</p>
            </div>
        </c:when>
        <c:when test="${requestScope.scheduleByStationNotFound}">
            <div class="alert alert-error">
                <p>Schedule by station "${sessionScope.scheduleByStationName}" was not found.</p>
            </div>
        </c:when>
        <c:when test="${requestScope.scheduleByStationNotExistStationName}">
            <div class="alert alert-error">
                <p>Station with name "${sessionScope.scheduleByStationName}" does not exist.</p>
            </div>
        </c:when>
    </c:choose>
</div>