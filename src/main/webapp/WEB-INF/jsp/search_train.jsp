<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="well">

    <div class="header">
        <h2>Search train</h2>
    </div>

    <p>Enter the route and the date of the travel to choose the train: </p>

    <c:choose>
        <c:when test="${empty sessionScope.extendedForm || sessionScope.extendedForm == false}">
            <jsp:include page="/WEB-INF/jsp/search_train_simple_form.jsp" flush="true"/>
        </c:when>
        <c:when test="${sessionScope.extendedForm == true}">
            <jsp:include page="/WEB-INF/jsp/search_train_extended_form.jsp" flush="true"/>
        </c:when>
    </c:choose>

    <div class="alert alert-error" id="searchTrainAlert">
        <p>Incorrect input data. Try again.</p>
    </div>

    <c:choose>
        <c:when test="${not empty sessionScope.trainList}">
            <table class="body-table table table-bordered">
                <thead>
                <tr>
                    <th>Train number</th>
                    <th>${sessionScope.stationFromName} departure time</th>
                    <th>${sessionScope.stationToName} arrival time</th>
                    <th>Buy ticket</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.trainList}" var="train">
                    <tr>
                        <td>${train.trainNumber}</td>
                        <td>
                            <fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                            value="${train.timeArrival}"/>
                        </td>
                        <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                            value="${train.timeDeparture}"/>
                        </td>
                        <td>
                            <sec:authorize access="isAnonymous()">
                                <form action="${pageContext.request.contextPath}buy_ticket" method="get">
                                    <a href="${pageContext.request.contextPath}/login"
                                       type="submit" class="btn disabled btn-block">Buy</a>
                                </form>
                            </sec:authorize>
                            <sec:authorize access="isAuthenticated()">
                                <form action="${pageContext.request.contextPath}/buy_ticket" method="post">
                                    <input type="hidden" name="trainNumber" value="${train.trainNumber}">
                                    <input type="hidden" name="timeDeparture" value="${train.timeDeparture}">
                                    <button type="submit" class="btn btn-block btn-success">Buy</button>
                                </form>
                            </sec:authorize>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:when test="${empty sessionScope.trainList}">
            <c:choose>
                <c:when test="${sessionScope.trainSearchingError}">
                    <div class="alert alert-error" id="trainSearchingErrorAlert">
                        <p>Invalid input data.</p>
                    </div>
                    <c:set scope="session" var="trainSearchingError" value="false"/>
                </c:when>
                <c:when test="${sessionScope.trainNotFoundError}">
                    <div class="alert alert-error" id="trainNotFoundAlert">
                        <p>Train from station "${sessionScope.stationFromName}" to "${sessionScope.stationToName}"
                            on date ${sessionScope.dateFrom} was not found.</p>
                    </div>
                    <c:set scope="session" var="trainNotFoundError" value="false"/>
                </c:when>
            </c:choose>
        </c:when>
    </c:choose>
</div>