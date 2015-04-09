<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/index.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/buy_ticket.css">
<div class="span4 buy-ticket-span">

    <a href="${pageContext.request.contextPath}/buy_ticket?step=1" id="btn-back-step-2" class="btn btn-primary"><i
            class="icon-white icon-arrow-left"></i></a>

    <h3>Step 2</h3>
    <h4>Passenger information:</h4>

    <p>Enter passenger information below.</p>

    <form class="buy-ticket-form" action="${pageContext.request.contextPath}/buy_ticket/step_2" method="post">
        <p>First name: </p>
        <c:if test="${requestScope.invalidFirstName}">
        <div class="buy-ticket-form-error">
            </c:if>
            <input type="text" name="firstName" class="input-medium" placeholder="First name"
                   value="<c:out value="${sessionScope.firstName}"/>">
            <c:if test="${requestScope.invalidFirstName}">
            <p>Invalid first name</p>
        </div>
        </c:if>

        <p>Last name: </p>
        <c:if test="${requestScope.invalidLastName}">
        <div class="buy-ticket-form-error">
            </c:if>
            <input type="text" name="lastName" class="input-medium" placeholder="Last name"
                   value="<c:out value="${sessionScope.lastName}"/>">
            <c:if test="${requestScope.invalidLastName}">
            <p>Invalid last name</p>
        </div>
        </c:if>

        <p>Date of birth: </p>
        <c:if test="${requestScope.invalidBirthdate}">
        <div class="buy-ticket-form-error">
            </c:if>
            <input id="buy-ticket-birthdate-from" name="birthdate" class="input-medium" data-format="yyyy-mm-dd"
                   type="text" autocomplete="off"
                   placeholder="yyyy-mm-dd" value="<c:out value="${sessionScope.birthdate}"/>">
            <c:if test="${requestScope.invalidBirthdate}">
            <p>Invalid date of birth</p>
        </div>
        </c:if>
        <c:choose>
            <c:when test="${requestScope.invalidInputDataException}">
                <div class="alert alert-error">
                    <p>Such train does not exist. Check inputs and try again</p>
                </div>
            </c:when>
            <c:when test="${requestScope.alreadyRegistered}">
                <div class="alert alert-error">
                    <p>Passenger with the same data already registered on the train.</p>
                </div>
            </c:when>
        </c:choose>
        <button class="btn btn-primary">Next step</button>
    </form>
</div>