<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/index.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style/buy_ticket.css">
<div class="span4 buy-ticket-span">

    <a href="${pageContext.request.contextPath}/buy_ticket?step=2" id="btn-back-step-3" class="btn btn-primary"><i
            class="icon-white icon-arrow-left"></i></a>

    <h3>Step 3</h3>
    <h4>Payment:</h4>

    <p>Input payment information and pay the ticket.</p>

    <p id="total-cost">Total cost: <c:out value="${sessionScope.ticketCost}"> - </c:out> rub</p>

    <form class="buy-ticket-from" action="${pageContext.request.contextPath}/buy_ticket/step_3" method="post">
        <p>Card number: </p>
        <c:if test="${requestScope.invalidCardNumber}">
        <div class="buy-ticket-form-error">
            </c:if>
            <input type="text" name="cardNumber" value="<c:out value="${sessionScope.cardNumber}"/>"
                   class="input-medium" placeholder="0000 0000 0000 0000" maxlength="16">
            <c:if test="${requestScope.invalidCardNumber}">
            <p>Invalid card number</p>
        </div>
        </c:if>

        <p>Card holder: </p>
        <c:if test="${requestScope.invalidCardHolder}">
        <div class="buy-ticket-form-error">
            </c:if>
            <input type="text" name="cardHolder" value="<c:out value="${sessionScope.cardHolder}"/>"
                   class="input-medium"
                   placeholder="CARD HOLDER" maxlength="27">
            <c:if test="${requestScope.invalidCardHolder}">
            <p>Invalid card holder</p>
        </div>
        </c:if>

        <p>CVC/CVV: </p>
        <c:if test="${requestScope.invalidCardCVC}">
        <div class="buy-ticket-form-error">
            </c:if>
            <input type="text" name="cardCVC" class="input-medium" placeholder="CVC" maxlength="3">
            <c:if test="${requestScope.invalidCardCVC}">
            <p>Invalid card CVC/CVV code</p>
        </div>
        </c:if>

        <p>Expires: </p>
        <c:if test="${requestScope.invalidCardExpiresDate}">
        <div class="buy-ticket-form-error">
            </c:if>
            <input id="buy-ticket-expires-from" name="cardExpiresDate"
                   value="<c:out value="${sessionScope.cardExpiresDate}"/>"
                   class="input-medium" data-format="yyyy-mm"
                   type="text" autocomplete="off" placeholder="yyyy-mm">
            <c:if test="${requestScope.invalidCardExpiresDate}">
            <p>Invalid card expires date</p>
        </div>
        </c:if>
        <button class="btn btn-success">Buy ticket</button>
    </form>
</div>