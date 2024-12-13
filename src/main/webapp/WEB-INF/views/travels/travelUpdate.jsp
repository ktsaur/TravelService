<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

<div class="container mt-5">
  <div class="card shadow-sm">
    <div class="card-header bg-primary text-white">
      <h3 class="mb-0">Редактирование путешествия</h3>
    </div>
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/travel/update" method="post">
        <input type="hidden" name="travel_id" value="${travel.travel_id}">

        <div class="mb-3">
          <label for="name_travel" class="form-label">Название:</label>
          <input type="text" name="name_travel" id="name_travel"
                 value="${travel.name_travel}" class="form-control" required>
        </div>

        <div class="mb-3">
          <label for="description" class="form-label">Описание:</label>
          <textarea name="description" id="description"
                    class="form-control" rows="4" required>${travel.description}</textarea>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="start_date" class="form-label">Дата начала:</label>
            <input type="date" name="start_date" id="start_date"
                   value="${travel.start_date}" class="form-control" required>
          </div>
          <div class="col-md-6 mb-3">
            <label for="end_date" class="form-label">Дата окончания:</label>
            <input type="date" name="end_date" id="end_date"
                   value="${travel.end_date}" class="form-control" required>
          </div>
        </div>

        <div class="mb-3">
          <label for="transport" class="form-label">Транспорт:</label>
          <input type="text" name="transport" id="transport"
                 value="${travel.transport}" class="form-control" required>
        </div>

        <div class="mb-3">
          <label for="list_of_things" class="form-label">Список вещей:</label>
          <textarea name="list_of_things" id="list_of_things"
                    class="form-control" rows="3" required>${travel.list_of_things}</textarea>
        </div>

        <div class="mb-3">
          <label for="notes" class="form-label">Заметки:</label>
          <textarea name="notes" id="notes"
                    class="form-control" rows="3">${travel.notes}</textarea>
        </div>

        <div class="d-flex justify-content-between">
          <button type="submit" class="btn btn-primary">Обновить</button>
          <a href="${pageContext.request.contextPath}/travel/detail?travel_id=${travel.travel_id}"
             class="btn btn-secondary">Отмена</a>
        </div>
      </form>
    </div>
  </div>
</div>

<%@include file="/WEB-INF/views/footer/_footer.jsp" %>

