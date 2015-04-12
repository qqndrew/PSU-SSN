<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="books" scope="request" type="java.util.Collection<edu.pdx.ssn.application.Book>"/>
<table>
    <tr>
        <td>ISBN</td>
        <td>Title</td>
        <td>Author</td>
    </tr>
    <c:forEach var="book" items="${books}">
        <tr>
            <td>${book.ISBN}</td>
            <td><a href = "${pageContext.request.contextPath}/?app=details&isbn=${book.ISBN}">${book.title}</a></td>
            <td>${book.authorLast}, ${book.authorFirst}</td>
        </tr>
    </c:forEach>
</table>

