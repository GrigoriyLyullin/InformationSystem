<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="well">
    <h2>Add schedule</h2>

    <p>Here you can add new station into database.</p>

    <div id="inputFromAddSchedule">
        <form class="form-inline" action="${pageContext.request.contextPath}/add_schedule" method="post">
            <div class="input-prepend">
                <span class="add-on">Station id</span>
                <input name="stationId" type="text" class="input-medium" placeholder="Station id">
                <%--Sorry non-breaking space.--%>
                <span class="add-on">&nbsp;&nbsp;&nbsp;&nbsp;Arrival datetime&nbsp;&nbsp;</span>
                <input name="arrivalDate" type="date" class="input-medium" placeholder="dd.mm.yyyy">
                <input name="arrivalTime" type="time" class="input-small" placeholder="hh:mm">
            </div>
            <div class="input-prepend">
                <span class="add-on">&nbsp;Train id&nbsp;&nbsp;</span>
                <input name="trainId" type="text" class="input-medium" placeholder="Train id">
                <span class="add-on">Departure datetime</span>
                <input name="departureDate" type="date" class="input-medium" placeholder="dd.mm.yyyy">
                <input name="departureTime" type="time" class="input-small" placeholder="hh:mm">
            </div>
            <button type="submit" class="height30 btn btn-success">
                <i class="icon-white icon-plus"></i>
            </button>
        </form>
        <c:choose>
            <c:when test="${sessionScope.invalidAddScheduleInputDataError}">
                <div class="alert alert-error">
                    <p>Incorrect input data.</p>
                </div>
                <c:set scope="session" var="invalidAddScheduleInputDataError" value="false"/>
            </c:when>
            <c:when test="${sessionScope.invalidAddScheduleInputDataArrivalGtDepartureError}">
                <div class="alert alert-error">
                    <p>Incorrect input data. Arrival date cannot be greater than departure or equals.</p>
                </div>
                <c:set scope="session" var="invalidAddScheduleInputDataArrivalGtDepartureError" value="false"/>
            </c:when>
            <c:when test="${sessionScope.AddScheduleTrainWithIdDoesNotExistError}">
                <div class="alert alert-error">
                    <p>Train with id "${sessionScope.doesNotExistTrainId}" does not exist in database.</p>
                </div>
                <c:set scope="session" var="AddScheduleTrainWithIdDoesNotExistError" value="false"/>
                <c:set scope="session" var="doesNotExistTrainId" value="empty"/>
            </c:when>
            <c:when test="${sessionScope.AddScheduleStationWithIdDoesNotExistError}">
                <div class="alert alert-error">
                    <p>Train with id "${sessionScope.doesNotExistStationId}" does not exist in database.</p>
                </div>
                <c:set scope="session" var="AddScheduleStationWithIdDoesNotExistError" value="false"/>
                <c:set scope="session" var="doesNotExistStationId" value="empty"/>
            </c:when>
            <c:when test="${sessionScope.AddScheduleSuchScheduleExistError}">
                <div class="alert alert-error">
                    <p>Such schedule already exist in the database.</p>
                </div>
                <c:set scope="session" var="AddScheduleSuchScheduleExistError" value="false"/>
            </c:when>
            <c:when test="${sessionScope.AddScheduleSuccess}">
                <div class="alert alert-success">
                    <p>Schedule has been successfully added in the database.</p>
                </div>
                <c:set scope="session" var="AddScheduleSuccess" value="false"/>
            </c:when>
        </c:choose>
    </div>
</div>