<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

<%--<body>--%>
<%--<div class="container mt-5">--%>

<%--  <c:if test="${not empty message}">--%>
<%--    <div class="alert alert-info">--%>
<%--      <p>${message}</p>--%>
<%--    </div>--%>
<%--  </c:if>--%>


<%--  <c:if test="${groupedArticles.isEmpty()}">--%>
<%--    <p class="text-center">Статей нет</p>--%>
<%--  </c:if>--%>

<%--  <c:if test="${!groupedArticles.isEmpty()}">--%>
<%--    <div class="row">--%>
<%--      <div class="col-4">--%>
<%--        <nav id="navbar-example3" class="h-100 flex-column align-items-stretch pe-4 border-end">--%>
<%--          <nav class="nav nav-pills flex-column">--%>
<%--            <c:forEach var="category" items="${groupedArticles.keySet()}">--%>
<%--              <a class="nav-link" href="#category-${category}">${category}</a>--%>
<%--              <nav class="nav nav-pills flex-column">--%>
<%--                <c:forEach var="article" items="${groupedArticles[category]}">--%>
<%--                  <a class="nav-link ms-3 my-1" href="#article-${article.getArticle_id()}">${article.getTitle()}</a>--%>
<%--                </c:forEach>--%>
<%--              </nav>--%>
<%--            </c:forEach>--%>
<%--          </nav>--%>
<%--        </nav>--%>
<%--      </div>--%>

<%--      <div class="col-8">--%>
<%--        <div data-bs-spy="scroll" data-bs-target="#navbar-example3" data-bs-smooth-scroll="true" class="scrollspy-example-2" tabindex="0">--%>
<%--          <c:forEach var="entry" items="${groupedArticles.entrySet()}">--%>
<%--            <div id="category-${entry.key}" class="mb-5">--%>
<%--              <h4>${entry.key}</h4>--%>
<%--            </div>--%>
<%--            <c:forEach var="article" items="${entry.value}">--%>
<%--              <div id="article-${article.getArticle_id()}" class="mb-4">--%>
<%--                <h5>${article.getTitle()}</h5>--%>
<%--                <p>${article.getContent() != null && fn:length(article.getContent()) > 200--%>
<%--                        ? article.getContent().substring(0, 200)--%>
<%--                        : article.getContent() != null ? article.getContent() : ''}...</p>--%>
<%--                <a href="<c:url value='/article/detail?article_id=${article.getArticle_id()}'/>" class="btn btn-primary">Читать полностью</a>--%>
<%--                <form action="<c:url value='/main'/>" method="post" style="display: inline;">--%>
<%--                  <input type="hidden" name="action" value="toggleFavourite"/>--%>
<%--                  <input type="hidden" name="article_id" value="${article.getArticle_id()}"/>--%>
<%--                  <c:set var="isFavourite" value="${favouriteStatus[article.getArticle_id()]}"/>--%>
<%--                  <button type="submit" class="btn btn-link p-0 ms-2">--%>
<%--                    <i class="${isFavourite ? 'bi bi-heart-fill text-danger fs-3' : 'bi bi-heart text-primary fs-3'}"></i>--%>
<%--                  </button>--%>
<%--                </form>--%>
<%--              </div>--%>
<%--            </c:forEach>--%>
<%--          </c:forEach>--%>
<%--        </div>--%>
<%--      </div>--%>
<%--    </div>--%>
<%--  </c:if>--%>
<%--</div>--%>
<%--  <div class="fixed-bottom mb-3 me-3 text-end">--%>
<%--    <a href="<c:url value='/article/create'/>" class="btn btn-secondary btn-lg d-inline-flex align-items-center justify-content-center" style="border-radius: 50px;">--%>
<%--      <i class="bi bi-plus-circle fs-4 me-2"></i>--%>
<%--      Создать статью--%>
<%--    </a>--%>
<%--  </div>--%>
<%--</body>--%>

