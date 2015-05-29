<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="errmessage" scope="request" type="java.lang.String"/>
<c:if test="${not empty errmessage}">
  <p>${errmessage}</p>
</c:if>
<c:choose>
  <c:when test="${param.confirm}">
    <jsp:useBean id="user" scope="request" type="edu.pdx.ssn.application.Member"/>
    <form action="${pageContext.request.contextPath}/?app=admin&page=edit_member&confirm=true&uid=${user.uid}" method="post">
      <p>E-Mail: <input type="text" name="user" size=10 value="${user.email}"  maxlength=255 title="E-Mail"></p>
      <p>First Name: <input type="text" name="first" size="10" value="${user.firstName}" maxLength="255" title="First Name"></p>
      <p>Last Name: <input type="text" name="last" size="10" value="${user.lastName}" maxLength="255" title="Last Name"></p>
      <p>Phone Number: <input type="text" name="phone" size="10" value="${user.phone.replaceAll('[^0-9]', '')}" maxLength="12" title="Phone Number"></p>
      <p>Administrator: <input type="checkbox" name="admin" value="${user.isAdmin ? 1 : 0}"></p>
      <input type="submit" value="Register">
    </form>
  </c:when>
  <c:otherwise>
    <form action="${requestScope.request.contextPath}/?app=admin&page=edit_member&confirm=false" method="post">
      Member E-Mail: <input type="text" name="email" size=10 value=""  maxlength=30><input type="submit" value="Continue">
    </form>
  </c:otherwise>
</c:choose>




