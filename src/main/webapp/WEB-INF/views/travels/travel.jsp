<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Путешествие">
  <div class="container mt-5">
    <h3>Детали путешествия</h3>

    <p><strong>Название путешествия:</strong>${travel.getName_travel()}</p>
    <p><strong>Описание:</strong> ${travel.getDescription()}</p>
    <p><strong>Дата начала:</strong> ${travel.getStart_date()}</p>
    <p><strong>Дата окончания:</strong> ${travel.getEnd_date()}</p>
    <p><strong>Транспорт:</strong>${travel.getTransport()}</p>
    <p><strong>Список вещей, которые нужно взять с собой:</strong>${travel.getList_of_things()}</p>
    <p><strong>Заметки:</strong>${travel.getNotes()}</p>
  </div>
  <form action="<c:url value='/travel/detail' />" method="post">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" name="travel_id" value="${travel.getTravel_id()}">
    <button type="submit" class="btn btn-danger">Удалить путешествие</button>
  </form>

</t:mainLayout>
