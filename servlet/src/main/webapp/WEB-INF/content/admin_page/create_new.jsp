<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${confirm}">
        <p>Book successfully created</p>
        <a href="${pageContext.request.contextPath}/?app=admin">Back</a>
    </c:when>
    <c:otherwise>
        <form action="${pageContext.request.contextPath}/?app=admin&page=create_new">
            Barcode: <input type="text" name="code" size=10 value=""  maxlength=20> <br/> <br/>
            ISBN: <input type="text" name="isbn" size=10 value=""  maxlength=20> <br/><br/>
            Title: <input type="text" name="title" size=25 value=""  maxlength=255> <br/><br/>
            Author: Last: <input type="text" name="last" size=25 value=""  maxlength=255> First: <input type="text" name="first" size=25 value=""  maxlength=255><br/><br/>
            Professors (Comma Separated|Blank): <input type="text" name="profs" size=25 value=""  maxlength=255><br/><br/>
            Course Subject (Or Blank): <input type="text" name="subj" size=25 value=""  maxlength=255><br/><br/>
            Course Number (Or Blank): <input type="text" name="num" size=25 value=""  maxlength=255><br/><br/>
            Donor (or Blank): <input type="text" name="donor" size=25 value=""  maxlength=255><br/><br/>
            Donor Return (or Blank): <input type="text" name="return" size=25 value=""  maxlength=255><br/><br/>
            <input type="submit" value="Submit">
        </form>
    </c:otherwise>
</c:choose>






