<jsp:useBean id="book" scope="request" type="edu.pdx.ssn.application.Book"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<p>Title: ${book.title}</p>
<p>ISBN: ${book.isbn}</p>
<p>Author: ${book.authorLast}, ${book.authorFirst}</p>
<p>Associated Course: ${book.subject} ${book.number}</p>
<p>Associated Professor: ${book.professor}</p> <!--TODO: multiples-->
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
    <c:if test="${books.size} > 0">
        <c:forEach var="item" items="books">
            <tr>
                <td>${item.uid}</td>
                <c:choose>
                    <c:when test="${item.in_circulation()}">
                        <td>Checked In</td>
                        <td>--</td>
                        <td><a href = "${pageContext.request.contextPath}/?app=checkout&uid=${item.uid}">Checkout</a></td>
                    </c:when>
                    <c:when test="${item.checked_out}">
                        <td>Checked Out</td>
                        <td><fmt:formatDate value="${item.due_date}" pattern="MM-dd-yy"/></td>
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
    </c:if>
</table>