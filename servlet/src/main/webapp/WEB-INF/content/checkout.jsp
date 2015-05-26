<jsp:useBean id="record" scope="request" type="edu.pdx.ssn.application.Record"/>
<jsp:useBean id="due_date" scope="request" class="java.util.Date" />
<jsp:useBean id="library" scope="request" type="edu.pdx.ssn.application.Library" />
<jsp:useBean id="confirm" scope="request" type="java.lang.Boolean" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
  <c:when test="${confirm}">
    <c:choose>
      <c:when test="${!sessionScope.IS_LOGGED_IN}">
        <c:set var="message" scope="request" value="Error: you must be logged in!"/>
        <jsp:include page="login.jsp" />
      </c:when>
      <c:otherwise>
        <c:choose>
          <c:when test="${library.checkout(record.barcode, sessionScope.USER_ID, due_date)}" >
            <p>Hold Successful: please go to the SSN office to pick up your book. Your due date is <fmt:formatDate value="${due_date}" pattern="MM-dd-yy"/></p>
          </c:when>
          <c:otherwise>
            <p>Hold Unsuccessful: Most likely someone else placed a hold on the same book before you!</p>
          </c:otherwise>
        </c:choose>
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:otherwise>
    <jsp:useBean id="book" scope="request" type="edu.pdx.ssn.application.Book"/>
    <p>Title: ${book.title}</p>
    <p>ISBN: ${book.ISBN}</p>
    <p>Author: ${book.authorLast}, ${book.authorFirst}</p>
    <p>Associated Course: ${book.subject} ${book.number}</p>
    <p>Associated Professors:
    <ul>
      <c:forEach var="professor" items="${book.professors}">
        <li>${professor}</li>
      </c:forEach>
    </ul>
    </p>
    <p>Due Date: <fmt:formatDate value="${due_date}" pattern="MM-dd-yy"/></p>
    <p><a href = "${pageContext.request.contextPath}/?app=checkout&confirm=true&uid=${record.barcode}">Confirm</a></p>
  </c:otherwise>
</c:choose>