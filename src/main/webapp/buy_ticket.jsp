<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="style/index.css">
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/index.js"></script>
    <meta charset="utf-8">
    <title>Railway Company</title>
</head>
<body>

<div class="container">

    <div class="main-header">
        <h2>Railway Company</h2>
    </div>

    <jsp:include page="/navbar.jsp" flush="true"/>

    <div class="well">

        <div class="header">
            <h2>Buy ticket</h2>
        </div>

        <div>
            <form class="form-horizontal" onsubmit="return checkBuyTicketForm(this)" method="post"
                  action="buy_ticket">
                <div class="input-prepend" id="schedule_by_station">
                    <span class="add-on">Train number</span>
                    <input id="Train-Number" name="Train-Number" type="text" class="input-xlarge"
                           placeholder="Train number" value="<c:out value="${sessionScope.trainNumber}"/>">
                    <span class="add-on">From</span>
                    <input id="Station-From-Name" name="Station-From-Name" type="text" class="input-xlarge"
                           placeholder="Station name" value="<c:out value="${sessionScope.stationFromName}"/>">
                </div>
                <button type="submit" class="btn btn-success">Buy</button>
            </form>
        </div>
        <div class="alert alert-error" id="buyTicketAlert">
            <p>Incorrect input data. Try again.</p>
        </div>
    </div>


</div>
</body>
</html>