<body>
<div class="container mt-5">
  <c:if test="${not empty message}">
    <div class="alert alert-info">
      <p>${message}</p>
    </div>
  </c:if>

  <c:if test="${groupedArticles.isEmpty()}">
    <p class="text-center">Статей нет</p>
  </c:if>

  <c:if test="${!groupedArticles.isEmpty()}">
    <div class="card border-0 shadow">
      <div class="card-body">
        <div class="row">
          <!-- Scrollspy Navigation -->
          <div class="col-4">
            <nav id="navbar-example3" class="h-100 flex-column align-items-stretch pe-4 border-end">
              <nav class="nav nav-pills flex-column">
                <c:forEach var="category" items="${groupedArticles.keySet()}">
                  <a class="nav-link text-secondary" href="#category-${category}">${category}</a>
                  <nav class="nav nav-pills flex-column">
                    <c:forEach var="article" items="${groupedArticles[category]}">
                      <a class="nav-link ms-3 my-1 text-secondary" href="#article-${article.getArticle_id()}">${article.getTitle()}</a>
                    </c:forEach>
                  </nav>
                </c:forEach>
              </nav>
            </nav>
          </div>

          <!-- Articles Section -->
          <div class="col-8">
            <div data-bs-spy="scroll" data-bs-target="#navbar-example3" data-bs-smooth-scroll="true" class="scrollspy-example-2" tabindex="0">
              <c:forEach var="entry" items="${groupedArticles.entrySet()}">
                <div id="category-${entry.key}" class="mb-5">
                  <h4>${entry.key}</h4>
                </div>
                <c:forEach var="article" items="${entry.value}">
                  <div id="article-${article.getArticle_id()}" class="mb-4">
                    <h5>${article.getTitle()}</h5>
                    <p>${article.getContent() != null && fn:length(article.getContent()) > 200
                            ? article.getContent().substring(0, 200)
                            : article.getContent() != null ? article.getContent() : ''}...</p>
                    <a href="<c:url value='/article/detail?article_id=${article.getArticle_id()}'/>" class="btn btn-primary">Читать полностью</a>
                    <form action="<c:url value='/main'/>" method="post" style="display: inline;">
                      <input type="hidden" name="action" value="toggleFavourite"/>
                      <input type="hidden" name="article_id" value="${article.getArticle_id()}"/>
                      <c:set var="isFavourite" value="${favouriteStatus[article.getArticle_id()]}"/>
                      <button type="submit" class="btn btn-link p-0 ms-2">
                        <i class="${isFavourite ? 'bi bi-heart-fill text-danger fs-3' : 'bi bi-heart text-primary fs-3'}"></i>
                      </button>
                    </form>
                  </div>
                </c:forEach>
              </c:forEach>
            </div>
          </div>
        </div>
      </div>
    </div>
  </c:if>
</div>

<div class="fixed-bottom mb-3 me-3 text-end">
  <a href="<c:url value='/article/create'/>" class="btn btn-secondary btn-lg d-inline-flex align-items-center justify-content-center" style="border-radius: 50px;">
    <i class="bi bi-plus-circle fs-4 me-2"></i>
    Создать статью
  </a>
</div>

<!-- Custom CSS -->
<style>

  /* Card Style */

  .nav-link.active {
    background-color: transparent !important; /* Фон остается прозрачным */
    color: #212529 !important; /* Цвет текста черный */
    font-weight: bold; /* Жирный текст для выделения */
    border-left: 3px solid #6c757d; !* Добавляем тонкую серую линию слева *!*/
  }

  /* При наведении на элемент */
  /*.nav-link:hover {*/
  /*  border: 1px solid #6c757d; !* Тонкая серая рамка *!*/
  /*  border-radius: 4px; !* Закругленные углы *!*/
  /*  background-color: transparent;*/
  /*}*/

  .card {
    background-color: #f8f9fa; /* Светло-серый фон */
  }

  /* Общий стиль ссылок Scrollspy */
  .nav-link {
    color: #6c757d; /* Серый текст */
    transition: border-color 0.3s, background-color 0.3s;
  }

  /* Стиль для активного элемента Scrollspy */
  .nav-link.active {
    color: #212529; /* Темный текст */
    font-weight: bold;
    background-color: #e9ecef; /* Светло-серый фон */
  }

  /* Стиль для наведения на ссылки Scrollspy */
  /*.nav-link:hover {*/
  /*  border: 1px solid #6c757d; !* Серая рамка *!*/
  /*  border-radius: 4px; !* Закругление рамки *!*/
  /*  background-color: transparent;*/
  /*}*/

  /* Стили для карточного контейнера */
  .card-body {
    padding: 2rem;
  }

  /* Для управления расстоянием внутри scrollspy */
  .scrollspy-example-2 {
    max-height: 500px;
    overflow-y: auto;
  }
</style>
</body>

<%@include file="/WEB-INF/views/footer/_footer.jsp" %>

