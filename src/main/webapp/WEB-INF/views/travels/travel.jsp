<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

<div class="container mt-5">
  <div class="card shadow-sm border-primary">
    <div class="card-header bg-primary text-white">
      <h3 class="mb-0">Детали путешествия</h3>
    </div>
    <div class="card-body">
      <div class="row mb-3">
        <div class="col-md-8">
          <p><strong>Название путешествия:</strong> ${travel.getName_travel()}</p>
          <p><strong>Описание:</strong> ${travel.getDescription()}</p>
          <p><strong>Дата начала:</strong> ${travel.getStart_date()}</p>
          <p><strong>Дата окончания:</strong> ${travel.getEnd_date()}</p>
          <p><strong>Транспорт:</strong> ${travel.getTransport()}</p>
          <p><strong>Список вещей, которые нужно взять с собой:</strong> ${travel.getList_of_things()}</p>
          <p><strong>Заметки:</strong> ${travel.getNotes()}</p>
        </div>
        <div class="col-md-4">
          <c:choose>
            <c:when test="${not empty travel.getTravel_url()}">
              <img src="${travel.getTravel_url()}" alt="Travel Photo" class="img-fluid rounded shadow">
            </c:when>
            <c:otherwise>
              <p class="text-muted">Фото не загружено</p>
            </c:otherwise>
          </c:choose>
        </div>
      </div>

      <div class="d-flex justify-content-between align-items-center">
        <form action="<c:url value='/travel/detail'/>" method="post" class="me-3">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="travel_id" value="${travel.getTravel_id()}">
          <button type="submit" class="btn btn-danger">Удалить путешествие</button>
        </form>

        <a href="<c:url value='/travel/update?travel_id=${travel.getTravel_id()}'/>" class="btn btn-warning">Редактировать</a>
      </div>
    </div>
  </div>

  <div class="card mt-4">
    <div class="card-header bg-light">
      <h5 class="mb-0">Загрузить фото</h5>
    </div>
    <div class="card-body">
      <form action="<c:url value='/travel/detail'/>" method="post" enctype="multipart/form-data" class="d-flex align-items-center">
        <input type="hidden" name="travel_id" value="${travel.getTravel_id()}">
        <input type="file" name="file" class="form-control me-3">
        <button type="submit" class="btn btn-primary">Загрузить</button>
      </form>
    </div>
  </div>

  <div class="mt-4">
    <a href="<c:url value='/travel/list'/>" class="btn btn-secondary">
      <i class="bi bi-arrow-bar-left"></i> Назад к списку путешествий
    </a>
  </div>
</div>

<%@include file="/WEB-INF/views/footer/_footer.jsp" %>
