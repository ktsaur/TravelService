<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%--<%@include file="/WEB-INF/views/header/_header.jsp" %>--%>
<%--  <body>--%>
<%--  <div class="container mt-5">--%>
<%--    <h3 class="mb-4">Избранный путешествия</h3><br>--%>

<%--    <c:if test="${articles.isEmpty()}">--%>
<%--      <p class="mb-5">Статей нет</p>--%>
<%--    </c:if>--%>

<%--    <c:if test="${!articles.isEmpty()}">--%>
<%--      <ul>--%>
<%--        <c:forEach var="article" items="${articles}">--%>
<%--          <li>--%>
<%--            <a href="<c:url value="/article/detail?article_id=${article.getArticle_id()}"/>">--%>
<%--              <h4> ${article.getTitle()} </h4> <br>--%>
<%--            </a>--%>
<%--          </li>--%>
<%--        </c:forEach>--%>
<%--      </ul>--%>
<%--    </c:if>--%>

<%--  </div>--%>
<%--  </body>--%>
<%--<%@include file="/WEB-INF/views/footer/_footer.jsp" %>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

<body>
<div class="container-fluid mt-5">
  <h3 class="mb-4 text-center">Избранные статьи</h3>

  <c:if test="${articles.isEmpty()}">
    <div class="alert alert-warning text-center" role="alert">
      Статей нет в избранном.
    </div>
  </c:if>

  <c:if test="${!articles.isEmpty()}">
    <div class="row g-4">
      <c:forEach var="article" items="${articles}">
        <div class="col-12 col-md-6 col-lg-4">
          <div class="card shadow-sm border-light h-100">
            <div class="card-body">
              <h5 class="card-title">${article.getTitle()}</h5>
              <p class="card-text">${article.getCategory().substring(0, 100)}...</p>
              <a href="<c:url value='/article/detail?article_id=${article.getArticle_id()}'/>" class="btn btn-primary stretched-link">
                Читать полностью
              </a>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>
  </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>

<%@include file="/WEB-INF/views/footer/_footer.jsp" %>

