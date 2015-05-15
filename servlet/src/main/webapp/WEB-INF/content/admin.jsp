<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
  <c:when test="${sessionScope.IS_LOGGED_IN} && ${sessionScope.ADMIN}">
    <jsp:include page="/WEB-INF/content/admin_page/${param.page}.jsp"/>
  </c:when>
  <c:otherwise>
    <c:choose>
      <c:when test="${sessionScope.IS_LOGGED_IN}">
        <p>You do not have the required permission level (administration) to access this page</p>
      </c:when>
      <c:otherwise>
        <jsp:include page="/WEB-INF/content/login.jsp"/>
      </c:otherwise>
    </c:choose>
  </c:otherwise>
</c:choose>