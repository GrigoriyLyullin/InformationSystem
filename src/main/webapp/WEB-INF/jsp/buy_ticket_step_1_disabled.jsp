<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="span4 buy-ticket-span-disabled">
    <h3>Step 1</h3>
    <h4>Train information:</h4>

    <p> Enter train information below. If you do not know all the necessary information for the
        purchase of a ticket you can use
        <a href="${pageContext.request.contextPath}/index#search_train">search</a> or
        <a href="${pageContext.request.contextPath}/index#schedule_by_station">train schedule by station</a>.
    </p>

    <form class="buy-ticket-form">
        <p>Train number: </p>
        <input type="text" class="input-medium" placeholder="Train number"
               value="<c:out value="${sessionScope.trainNumber}"/>" disabled>

        <p>Dispatch station: </p>
        <input type="text" class="input-medium" placeholder="Station name"
               value="<c:out value="${sessionScope.dispatchStation}"/>" disabled>

        <p>Departure date: </p>

        <div id="buy-ticket-datetime-from">
            <input id="buy-ticket-date-from" class="input-medium" data-format="yyyy-mm-dd" type="text"
                   autocomplete="off" placeholder="yyyy-mm-dd"
                   value="<c:out value="${sessionScope.departureDate}"/>" disabled>
            <input id="buy-ticket-time-from" name="departureTime"
                   value="<c:out value="${sessionScope.departureTime}"/>" class="input-medium" type="text"
                   autocomplete="off" placeholder="hh:mm" disabled>
        </div>
    </form>
</div>