<%--<%@page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

<div class="container mt-5">
  <h3>${article.getTitle()}</h3>

  <p>${article.getContent()}</p>

  <p><strong>Дата опубликования:</strong>${article.getCreated_date()}</p>

</div>

<%@include file="/WEB-INF/views/footer/_footer.jsp" %>

<%--<t:mainLayout title="Статья">--%>
<%--  <div class="container mt-5">--%>
<%--    <h3>${article.getTitle()}</h3>--%>

<%--    <p>${article.getContent()}</p>--%>

<%--    <p><strong>Дата опубликования:</strong>${article.getCreated_date()}</p>--%>

<%--  </div>--%>
<%--</t:mainLayout>--%>
