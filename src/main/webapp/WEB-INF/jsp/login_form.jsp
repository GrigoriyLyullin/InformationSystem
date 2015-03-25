<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form class="form-signin" onsubmit="return checkSignInForm(this)"
      action="${pageContext.request.contextPath}/j_spring_security_check" method="post">

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div id="form-signin-body">
        <h2 class="form-signin-heading">Sign in</h2>
        <input name="login" id="username" type="text" class="input-block-level"
               placeholder="Username">
        <input name="password" id="password" type="password" class="input-block-level"
               placeholder="Password">

        <div class="alert alert-error" id="loginAlert">
            <p>Incorrect username or password.</p>
        </div>
        <c:choose>
            <%--<c:when test="${requestScope.signInMessage}">--%>
                <%--<div class="alert alert-error" id="loginError">--%>
                    <%--<p>Please log in to <c:out value="${sessionScope.signInMessage}">to continue</c:out></p>--%>
                <%--</div>--%>
                <%--<c:set scope="session" var="signInMessage" value="null"/>--%>
            <%--</c:when>--%>
            <c:when test="${not empty error}">
                <div class="alert alert-error" id="loginError">
                    <p>User with such data does not exist</p>
                </div>
            </c:when>
        </c:choose>
        <button class="btn btn-medium btn-primary" type="submit">Sign in</button>
    </div>
</form>