<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="book" scope="request" type="edu.pdx.ssn.application.Book"/>
<c:if test="${book != null}">
  <b>Confirm Details</b>
  <tr>
    <td>ISBN</td>
    <td>Title</td>
    <td>Author</td>
    <td>Due Date</td>
  </tr>
  <tr>
    <td>${book.ISBN}</td>
    <td>${book.title}</td>
    <td>${book.authorLast}, ${book.authorFirst}</td>
    <c:if test="${book.dueDate.time != 0}">
      <td>${book.dueDate.toLocaleString()}</td>
    </c:if>
    <c:otherwise>
      <td>"Indefinite Loan"</td>
    </c:otherwise>
  </tr>
  <input type="submit" value="Back"> <input type="submit" value="Continue">
</c:if>
<c:otherwise>
  Barcode:<input type="text" name="code" size=10 value=""  maxlength=10><input type="submit" value="Continue">
</c:otherwise>