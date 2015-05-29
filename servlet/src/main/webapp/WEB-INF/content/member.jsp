<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
  <c:when test="${sessionScope.IS_LOGGED_IN}">
    <jsp:useBean id="user" scope="request" type="edu.pdx.ssn.application.Member"/>
    <p>User ID: ${user.uid}</p>
    <p>E-Mail: ${user.email}</p>
    <p>Phone Number: ${user.phone}</p>
    <p>Currently Associated With Your Account:
    <table>
      <tr>
        <td>ISBN</td>
        <td>Title</td>
        <td>Status</td>
        <td>Hold End/Due Date/Loan End</td>
      </tr>
      <c:forEach var="record" items="${user.borrowedRecords}">
        <tr>
          <td>${record.book.ISBN}</td>
          <td>${record.book.title}</td>
          <td>
            <c:choose>
              <c:when test="${record.checkoutState == 2}">
                Checked Out
              </c:when>
              <c:when test="${record.checkoutState == 1}">
                Held (Awaiting Pickup)
              </c:when>
              <c:otherwise>
                Unknown -- Please contact the SSN office
              </c:otherwise>
            </c:choose>
          </td>
          <td>${record.dueDate}</td>
        </tr>
      </c:forEach>
      <c:forEach var="record" items="${user.loanedRecords}">
        <tr>
          <td>${record.book.ISBN}</td>
          <td>${record.book.title}</td>
          <td>
            <c:choose>
              <c:when test="${record.checkoutState == 2}">
                Loaned By You: Currently Checked Out
              </c:when>
              <c:when test="${record.checkoutState == 1}">
                Loaned By You: Currently on Hold but at the SSN office
              </c:when>
              <c:when test="${record.checkoutState == -1}">
                Removed From Circulation: Please return ASAP
              </c:when>
              <c:otherwise>
                Loaned By You: Available for pickup
              </c:otherwise>
            </c:choose>
          </td>
          <td>${record.dueDate}</td>
        </c:forEach>
    </table>
    </p>
  </c:when>
  <c:otherwise>
    <c:set var="message" scope="request" value="You must be logged in!"/>
    <jsp:include page="/WEB-INF/content/login.jsp"/>
  </c:otherwise>
</c:choose>
<br/>
<p><a href="${requestScope.request.contextPath}/?app=idx">Back to Home Page</a></p>
