<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="span4 buy-ticket-span">
    <h3>Step 1</h3>
    <h4>Train information:</h4>

    <p> Enter train information below. If you do not know all the necessary information for the
        purchase of a ticket you can use
        <a href="${pageContext.request.contextPath}/index#search_train">search</a> or
        <a href="${pageContext.request.contextPath}/index#schedule_by_station">train schedule by station</a>.
    </p>

    <form class="buy-ticket-form" action="${pageContext.request.contextPath}/buy_ticket/step_1" method="post">
        <p>Train number: </p>
        <c:if test="${requestScope.invalidTrainNumber}">
        <div class="buy-ticket-form-error">
            </c:if>
            <input type="text" name="trainNumber" value="<c:out value="${sessionScope.trainNumber}"/>"
                   class="input-medium" placeholder="Train number">
            <c:if test="${requestScope.invalidTrainNumber}">
            <p>Invalid train number</p>
        </div>
        </c:if>

        <p>Dispatch station: </p>
        <c:if test="${requestScope.invalidDispatchStation}">
        <div class="buy-ticket-form-error">
            </c:if>
            <input type="text" name="dispatchStation" value="<c:out value="${sessionScope.dispatchStation}"/>"
                   class="input-medium" placeholder="Station name">
            <c:if test="${requestScope.invalidDispatchStation}">
            <p>Invalid dispatch station name</p>
        </div>
        </c:if>

        <p>Departure date: </p>

        <div id="buy-ticket-datetime-from">
            <c:if test="${requestScope.invalidDepartureDate}">
            <div class="buy-ticket-form-error"></c:if>
                <input id="buy-ticket-date-from" name="departureDate"
                       value="<c:out value="${sessionScope.departureDate}"/>" class="input-medium" type="text"
                       autocomplete="off" placeholder="yyyy-mm-dd">
                <input id="buy-ticket-time-from" name="departureTime"
                       value="<c:out value="${sessionScope.departureTime}"/>" class="input-medium" type="text"
                       autocomplete="off" placeholder="hh:mm">
                <c:if test="${requestScope.invalidDepartureDate}">
                <p>Invalid departure datetime</p>
            </div>
            </c:if>
        </div>
        <c:choose>
            <c:when test="${requestScope.invalidInputDataException}">
                <div class="alert alert-error">
                    <p>Such train does not exist. Check inputs and try again</p>
                </div>
            </c:when>
            <c:when test="${requestScope.hasNotEmptySeats}">
                <div class="alert alert-error">
                    <p>The train has not empty seats</p>
                </div>
            </c:when>
            <c:when test="${requestScope.hasNotEnoughTime}">
                <div class="alert alert-error">
                    <p>Ticket sales has been stopped because less than 10 minutes before dispatch left</p>
                </div>
            </c:when>
        </c:choose>
        <button class="btn btn-primary">Next step</button>
    </form>
</div>