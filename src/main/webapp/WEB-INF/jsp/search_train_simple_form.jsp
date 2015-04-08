<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="inputFromSearchTrain">
    <form class="form-inline" onsubmit="return checkSearchTrainForm(this)" method="post"
          action="${pageContext.request.contextPath}/search_train">

        <div class="input-prepend">

            <span class="add-on">From</span>
            <input id="Station-From-Name" name="Station-From-Name" type="text" class="input-large"
                   placeholder="Station name" value="<c:out value="${sessionScope.stationFromName}"/>"
                   autocomplete="off" data-provide="typeahead">

            <span class="add-on">To</span>
            <input id="Station-To-Name" name="Station-To-Name" type="text" class="input-large"
                   placeholder="Station name" value="<c:out value="${sessionScope.stationToName}"/>"
                   autocomplete="off" data-provide="typeahead">

            <span class="add-on">Departure date</span>
            <input id="dateFrom" name="dateFrom" type="date" class="input-medium" title="Date"
                   value="<c:out value="${sessionScope.dateFrom}"/>">
        </div>

        <button type="submit" class="btn btn-primary">
            <i class="icon-white icon-search"></i>
        </button>

    </form>
</div>