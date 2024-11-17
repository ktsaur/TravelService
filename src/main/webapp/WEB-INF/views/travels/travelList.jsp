<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Список путешествий">
  <div class="container mt-5">
    <h3 class="mb-4">Список путешествий</h3><br>

    <c:if test="${travels.isEmpty()}">
      <p class="mb-5">Ваш список пуст</p>
    </c:if>

    <c:if test="${!travels.isEmpty()}">
      <ul>
        <c:forEach var="travel" items="${travels}">
          <li>
            <a href="<c:url value="/travel/detail?travel_id=${travel.getTravel_id()}"/>">
              <h4> ${travel.getName_travel()} (${travel.getStart_date()} - ${travel.getEnd_date()}) </h4> <br>
            </a>
          </li>
        </c:forEach>
      </ul>
    </c:if>

    <a href="<c:url value="/travel/create"/>" class="btn btn-primary mt-3">Создать новое путешествие</a>
  </div>
</t:mainLayout>