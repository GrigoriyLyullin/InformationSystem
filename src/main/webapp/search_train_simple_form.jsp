<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="inputFromSearchTrain">
    <form class="form-inline" onsubmit="return checkSearchTrainForm(this)" method="post"
          action="${pageContext.request.contextPath}search_train">

        <div class="input-prepend">

            <span class="add-on">From</span>
            <input id="Station-From-Name" name="Station-From-Name" type="text" class="input-large"
                   placeholder="Station name" value="<c:out value="${sessionScope.stationFromName}"/>">

            <span class="add-on">To</span>
            <input id="Station-To-Name" name="Station-To-Name" type="text" class="input-large"
                   placeholder="Station name" value="<c:out value="${sessionScope.stationToName}"/>">

            <span class="add-on">Date</span>
            <input id="dateFrom" name="dateFrom" type="date" class="input-medium" title="Date"
                   value="<c:out value="${sessionScope.dateFrom}"/>">
        </div>


        <a href="${pageContext.request.contextPath}search_train?extendedForm=true" class="btn btn-primary"
           id="extendedFormButton">
            <i class="icon-white icon-plus"></i>
        </a>

        <button type="submit" class="btn btn-success">
            <i class="icon-white icon-search"></i>
        </button>

    </form>
</div>