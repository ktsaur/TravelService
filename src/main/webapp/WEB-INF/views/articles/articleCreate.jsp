<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header/_header.jsp" %>

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="card shadow">
        <div class="card-header text-center bg-primary text-white">
          <h4>Создание новой статьи</h4>
        </div>
        <div class="card-body">
          <c:if test="${not empty message}">
            <div class="alert alert-warning">${message}</div>
          </c:if>

          <form action="<c:url value='/article/create'/>" method="post">
            <div class="mb-3">
              <label for="title" class="form-label">Заголовок</label>
              <input type="text" class="form-control" id="title" name="title" required placeholder="Введите название">
            </div>

            <div class="mb-3">
              <label for="content" class="form-label">Содержание</label>
              <textarea class="form-control" id="content" name="content" rows="5" required placeholder="Добавьте описание"></textarea>
            </div>

              <div class="mb-3">
              <label for="category" class="form-label">Категория</label>
              <input type="text" class="form-control" id="category" name="category" list="categories" required placeholder="Выберите или добавьте категорию">
              <datalist id="categories">
                <c:forEach var="existingCategory" items="${categories}">
                  <option value="${existingCategory}" />
                </c:forEach>
              </datalist>
            </div>

            <button type="submit" class="btn btn-success w-100">Опубликовать статью</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/views/footer/_footer.jsp" %>

