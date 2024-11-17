<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Регистрация">
    <h2>Регистрация нового пользователя</h2><br>
    <c:if test="${not empty message}">
        <p>${message}</p>
    </c:if>

    <form action="<c:url value='/registration' />" method="POST">
        <div class="form-group">
            <label for="username">Имя пользователя:</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="email">Электронная почта:</label>
            <input type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
    </form>

    <p class="mt-3 text-center">Уже есть аккаунт? <a href="<c:url value='/signin' />">Войти</a></p>

</t:mainLayout>
