<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <%--@elvariable id="confirm" type="java.lang.Boolean"--%>
    <c:when test="${confirm}">
        <jsp:useBean id="book" scope="request" type="edu.pdx.ssn.application.Book"/>
        <p>Book successfully created:</p>
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
        <a href="${pageContext.request.contextPath}/?app=admin">Back</a>
    </c:when>
    <c:otherwise>
        <jsp:useBean id="err" scope="request" type="java.lang.String"/>
        <c:if test="${not empty err}">
            <p>${err}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/?app=admin&page=create_new" method="post">
            ISBN: <input type="text" name="isbn" size=13 value=""  maxlength=20> <br/><br/>
            Title: <input type="text" name="title" size=25 value=""  maxlength=255> <br/><br/>
            Author: Last: <input type="text" name="last" size=25 value=""  maxlength=255> First: <input type="text" name="first" size=25 value=""  maxlength=255><br/><br/>
            Professors (Comma Separated|Blank): <input type="text" name="profs" size=25 value=""  maxlength=255><br/><br/>
            Course Subject (Or Blank): <input type="text" name="subj" size=25 value=""  maxlength=255><br/><br/>
            Course Number (Or Blank): <input type="text" name="num" size=25 value=""  maxlength=255><br/><br/>
            <input type="submit" value="Submit" >
        </form>
    </c:otherwise>
</c:choose>






