<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
  <c:when test="${param.confirm}">
    <jsp:useBean id="book" scope="request" type="edu.pdx.ssn.application.Book"/>
    <form action="${requestScope.request.contextPath}/?app=admin&page=editbook&confirm=true&isbn=${book.ISBN}" method="post">
      ISBN: ${book.ISBN} <br/>
      Title: <input type="text" name="title" size=25 value="${book.title}"  maxlength=255> <br/>
      Author: Last: <input type="text" name="last" size=25 value="${book.authorLast}"  maxlength=255> First: <input type="text" name="first" size=25 value="${book.authorFirst}"  maxlength=255><br/>
      <c:set var="build" value=""/>
      <c:forEach var="prof" items="${book.professors}">
        <c:choose>
          <c:when test="${empty profs}">
            <c:set var="build" value="${prof}"/>
          </c:when>
          <c:otherwise>
            <c:set var="build" value="${build + \",\" + prof}"/>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      Professors (Comma Separated|Blank): <input type="text" name="profs" size=25 value="${build}"  maxlength=255><br/>
      Course Subject: <input type="text" name="subj" size=25 value="${book.subject}"  maxlength=255><br/>
      Course Number: <input type="text" name="num" size=25 value="${book.number}"  maxlength=255><br/>
      <input type="submit" value="Submit">
    </form>
  </c:when>
  <c:otherwise>
    <form action="${requestScope.request.contextPath}/?app=admin&page=editbook&confirm=false" method="post">
      ISBN: <input type="text" name="code" size=10 value=""  maxlength=13><input type="submit" value="Continue">
    </form>
  </c:otherwise>
</c:choose>




