<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
  <c:when test="${param.confirm}">
    <jsp:useBean id="record" scope="request" type="edu.pdx.ssn.application.Record"/>
    <b>Confirm Details</b>
    <table>
      <tr>
        <td>ISBN</td>
        <td>Title</td>
        <td>Author</td>
        <td>Due Date</td>
      </tr>
      <tr>
        <td>${record.book.ISBN}</td>
        <td>${record.book.title}</td>
        <td>${record.book.authorLast}, ${record.book.authorFirst}</td>
        <c:choose>
          <c:when test="${record.dueDate.time != 0}">
            <td>${record.dueDate.toLocaleString()}</td>
          </c:when>
        </c:choose>
      </tr>
    </table>
    <a href="${requestScope.request.contextPath}/?app=admin&page=checkin&confirm=false">Back</a> <a href="${requestScope.request.contextPath}/?app=admin&page=checkin&code=${record.barcode}&confirm=true&continue=true">Continue</a>
  </c:when>
  <c:otherwise>
    <jsp:useBean id="err" scope="request" type="java.lang.String"/>
    <c:if test="${not empty err}">
      <p>${err}</p>
    </c:if>
    <form action="${requestScope.request.contextPath}/?app=admin&page=checkin&confirm=false&continue=true" method="post">
      Barcode:<input type="text" name="code" size=10 value=""  maxlength=10><input type="submit" value="Continue">
    </form>
  </c:otherwise>
</c:choose>