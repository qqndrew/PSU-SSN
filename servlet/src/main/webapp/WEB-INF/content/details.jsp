<jsp:useBean id="book" scope="request" type="edu.pdx.ssn.application.Book"/>
<jsp:useBean id="books" scope="request" type="java.util.Collection<edu.pdx.ssn.application.Book>"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<p>Title: ${book.title}</p>
<p>ISBN: ${book.ISBN}</p>
<p>Author: ${book.authorLast}, ${book.authorFirst}</p>
<p>Associated Course: ${book.subject} ${book.number}</p>
<p>Associated Professors:</p>
<ul>
    <c:forEach var="professor" items="${book.professors}">
        <li>${professor}</li>
    </c:forEach>
</ul>
<br/>
<br/>
<p>Available Copies</p>
<table>
    <tr>
        <td>Barcode</td>
        <td>Circulation Status</td>
        <td>Due Date</td>
        <td>Checkout</td>
    </tr>
    <c:choose>
        <c:when test="${books.size() > 0}">
            <c:forEach var="item" items="${books}">
                <tr>
                    <td>${item.barcode}</td>
                    <c:choose>
                        <c:when test="${item.in_circulation()}">
                            <td>Checked In</td>
                            <td>--</td>
                            <td><a href = "${pageContext.request.contextPath}/?app=checkout&uid=${item.barcode}">Place Hold</a></td>
                        </c:when>
                        <c:when test="${item.dueDate.time != 0}">
                            <td>Held or Checked Out</td>
                            <td><fmt:formatDate value="${item.dueDate}" pattern="MM-dd-yy"/></td>
                            <td>--</td>
                        </c:when>
                        <c:otherwise>
                            <td>Not in Circulation</td>
                            <td>--</td>
                            <td>--</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </c:when>
    </c:choose>
</table>