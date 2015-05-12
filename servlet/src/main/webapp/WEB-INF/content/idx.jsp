<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href = "${pageContext.request.contextPath}/?app=catalog">
    Book Catalog
</a>
<br>
<a href = "${pageContext.request.contextPath}/?app=checkout">
    Checkout Book
</a>
<br>
<c:choose>
    <c:when test="${sessionScope.IS_LOGGED_IN}">
        <c:choose>
            <c:when test="${sessionScope.ADMIN}">
                <a href = "${pageContext.request.contextPath}/?app=admin">
                    Administration Panel
                </a>
                <br>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
        <a href = "${pageContext.request.contextPath}/?app=logout">
            Logout
        </a>
    </c:when>
    <c:otherwise>
        <a href = "${pageContext.request.contextPath}/?app=login">
            Login
        </a>
    </c:otherwise>
</c:choose>


