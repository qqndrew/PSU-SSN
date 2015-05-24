<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${sessionScope.IS_LOGGED_IN}">
        <p>You are logged in</p>
        <a href="${pageContext.request.contextPath}/?app=idx">To main page</a>
        <a href="${pageContext.request.contextPath}/?app=logout">Logout</a>
    </c:when>
    <c:otherwise>
        <%--@elvariable id="message" type="java.lang.String"--%>
        <c:if test="${not empty message}">
            <p>${message}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/?app=login" method="post">
            <p>E-mail:<input type="text" name="user" size=10 value=""  maxlength=255></p>
            <p>Password:<input type="password" name="password" size=10 value="" maxlength=255></p>
            <input type="submit" value="Login">
        </form>
    </c:otherwise>
</c:choose>

