<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Профиль">
  <div class="container mt-5">
<%--    <h1>Profile</h1>--%>
    <h3>Информация о пользователе</h3>

    <c:if test="${user != null}">
      <p><strong>Имя пользователя: </strong>${user.getUsername()}</p>
      <p><strong>Электронная почта: </strong> ${user.getEmail()}</p>
      <p><strong>Пароль: </strong> ${user.getPassword()}</p>
    </c:if>

    <hr>
    <form action="<c:url value='/profile' />" method="post">
      <input type="hidden" name="action" value="delete">
      <button type="submit" class="btn btn-danger">Удалить аккаунт</button>
    </form>
  </div>
</t:mainLayout>
