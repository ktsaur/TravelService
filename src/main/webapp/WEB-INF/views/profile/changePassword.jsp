<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

<div class="container mt-5">
  <h2>Изменить пароль</h2>

  <!-- Сообщения об ошибке или успехе -->
  <c:if test="${not empty error}">
    <div class="alert alert-danger">
        ${error}
    </div>
  </c:if>

  <c:if test="${not empty message}">
    <div class="alert alert-success">
        ${message}
    </div>
  </c:if>

  <div class="card">
    <div class="card-body">
      <form action="<c:url value='/profile/updatePassword'/>" method="post">
        <div class="mb-3">
          <label for="oldPassword" class="form-label">Старый пароль</label>
          <input type="password" class="form-control" id="oldPassword" name="oldPassword" required>
        </div>
        <div class="mb-3">
          <label for="newPassword" class="form-label">Новый пароль</label>
          <input type="password" class="form-control" id="newPassword" name="newPassword" required>
        </div>
        <div class="mb-3">
          <label for="confirmPassword" class="form-label">Подтвердите новый пароль</label>
          <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
        </div>
        <button type="submit" class="btn btn-primary">Сохранить изменения</button>
      </form>
    </div>
  </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
