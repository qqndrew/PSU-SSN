<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE HTML public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Student Support Network - ${param.title}</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/style.css" /> <!--TODO-->
</head>
<body>
<jsp:include page="/WEB-INF/header.jsp"/>
<jsp:include page="/WEB-INF/forwards.jsp"/>
<jsp:include page="/WEB-INF/content/${param.content}.jsp"/>
<jsp:include page="/WEB-INF/footer.jsp"/>
</body>
</html>

</body>
</html>
