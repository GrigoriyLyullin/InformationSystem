<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="well">
    <h2>Add train</h2>

    <p>Add train page. Should be available only for employee</p>

    <div id="inputFromAddTrain">
        <form class="form-inline" action="${pageContext.request.contextPath}add_train" method="post">
            <div class="input-prepend">
                <span class="add-on">New train number</span>
                <input name="trainNumber" type="text" class="input-medium" placeholder="Train number">
                <span class="add-on">Seats</span>
                <input name="trainSeats" type="text" class="input-medium" placeholder="Train seats">
            </div>
            <button type="submit" class="btn btn-success">
                <i class="icon-white icon-plus"></i>
            </button>
            <c:choose>
                <c:when test="${ empty sessionScope.allTrainList}">
                    <a href="${pageContext.request.contextPath}add_train?getAllTrain=true" class="btn btn-primary">
                        <i class="icon-white icon-list"></i>
                    </a>
                </c:when>
                <c:when test="${not empty sessionScope.allTrainList}">
                    <a href="${pageContext.request.contextPath}add_train?getAllTrain=false"
                       class="btn btn-primary active">
                        <i class="icon-white icon-list"></i>
                    </a>
                </c:when>
            </c:choose>
        </form>
        <c:choose>
            <c:when test="${sessionScope.invalidInputDataError}">
                <div class="alert alert-error">
                    <p>Incorrect train number or seat count.</p>
                </div>
                <c:set scope="session" var="invalidInputDataError" value="false"/>
            </c:when>
            <c:when test="${sessionScope.existTrainWarning}">
                <div class="alert alert-error">
                    <p>Train with number "${sessionScope.existTrainNumber}" and seats "${sessionScope.existTrainSeats}"
                        already exist in the database. Click "Add anyway" to add this train into database.
                    </p>

                    <form action="${pageContext.request.contextPath}add_train" method="post">
                        <input type="hidden" name="trainNumber" value="${sessionScope.existTrainNumber}">
                        <input type="hidden" name="trainSeats" value="${sessionScope.existTrainSeats}">
                        <input type="hidden" name="addTrainAnyway" value="true">
                        <button type="submit" class="btn">Add anyway</button>
                    </form>
                </div>
                <c:set scope="session" var="existTrainWarning" value="false"/>
            </c:when>
        </c:choose>
    </div>

    <c:if test="${not empty sessionScope.allTrainList}">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Train id</th>
                <th>Train number</th>
                <th>Train seat count</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.allTrainList}" var="train">
                <tr>
                    <td>${train.id}</td>
                    <td>${train.number}</td>
                    <td>${train.seats}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>