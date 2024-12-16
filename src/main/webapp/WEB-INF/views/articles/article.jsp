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
      position: absolute;
      top: 10px;
      right: 10px;
    }
    .favourite-button i {
      transition: transform 0.2s ease;
    }
    .favourite-button:hover i {
      transform: scale(1.1);
    }
    .article-card {
      max-width: 1000px; /* Уменьшаем ширину карточки */
      margin: 0 auto; /* Центрируем карточку */
    }
    .card-title {
      margin-bottom: 30px; /* Увеличиваем расстояние между заголовком и содержанием */
    }
  </style>

  <div class="card shadow-sm article-card">
    <div class="card-body">
      <form action="<c:url value='/article/detail' />" method="post" style="display: inline;">
        <input type="hidden" name="article_id" value="${article.article_id}" />
        <button type="submit" class="btn favourite-button">
          <i class="${isFavourite ? 'bi bi-heart-fill text-danger fs-3' : 'bi bi-heart text-primary fs-3'}"></i>
        </button>
      </form>

      <h3 class="card-title">${article.title}</h3>
      <p class="card-text">${article.content}</p>
      <p><strong>Дата опубликования:</strong> ${article.created_date}</p>
    </div>
  </div>

</div>

<%@ include file="/WEB-INF/views/footer/_footer.jsp" %>

