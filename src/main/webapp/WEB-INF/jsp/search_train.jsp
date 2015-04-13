<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="well">

    <div class="header">
        <h2>Search train</h2>
    </div>

    <p>Enter the route and the date of the travel to choose the train: </p>

    <div id="inputFromSearchTrain">
        <form class="form-inline" id="search-train-form" onsubmit="return checkSearchTrainForm(this)" method="get"
              action="${pageContext.request.contextPath}/search_train">

            <div class="input-prepend">

                <span class="add-on">From</span>
                <input id="search-train-station-from-name" name="station-from-name" type="text" class="input-large"
                       placeholder="Station name" value="<c:out value="${sessionScope.stationFromName}"/>"
                       autocomplete="off" data-provide="typeahead">

                <span class="add-on">To</span>
                <input id="search-train-station-to-name" name="station-to-name" type="text" class="input-large"
                       placeholder="Station name" value="<c:out value="${sessionScope.stationToName}"/>"
                       autocomplete="off" data-provide="typeahead">

                <span class="add-on">Departure date</span>
                <input id="search-train-date-from" name="date-from" class="input-medium"
                       type="text"
                       autocomplete="off" value="<c:out value="${sessionScope.dateFrom}"/>" title="Date"
                       placeholder="yyyy-mm-dd">
            </div>
            <button type="submit" class="btn btn-primary" id="btn-search-train">
                <i class="icon-white icon-search"></i>
            </button>
            <p class="hidden">Invalid input data</p>
        </form>
    </div>

    <div class="alert alert-error" id="searchTrainAlert">
        <p>Incorrect input data. Try again.</p>
    </div>

    <div id="search-train-table-ajax-preloader">
        <img src="${pageContext.request.contextPath}/resources/img/preloader.gif"/>
    </div>

    <div>
        <div id="search-train-table-ajax"></div>
        <div id="search-train-table-ajax-empty" class="alert alert-error"></div>
        <div class="centered-button">
            <a href="#" class="btn centered-button-form" id="search-train-ajax-previous">
                <i class="icon-arrow-left"></i>
            </a>
            <a href="#" class="btn centered-button-form" id="search-train-ajax-next">
                <i class="icon-arrow-right"></i>
            </a>
        </div>
    </div>

    <c:choose>
        <c:when test="${not empty requestScope.trainList}">
            <table class="body-table table table-bordered" id="search-train-table">
                <thead>
                <tr>
                    <th>Train number</th>
                    <th>${sessionScope.stationFromName} departure time</th>
                    <th>${sessionScope.stationToName} arrival time</th>
                    <th>Buy ticket</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.trainList}" var="scheduleData">
                    <tr>
                        <td>${scheduleData.trainNumber}</td>
                        <td>
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${scheduleData.timeDeparture}"/>
                        </td>
                        <td>
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${scheduleData.timeArrival}"/>
                        </td>
                        <td>
                            <sec:authorize access="isAnonymous()">
                                <form action="${pageContext.request.contextPath}buy_ticket" method="get">
                                    <a href="${pageContext.request.contextPath}/login?msg=true"
                                       type="submit" class="btn disabled btn-block">Buy</a>
                                </form>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <form action="${pageContext.request.contextPath}/buy_ticket" method="post">
                                    <input type="hidden" name="trainNumber" value="${scheduleData.trainNumber}">
                                    <input type="hidden" name="timeDeparture" value="${scheduleData.timeDeparture}">
                                    <button type="submit" class="btn btn-block btn-success">Buy</button>
                                </form>
                            </sec:authorize>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="centered-button">
                <c:if test="${requestScope.searchTrainStartNumber ne 0}">
                    <form class="centered-button-form" method="get"
                          action="${pageContext.request.contextPath}/search_train/previous">
                        <input type="hidden" name="station-from-name"
                               value="<c:out value="${sessionScope.stationFromName}"/>">
                        <input type="hidden" name="station-to-name"
                               value="<c:out value="${sessionScope.stationToName}"/>">
                        <input type="hidden" name="date-from"
                               value="<c:out value="${sessionScope.dateFrom}"/>">
                        <input type="hidden" name="schedule-by-station-start-number"
                               value="${requestScope.searchTrainStartNumber}">
                        <button class="btn" id="search-train-previous"><i class="icon-arrow-left"></i></button>
                    </form>
                </c:if>
                <c:if test="${requestScope.searchTrainStartNumber lt requestScope.searchTrainMaxSize}">
                    <form class="centered-button-form" method="get"
                          action="${pageContext.request.contextPath}/search_train/next">
                        <input type="hidden" name="station-from-name"
                               value="<c:out value="${sessionScope.stationFromName}"/>">
                        <input type="hidden" name="station-to-name"
                               value="<c:out value="${sessionScope.stationToName}"/>">
                        <input type="hidden" name="date-from"
                               value="<c:out value="${sessionScope.dateFrom}"/>">
                        <input type="hidden" name="schedule-by-station-start-number"
                               value="${requestScope.searchTrainStartNumber}">
                        <button class="btn" id="search-train-next"><i class="icon-arrow-right"></i></button>
                    </form>
                </c:if>
            </div>
        </c:when>
        <c:when test="${empty requestScope.trainList}">
            <c:choose>
                <c:when test="${requestScope.trainSearchingInvalidInput}">
                    <div class="alert alert-error" id="trainSearchingErrorAlert">
                        <p>Invalid input data.</p>
                    </div>
                </c:when>
                <c:when test="${requestScope.stationWithSuchNameDoesNotExist}">
                    <div class="alert alert-error" id="trainSearchingErrorAlert">
                        <p>Station ${requestScope.stationDoesNotExist} does not exist.</p>
                    </div>
                </c:when>
                <c:when test="${requestScope.trainNotFoundError}">
                    <div class="alert alert-error" id="trainNotFoundAlert">
                        <p>Train from station "${sessionScope.stationFromName}" to "${sessionScope.stationToName}"
                            on date ${sessionScope.dateFrom} was not found.</p>
                    </div>
                </c:when>
            </c:choose>
        </c:when>
    </c:choose>
</div>