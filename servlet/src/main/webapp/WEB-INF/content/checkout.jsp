<jsp:useBean id="book" scope="request" type="edu.pdx.ssn.application.Book"/>
<jsp:useBean id="now" class="java.util.Date" />
<jsp:useBean id="library" type="edu.pdx.ssn.application.Library" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="due_date" value ="${now.time + 6652800000}"/>
<c:choose>
  <c:when test="${requestScope.containsKey(\"confirmed\")}">
    <c:choose>
      <c:when test="!${sessionScope.IS_LOGGED_IN}">
        <p>Error: You must be logged in!</p>
        <jsp:include page="login.jsp" />
      </c:when>
      <c:otherwise>
        <c:choose>
          <c:when test="${library.checkout(book.barcode, sessionScope.USER_ID, due_date)}" >
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
    <p><a href = "${pageContext.request.contextPath}/?app=checkout&confirm=true&uid=${book.barcode}">Confirm</a></p>
  </c:otherwise>
</c:choose>