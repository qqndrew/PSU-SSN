<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
    <tr>
        <td>ISBN</td>
        <td>Title</td>
        <td>Author</td>
    </tr>
    <c:forEach var="book" items="books">
        <tr>
            <td>${book.isbn}</td>
            <td><a href = "${pageContext.request.contextPath}/?app=details&isbn=${book.isbn}">${book.title}</a></td>
            <td>${book.authorLast}, ${book.authorFirst}</td>
        </tr>
    </c:forEach>
</table>

