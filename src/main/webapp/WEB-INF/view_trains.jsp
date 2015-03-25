<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="well">
    <h2>View all trains</h2>

    <p>View information about all trains
        <c:choose>
            <c:when test="${empty sessionScope.viewAllTrainsList}">
                <a href="${pageContext.request.contextPath}/view_all_trains?viewAllTrains=true"
                   class="height20 btn btn-primary">
                    <i class="icon-white icon-chevron-down"></i>
                </a>
            </c:when>
            <c:when test="${not empty sessionScope.viewAllTrainsList}">
                <a href="${pageContext.request.contextPath}/view_all_trains?viewAllTrains=false"
                   class="height20 btn btn-primary active">
                    <i class="icon-white icon-chevron-up"></i>
                </a>
            </c:when>
        </c:choose>
    </p>

    <c:if test="${not empty sessionScope.viewAllTrainsList}">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Train id</th>
                <th>Train number</th>
                <th>Train seat count</th>
                <th>Train route</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.viewAllTrainsList}" var="train">
                <tr>
                    <td>${train.id}</td>
                    <td>${train.number}</td>
                    <td>${train.seats}</td>
                    <td>
                        <c:forEach items="${train.stations}" var="station">
                            ${station.id}.${station.name}
                            Arrival: <fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                                     value="${station.arrivalDate}"/>
                            Departure: <fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                                       value="${station.departureDate}"/>
                            <br>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>