<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
  <c:when test="${sessionScope.IS_LOGGED_IN && sessionScope.ADMIN}">
    <jsp:useBean id="admpage" scope="request" type="java.lang.String"/>
    <jsp:include page="/WEB-INF/content/admin_page/${admpage}.jsp"/>
  </c:when>
  <c:otherwise>
    <c:choose>
      <c:when test="${sessionScope.IS_LOGGED_IN}">
        <p>You do not have the required permission level (administration) to access this page</p>
        <a href="${requestScope.request.contextPath}/?app=idx">Back to Home Page</a>
      </c:when>
      <c:otherwise>
        <c:set var="message" scope="request" value=""/>
        <jsp:include page="/WEB-INF/content/login.jsp"/>
      </c:otherwise>
    </c:choose>
  </c:otherwise>
</c:choose>