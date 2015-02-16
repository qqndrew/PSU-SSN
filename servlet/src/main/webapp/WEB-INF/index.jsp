<jsp:include page="/WEB-INF/templates/template_global.jsp">
    <jsp:param name="title" value="${requestScope.title}"/>
    <jsp:param name="content" value="${requestScope.app}"/>
</jsp:include>