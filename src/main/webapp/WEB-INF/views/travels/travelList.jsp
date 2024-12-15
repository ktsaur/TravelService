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

    <h3 class="mb-4">Список путешествий</h3><br>

    <c:if test="${travels.isEmpty()}">
      <p class="mb-5">Ваш список пуст</p>
    </c:if>

    <a href="<c:url value='/travel/create'/>" class="d-flex align-items-center" style="text-decoration: none;">
      <i class="bi bi-plus-circle-fill fs-4" width="30" height="30"></i>
      <span class="ms-2">Создать новое путешествие</span><br>
        <br>
    </a>

      <c:if test="${!travels.isEmpty()}">
          <!-- Вынесли группы карточек за пределы цикла -->
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
