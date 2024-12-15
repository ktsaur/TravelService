<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card w-100">
  <div class="list-group">
    <form action="<c:url value='/profile/updatePassword'/>" method="post" class="list-group-item list-group-item-action">
      <input type="hidden" name="action" value="updatePassword">
      <button type="submit" class="btn">Изменить пароль</button>
    </form>

    <form action="<c:url value='/profile'/>" method="post" class="list-group-item list-group-item-action">
      <input type="hidden" name="action" value="delete">
      <button type="button" class="list-group-item list-group-item-action btn" data-bs-toggle="modal" data-bs-target="#deleteModal">
        Удалить аккаунт
      </button>
    </form>
  </div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="deleteModalLabel">Удаление аккаунта</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        Вы уверены, что хотите удалить аккаунт? Это действие нельзя будет отменить.
      </div>
      <div class="modal-footer">
        <form action="${pageContext.request.contextPath}/profile" method="post">
          <input type="hidden" name="action" value="delete">
          <button type="submit" class="btn btn-danger">Удалить</button>
        </form>
      </div>
    </div>
  </div>
</div>

