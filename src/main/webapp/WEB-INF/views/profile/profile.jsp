<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

<div class="container mt-5 d-flex flex-column align-items-center" style="height: 70vh;">
    <div class="card mb-4" style="width: 18rem;">
        <c:choose>
            <c:when test="${not empty user.url}">
                <img src="${user.url}" alt="Фото профиля" class="card-img-top" width="100" height="100">
            </c:when>
            <c:otherwise>
                <p>Фото не загружено</p>
            </c:otherwise>
        </c:choose>

        <div class="card-body">
            <h5 class="card-title">Персональная информация</h5>
        </div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">
                Имя пользователя: ${user.getUsername()} <br>
                Почта: ${user.getEmail()}
            </li>
        </ul>
        <div class="card-body">
            <a href="<c:url value='/profile/update'/>" class="card-link">Изменить информацию</a>
        </div>

<%--        <div class="card mt-4">--%>
<%--            <div class="card-header bg-light">--%>
<%--                <h5 class="mb-0">Загрузить фото</h5>--%>
<%--            </div>--%>
<%--            <div class="card-body">--%>
<%--                <form action="<c:url value='/profile'/>" method="post" enctype="multipart/form-data">--%>
<%--                    <div class="card-body">--%>
<%--                        <h5 class="card-title">Загрузить фото профиля</h5>--%>
<%--                        <input type="file" name="file" accept="image/*" class="form-control mb-2" required>--%>
<%--                        <button type="submit" class="btn btn-primary">Загрузить</button>--%>
<%--                    </div>--%>
<%--                </form>--%>

<%--            </div>--%>
<%--        </div>--%>

    </div>

    <!-- Вторая карточка с изменением пароля и удалением аккаунта -->
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">Управление аккаунтом</h5>
        </div>
        <div class="list-group">
            <!-- Элемент для изменения пароля -->
<%--            <a href="<c:url value='/profile/updatePassword'/>" class="list-group-item list-group-item-action">--%>
<%--                Изменить пароль--%>
<%--            </a>--%>
            <form action="<c:url value='/profile/updatePassword'/>" method="post" class="list-group-item list-group-item-action">
                <input type="hidden" name="action" value="updatePassword">
                <button type="submit" class="btn">Изменить пароль</button>
            </form>

            <!-- Элемент для удаления аккаунта -->
            <form action="<c:url value='/profile'/>" method="post" class="list-group-item list-group-item-action">
                <input type="hidden" name="action" value="delete">
                <button type="submit" class="btn">Удалить аккаунт</button>
            </form>
        </div>
    </div>

<%--    <form action="<c:url value='/profile' />" method="post">--%>
<%--        <input type="hidden" name="action" value="delete">--%>
<%--        <button type="submit" class="btn btn-danger">Удалить аккаунт</button>--%>
<%--    </form>--%>

<%--    <form action="<c:url value='/profile' />" method="post">--%>
<%--        <input type="hidden" name="action" value="delete">--%>
<%--        <button type="submit" class="btn btn-danger">Удалить аккаунт</button>--%>
<%--    </form>--%>

</div>

<%--<div class="modal" tabindex="-1" id="deleteModal" aria-labelledby="deleteModalLabel" aria-hidden="true">--%>
<%--    <div class="modal-dialog">--%>
<%--        <div class="modal-content">--%>
<%--            <div class="modal-header">--%>
<%--                <h5 class="modal-title" id="deleteModalLabel">Удаление аккаунта</h5>--%>
<%--                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>--%>
<%--            </div>--%>
<%--            <div class="modal-body">--%>
<%--                <p>Вы действительно хотите удалить аккаунт?</p>--%>
<%--            </div>--%>
<%--            <div class="modal-footer">--%>
<%--                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Да</button>--%>
<%--                <form action="<c:url value='/profile'/>" method="post" style="display: inline;">--%>
<%--                    <input type="hidden" name="action" value="delete">--%>
<%--                    <button type="button" class="btn btn-primary">Нет</button>--%>
<%--                </form>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<style>
    .card img {
        max-width: 100%; /* Ограничивает размер изображения */
        height: auto;
    }
</style>

<%--<%@include file="/WEB-INF/views/footer/_footer.jsp" %>--%>
