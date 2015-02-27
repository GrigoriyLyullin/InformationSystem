<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="hero-unit" id="form-signin">
    <form class="form-signin" onsubmit="return checkSignInForm(this)" method="post" action="login">
        <div id="form-signin-body">
            <h2 class="form-signin-heading">Sign in</h2>

            <c:if test="${not empty sessionScope.signUpErrorUrl}">
                <p>Please sign in to access requested page</p>
            </c:if>

            <input name="login" id="username" type="text" class="input-block-level"
                   placeholder="Username or Email address">
            <input name="password" id="password" type="password" class="input-block-level"
                   placeholder="Password">

            <div class="alert alert-error" id="loginAlert">
                <p>Incorrect username or password.</p>
            </div>
            <button class="btn btn-medium btn-primary" type="submit">Sign in</button>
        </div>
    </form>
</div>