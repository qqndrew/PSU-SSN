<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.IS_LOGGED_IN} && ${sessionScope.ADMIN}">
  <jsp:include page="/WEB-INF/content/admin/${param.page}.jsp"/>
</c:if>
<c:otherwise>
  <c:if test="${sessionScope.IS_LOGGED_IN}">
    <p>You do not have the required permission level (administration) to access this page</p>
  </c:if>
  <c:otherwise>
    <jsp:include page="/WEB-INF/content/login.jsp"/>
  </c:otherwise>
</c:otherwise>