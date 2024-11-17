<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Главная">
  <body>
  <div class="container mt-5">
    <h3 class="mb-4">Познакомьтесь с интересными статьями на тему путешествий</h3><br>

    <c:if test="${articles.isEmpty()}">
      <p class="mb-5">Статей нет</p>
    </c:if>

    <c:if test="${!articles.isEmpty()}">
      <ul>
        <c:forEach var="article" items="${articles}">
          <li>
            <a href="<c:url value="/article/detail?article_id=${article.getArticle_id()}"/>">
              <h4> ${article.getTitle()} </h4> <br>
            </a>
          </li>
        </c:forEach>
      </ul>
    </c:if>

  </div>
  </body>
</t:mainLayout>