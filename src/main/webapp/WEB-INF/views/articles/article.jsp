<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header/_header.jsp" %>

<div class="container mt-5">
  <style>
    .favourite-button {
      display: inline-flex;
      align-items: center;
      justify-content: center;
      border: none;
      background: none;
      cursor: pointer;
    }
    .favourite-button i {
      transition: transform 0.2s ease;
    }
    .favourite-button:hover i {
      transform: scale(1.1);
    }
  </style>

  <c:if test="${not empty message}">
    <div class="alert alert-info">
      <p>${message}</p>
    </div>
  </c:if>

  <form action="<c:url value='/article/detail' />" method="post" style="display: inline;">
    <input type="hidden" name="article_id" value="${article.article_id}" />
    <button type="submit" class="btn favourite-button">
      <i class="${isFavourite ? 'bi bi-heart-fill text-danger fs-3' : 'bi bi-heart text-primary fs-3'}"></i>
    </button>
  </form>

<%--  <script>--%>
<%--    document.querySelector('.favourite-button').addEventListener('click', function (event) {--%>
<%--      event.preventDefault();--%>
<%--      const button = event.target.closest('button');--%>
<%--      const articleId = button.querySelector('input[name="article_id"]').value;--%>

<%--      fetch('/article/detail', {--%>
<%--        method: 'POST',--%>
<%--        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },--%>
<%--        body: `article_id=${articleId}`--%>
<%--      })--%>
<%--              .then(response => {--%>
<%--                if (!response.ok) {--%>
<%--                  throw new Error('Failed to update favourite status');--%>
<%--                }--%>
<%--                return response.text();--%>
<%--              })--%>
<%--              .then(() => {--%>
<%--                // Меняем иконку после успешного выполнения--%>
<%--                const icon = button.querySelector('i');--%>
<%--                icon.classList.toggle('bi-heart');--%>
<%--                icon.classList.toggle('bi-heart-fill');--%>
<%--                icon.classList.toggle('text-danger');--%>
<%--                icon.classList.toggle('text-primary');--%>
<%--              })--%>
<%--              .catch(error => {--%>
<%--                console.error('Error:', error);--%>
<%--              });--%>
<%--    });--%>

<%--  </script>--%>

  <h3>${article.title}</h3>
  <p>${article.content}</p>
  <p><strong>Дата опубликования:</strong> ${article.created_date}</p>
</div>

<%@ include file="/WEB-INF/views/footer/_footer.jsp" %>

