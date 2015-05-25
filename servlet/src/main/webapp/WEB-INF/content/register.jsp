<%@ c:taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="errmessage" scope="request" type="java.lang.String"/>
<c:if test="${not empty errmessage}">
  <p>${errmessage}</p>
</c:if>

<form action="${pageContext.request.contextPath}/?app=register" method="post">
  <p><input type="text" name="user" size=10 value=""  maxlength=255 title="E-Mail"></p>
  <p><input type="password" name="password" size=10 value="" maxlength=255 title="Password"></p>
  <p><input type="password" name="confpassword" size=10 value="" maxlength=255 title="Confirm Password"></p>
  <br/>
  <p><input type="text" name="first" size="10" value="" maxLength="255" title="First Name"></p>
  <p><input type="text" name="last" size="10" value="" maxLength="255" title="Last Name"></p>
  <p><input type="text" name="phone" size="10" value="" maxLength="12" title="Phone Number"></p>

  <input type="submit" value="Login">
</form>