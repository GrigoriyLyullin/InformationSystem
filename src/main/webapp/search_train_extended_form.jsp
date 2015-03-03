<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="inputFromSearchTrain">

    <form class="form-inline" onsubmit="return checkSearchTrainForm(this)" method="post"
          action="search_train">

        <div class="input-prepend">

            <span class="add-on">From</span>
            <input id="Station-From-Name" name="Station-From-Name" type="text" class="input-xlarge"
                   placeholder="Station name" value="<c:out value="${sessionScope.stationFromName}"/>">

            <span class="add-on">To</span>
            <input id="Station-To-Name" name="Station-To-Name" type="text" class="input-xlarge"
                   placeholder="Station name" value="<c:out value="${sessionScope.stationToName}"/>">

        </div>
        <div class="input-prepend">

            <span class="add-on">Date from</span>
            <input id="dateFrom" name="dateFrom" type="date" class="input-medium" title="Date"
                   value="<c:out value="${sessionScope.dateFrom}"/>">
            <input id="timeFrom" name="timeFrom" type="time" class="input-small" title="Time">

            <span class="add-on">Date to</span>
            <input id="dateTo" name="dateTo" type="date" class="input-medium" title="Date"
                   value="<c:out value="${sessionScope.dateTo}"/>">
            <input id="timeTo" name="timeTo" type="time" class="input-small" title="Time">

        </div>

        <a id="extendedFormButton" href="/search_train?extendedForm=false" class="btn btn-primary">
            <i class="icon-white icon-minus"></i>
        </a>

        <button type="submit" class="btn btn-success">
            <i class="icon-white icon-search"></i>
        </button>

    </form>
</div>