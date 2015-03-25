<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="inputFromSearchTrain">
    <form class="form-inline" method="post"
          action="${pageContext.request.contextPath}/schedule_by_station">
        <div class="input-prepend">
            <span class="add-on">Station</span>
            <input id="Station-Name" name="Station-Name" type="text" class="input-large"
                   placeholder="Station name" value="<c:out value="${sessionScope.stationName}"/>">
        </div>
        <button type="submit" class="btn btn-success">
            <i class="icon-white icon-search"></i>
        </button>
    </form>
</div>