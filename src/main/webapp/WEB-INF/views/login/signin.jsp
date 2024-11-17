<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Вход">

    <c:if test="${not empty message}">
        <p class="alert alert-warning">${message}</p>
    </c:if>

    <form action="<c:url value="/signin"/>" method="POST">
        <div class="form-group">
            <label for="username">Имя пользователя:</label>
            <input id="username" name="username" class="form-control" type="text" value=""/>
        </div>
        <div class="form-group">
            <label for="password">Пароль:</label>
            <input id="password" name="password" class="form-control" type="password" value=""/>
        </div>
        <button type="submit" class="btn btn-success">Sign in</button>
    </form>


    <p class="mt-3 text-center">Нет аккаунта? <a href="<c:url value='/registration' />">Зарегистрируйтесь</a></p>

</t:mainLayout>
