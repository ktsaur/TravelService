<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

  <div class="container mt-5">

      <style>
          .fixed-img {
              width: 100%; /* Ширина изображения совпадает с шириной карточки */
              height: 200px; /* Фиксированная высота для всех изображений */
              object-fit: cover; /* Сохранение пропорций и заполнение области */
          }
      </style>

      <div class="btn-toolbar justify-content-between" role="toolbar" aria-label="Toolbar with button groups">
          <div class="btn-group" role="group" aria-label="First group">
              <a href="?filter=upcoming" class="btn btn-outline-secondary">Планирующиеся</a>
              <a href="?filter=completed" class="btn btn-outline-secondary">Завершенные</a>
              <a href="?filter=all" class="btn btn-outline-secondary">Все</a>
          </div>
          <form method="GET" action="">
              <div class="input-group">
                  <input type="text" name="search" class="form-control" placeholder="Введите название" aria-label="Input group example" aria-describedby="btnGroupAddon2">
                  <button type="submit" class="btn">Поиск</button>
              </div>
          </form>

      </div>

    <c:if test="${travels.isEmpty()}">
      <p class="mb-5">Ваш список пуст</p>
    </c:if>

    <a href="<c:url value='/travel/create'/>" class="d-flex align-items-center" style="text-decoration: none;">
      <i class="bi bi-plus-circle-fill fs-4" width="30" height="30"></i>
      <span class="ms-2">Создать новое путешествие</span><br>
        <br>
    </a>

      <c:if test="${!travels.isEmpty()}">
          <div class="card-group">
              <div class="row row-cols-1 row-cols-md-3 g-4">
                  <c:forEach var="travel" items="${travels}">
                      <div class="col">
                          <div class="card h-100">
                              <img src="${travel.getTravel_url()}" class="card-img-top fixed-img" alt="Travel photo">
                              <div class="card-body">
                                  <h5 class="card-title">${travel.getName_travel()}</h5>
                                  <p class="card-text">${travel.getDescription()}</p>
                                  <a href="<c:url value="/travel/detail?travel_id=${travel.getTravel_id()}"/>" class="btn btn-primary">Детали путешествия</a>
                              </div>
                          </div>
                      </div>
                  </c:forEach>
              </div>
          </div>
      </c:if>

  </div>

<%@include file="/WEB-INF/views/footer/_footer.jsp" %>
