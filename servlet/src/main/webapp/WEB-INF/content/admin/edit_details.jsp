<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="book" scope="request" type="edu.pdx.ssn.application.Book"/>
<c:if test="${book != null}">
  <c:if test="${book.dueDate.time != 0}">
    <p>This book is checked out, please check the book in prior to attempting to edit its details</p>
  </c:if>
  <c:otherwise>
    Barcode: ${book.barcode}
    ISBN: <input type="text" name="isbn" size=10 value="${book.ISBN}"  maxlength=10> <br/>
    Title: <input type="text" name="title" size=25 value="${book.title}"  maxlength=255> <br/>
    Author: Last: <input type="text" name="title" size=25 value="${book.authorLast}"  maxlength=255> First: <input type="text" name="title" size=25 value="${book.authorFirst}"  maxlength=255><br/>
    Professors (Comma Separated|Blank): <input type="text" name="profs" size=25 value=""  maxlength=255><br/>
    Course Subject: <input type="text" name="subj" size=25 value="${book.subject}"  maxlength=255><br/>
    Course Number: <input type="text" name="num" size=25 value="${book.number}"  maxlength=255><br/>
    Donor UUID (or Blank): <input type="text" name="donor" size=25 value="${book.loanerUid}"  maxlength=255><br/>
    Donor Return Date (or Blank): <input type="text" name="return" size=25 value="${book.loanEnd}"  maxlength=255><br/>
    <input type="submit" value="Submit">
  </c:otherwise>
</c:if>
<c:otherwise>
  Barcode:<input type="text" name="code" size=10 value=""  maxlength=10><input type="submit" value="Continue">
</c:otherwise>





