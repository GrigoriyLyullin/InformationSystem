<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form class="form-signin" onsubmit="return checkSignInForm(this)"
      action="<c:url value="/j_spring_security_check"/>" method="post">

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
            <c:when test="${signInError}">
                <div class="alert alert-error" id="loginError">
                    <p>User with such data does not exist</p>
                </div>
            </c:when>
        </c:choose>
        <button class="btn btn-medium btn-primary" type="submit">Sign in</button>
    </div>
</form>