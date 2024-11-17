<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar top">
  <div class="container-fluid">
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <a class="navbar-brand" href="<c:url value='/main' />">TravelMate</a>

    <ul class="nav navbar-nav">
      <c:if test="${user != null}">
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/profile' />">Profile</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/signout' />">Sign out</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/travel/list' />">My travels</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/favourites' />">Favourites</a>
        </li>

      </c:if>
      <c:if test="${user == null}">
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/signin' />">Sign in</a>
        </li>
      </c:if>
    </ul>
  </div>
  </div>
</nav>