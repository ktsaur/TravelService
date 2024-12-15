<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  .card img {
    width: 100%; /* Фиксированная ширина */
    height: auto; /* Фиксированная высота */
    /*object-fit: cover; !* Убедитесь, что изображение заполняет заданные размеры *!*/
  }
</style>
<div class="card mb-4" style="width: 18rem;">
  <c:choose>
    <c:when test="${not empty user.url}">
      <img src="${user.url}" alt="Фото профиля" class="card-img-top" width="100" height="100">
    </c:when>
    <c:otherwise>
      <p>Фото не загружено</p>
    </c:otherwise>
  </c:choose>
<%--  <div class="card-body">--%>
<%--    <h5 class="card-title">Персональная информация</h5>--%>
<%--  </div>--%>
  <ul class="list-group list-group-flush">
    <li class="list-group-item">
      Имя пользователя: ${user.getUsername()} <br>
      Почта: ${user.getEmail()}
    </li>
  </ul>
  <div class="card-body">
    <a href="<c:url value='/profile/update'/>" class="card-link">Изменить информацию</a>
  </div>
</div>

