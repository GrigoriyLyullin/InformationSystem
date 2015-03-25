<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="well">
    <h2>Add station</h2>

    <p>Here you can add new station into database.</p>

    <div id="inputFromAddStation">
        <form class="form-inline" action="${pageContext.request.contextPath}/add_station" method="post">
            <div class="input-prepend">
                <span class="add-on">New station</span>
                <input name="stationName" type="text" class="input-medium" placeholder="Station name">
            </div>
            <button type="submit" class="height30 btn btn-success">
                <i class="icon-white icon-plus"></i>
            </button>
            <c:choose>
                <c:when test="${ empty sessionScope.allStationList}">
                    <a href="${pageContext.request.contextPath}/add_station?getAllStation=true"
                       class="height20 btn btn-primary">
                        <i class="icon-white icon-list"></i>
                    </a>
                </c:when>
                <c:when test="${not empty sessionScope.allStationList}">
                    <a href="${pageContext.request.contextPath}/add_station?getAllStation=false"
                       class="height20  btn btn-primary active">
                        <i class="icon-white icon-list"></i>
                    </a>
                </c:when>
            </c:choose>
        </form>
        <c:choose>
            <c:when test="${sessionScope.invalidStationNameError}">
                <div class="alert alert-error">
                    <p>Incorrect station name.</p>
                </div>
                <c:set scope="session" var="invalidStationNameError" value="false"/>
            </c:when>
            <c:when test="${sessionScope.existStationError}">
                <div class="alert alert-error">
                    <p>Station with name "${sessionScope.existStationName}" already exist in the database.</p>
                </div>
                <c:set scope="session" var="existStationError" value="false"/>
                <c:set scope="session" var="existStationName" value="false"/>
            </c:when>
        </c:choose>
    </div>

    <c:if test="${not empty sessionScope.allStationList}">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Station id</th>
                <th>Station name</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.allStationList}" var="station">
                <tr>
                    <td>${station.id}</td>
                    <td>${station.name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

</div>
