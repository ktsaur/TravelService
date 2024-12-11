<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Редактировать путешествие">
  <div class="container mt-5">
    <h3>Редактирование путешествия</h3>
    <form action="${pageContext.request.contextPath}/travel/update" method="post" class="mt-4">
      <input type="hidden" name="travel_id" value="${travel.travel_id}">

      <div class="mb-3">
        <label for="name_travel" class="form-label">Название:</label>
        <input type="text" name="name_travel" id="name_travel" value="${travel.name_travel}"
               class="form-control" required>
      </div>

      <div class="mb-3">
        <label for="description" class="form-label">Описание:</label>
        <textarea name="description" id="description" class="form-control" required>${travel.description}</textarea>
      </div>

      <div class="mb-3">
        <label for="start_date" class="form-label">Дата начала:</label>
        <input type="date" name="start_date" id="start_date" value="${travel.start_date}"
               class="form-control" required>
      </div>

      <div class="mb-3">
        <label for="end_date" class="form-label">Дата окончания:</label>
        <input type="date" name="end_date" id="end_date" value="${travel.end_date}"
               class="form-control" required>
      </div>

      <div class="mb-3">
        <label for="transport" class="form-label">Транспорт:</label>
        <input type="text" name="transport" id="transport" value="${travel.transport}"
               class="form-control" required>
      </div>

      <div class="mb-3">
        <label for="list_of_things" class="form-label">Список вещей:</label>
        <textarea name="list_of_things" id="list_of_things" class="form-control" required>${travel.list_of_things}</textarea>
      </div>

      <div class="mb-3">
        <label for="notes" class="form-label">Заметки:</label>
        <textarea name="notes" id="notes" class="form-control">${travel.notes}</textarea>
      </div>

      <button type="submit" class="btn btn-primary">Обновить</button>
      <a href="${pageContext.request.contextPath}/travel/detail?travel_id=${travel.travel_id}"
         class="btn btn-secondary">Отмена</a>
    </form>
  </div>
</t:mainLayout>
