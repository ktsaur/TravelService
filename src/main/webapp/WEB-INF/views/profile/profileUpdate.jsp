<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

<div class="container mt-5 d-flex justify-content-center align-items-center" style="height: 50vh;">
    <div class="card" style="width: 30rem;">
        <div class="card-body">
            <h5 class="card-title">Обновить персональную информацию</h5>

            <form action="${pageContext.request.contextPath}/profile/update" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="user_id" value="${user.getUser_id()}" />

                <div class="mb-3">
                    <label for="username" class="form-label">Имя пользователя</label>
                    <input type="text" class="form-control" id="username" name="username" value="${user.getUsername()}" required>
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Почта</label>
                    <input type="email" class="form-control" id="email" name="email" value="${user.getEmail()}" required>
                </div>

                <div class="mb-3">
                    <label for="file" class="form-label">Загрузить фото</label>
                    <input type="file" id="file" name="file" class="form-control">
                </div>

                <button type="submit" class="btn btn-primary" name="action" value="updateInfo">Обновить информацию</button>
            </form>

<%--            <div class="card mt-4">--%>
<%--                <div class="card-header bg-light">--%>
<%--                    <h5 class="mb-0">Загрузить фото</h5>--%>
<%--                </div>--%>
<%--                <div class="card-body">--%>
<%--                    <form action="<c:url value='/profile/update'/>" method="post" enctype="multipart/form-data" class="d-flex align-items-center">--%>
<%--                        <input type="hidden" name="user_id" value="${user.getUser_id()}">--%>
<%--                        <input type="file" name="file" class="form-control me-3">--%>
<%--                        <button type="submit" class="btn btn-primary">Загрузить</button>--%>
<%--                    </form>--%>
<%--                </div>--%>
<%--            </div>--%>

            <c:if test="${not empty message}">
                <div class="alert alert-danger mt-3" role="alert">
                        ${message}
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/views/footer/_footer.jsp" %>

