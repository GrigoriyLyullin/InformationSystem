<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<div class="hero-unit" id="form-signin">
    <form class="form-signin" onsubmit="return checkForm(this)" method="post" action="login">
        <div id="form-signin-body">
            <h2 class="form-signin-heading">Sign in</h2>
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