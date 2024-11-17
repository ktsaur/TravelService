<%@ tag description="Main Layout Tag" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="title" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>${title}</title>

    <script src="<c:url value="/WEB-INF/js/bootstrap.min.js"/>"></script>

    <link rel="stylesheet" href="<c:url value='/style/all.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/style/main.css'/>">

  </head>
  <body>
  <%@include file="/WEB-INF/views/navigation/_nav.jsp" %>
<%--  <%@ include file="/WEB-INF/views/header.jsp" %>--%>
    <div class="container">
      <jsp:doBody/>
    </div>
<%--  <%@ include file="/WEB-INF/views/footer.jsp" %>--%>
  </body>
</html>
