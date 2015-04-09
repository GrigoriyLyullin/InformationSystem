<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="span4 buy-ticket-span-disabled">
    <h3>Step 2</h3>
    <h4>Passenger information:</h4>

    <p>Enter passenger information below.</p>

    <form class="buy-ticket-from">
        <p>First name: </p>
        <input type="text" class="input-medium" placeholder="First name"
               value="<c:out value="${sessionScope.firstName}"/>" disabled>

        <p>Last name: </p>
        <input type="text" class="input-medium" placeholder="Last name"
               value="<c:out value="${sessionScope.lastName}"/>" disabled>

        <p>Date of birth: </p>
        <input class="input-medium" data-format="yyyy-mm-dd" type="text" autocomplete="off"
               placeholder="yyyy-mm-dd" value="<c:out value="${sessionScope.birthdate}"/>" disabled>
    </form>
</div>