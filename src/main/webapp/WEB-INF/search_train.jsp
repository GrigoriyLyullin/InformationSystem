<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="well">

    <div class="header">
        <h2>Search train</h2>
    </div>

    <p>Enter the route and the date of the travel to choose the train: </p>

    <c:choose>
        <c:when test="${empty sessionScope.extendedForm || sessionScope.extendedForm == false}">
            <jsp:include page="/WEB-INF/search_train_simple_form.jsp" flush="true"/>
        </c:when>
        <c:when test="${sessionScope.extendedForm == true}">
            <jsp:include page="/WEB-INF/search_train_extended_form.jsp" flush="true"/>
        </c:when>
    </c:choose>

    <div class="alert alert-error" id="searchTrainAlert">
        <p>Incorrect input data. Try again.</p>
    </div>

    <c:choose>
        <c:when test="${not empty sessionScope.trainList}">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Train number</th>
                    <th>${sessionScope.stationFromName} departure time</th>
                    <th>${sessionScope.stationToName} arrival time</th>
                    <%--<th>Buy ticket</th>--%>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.trainList}" var="train">
                    <tr>
                        <td>${train.trainNumber}</td>
                        <td>${train.timeArrival}</td>
                        <td>${train.timeDeparture}</td>
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
        <c:when test="${empty sessionScope.trainList}">
            <c:choose>

                <c:when test="${sessionScope.trainSearchingError}">
                    <div class="alert alert-error" id="trainSearchingErrorAlert">
                        <p>Error! Invalid input data.</p>
                    </div>
                </c:when>

                <c:when test="${sessionScope.trainNotFoundError}">
                    <div class="alert alert-error" id="trainNotFoundAlert">
                        <p>Train from station "${sessionScope.stationFromName}" to "${sessionScope.stationToName}"
                            was not found.</p>
                    </div>
                </c:when>

            </c:choose>
        </c:when>
    </c:choose>
</div>