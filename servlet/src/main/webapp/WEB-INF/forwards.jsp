<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--Intended for passing messages between pages--%>
<jsp:useBean id="forward" scope="request" type="java.lang.String"/>
<c:if test="${not empty forward}">
    <p>${forward}</p>
</c:if>