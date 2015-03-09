<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="well">
    <h2>View all passengers</h2>

    <p>View information about all passengers that has been registered on selected train.</p>

    <div id="inputFromSearchTrain">
        <form class="form-inline" method="post" action="${pageContext.request.contextPath}view_all_passengers">
            <div class="input-prepend">
                <span class="add-on">Train id</span>
                <input name="trainId" type="text" class="input-small" placeholder="Train id">
            </div>
            <button type="submit" class="btn btn-success">
                <i class="icon-white icon-search"></i>
            </button>
            <c:if test="${not empty sessionScope.allPassengerList}">
                <a id="passengerCollapseBtn"
                   href="${pageContext.request.contextPath}view_all_passengers?hideAllPassengers=true"
                   class="btn btn-primary">
                    <i class="icon-white icon-chevron-up"></i>
                </a>
            </c:if>
        </form>
        <c:choose>
            <c:when test="${sessionScope.invalidTrainIdError}">
                <div class="alert alert-error">
                    <p>Incorrect train id.</p>
                </div>
                <c:set scope="session" var="invalidTrainIdError" value="false"/>
            </c:when>
            <c:when test="${sessionScope.passengersNotFound}">
                <div class="alert alert-info">
                    <p>
                        None of the passenger is not yet registered in the train with id
                        "${sessionScope.passengersNotFoundTrainId}".
                    </p>
                </div>
                <c:set scope="session" var="passengersNotFound" value="false"/>
                <c:set scope="session" var="passengersNotFoundTrainId" value="null"/>
            </c:when>
        </c:choose>
    </div>

    <c:if test="${not empty sessionScope.allPassengerList}">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Passenger id</th>
                <th>Passenger name</th>
                <th>Passenger surname</th>
                <th>Passenger birthdate</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.allPassengerList}" var="passenger">
                <tr>
                    <td>${passenger.id}</td>
                    <td>${passenger.name}</td>
                    <td>${passenger.surname}</td>
                    <td>${passenger.birthdate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%--Total: <c:out value="${sessionScope.allPassengerList.numberOfItems}"/>--%>
    </c:if>
</div>