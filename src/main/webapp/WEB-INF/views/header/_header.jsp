<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>JSP page</title>
  <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
  <script src="<c:url value="/js/bootstrap.bundle.min.js"/>"></script>


  <link rel="stylesheet" href="<c:url value="/style/bootstrap.css"/>">
  <link rel="stylesheet" href="<c:url value="/style/_nav.scss"/>">
  <link rel="stylesheet" href="<c:url value="/style/main.css"/>">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <nav class="navbar bg-body-tertiary">
      <div class="container">
        <a class="navbar-brand" href="<c:url value='/main' />">
          <img src="<c:url value="https://res.cloudinary.com/dkiovijcy/image/upload/v1733911964/logo_hw0u7x.jpg"/> " alt="Bootstrap" width="50" height="50">
        </a>
      </div>
    </nav>
    <a class="navbar-brand" href="<c:url value='/main' />">TravelMate</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
      </ul>
      <c:if test="${user != null}">
        <ul class="nav nav-tabs">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">
                <c:choose>
                  <c:when test="${not empty user.url}">
                    <img src="${user.url}" alt="Bootstrap" width="40" height="40" class="rounded-circle" />
                  </c:when>
                  <c:otherwise>
                    <i class="bi bi-person-circle fs-4"></i>
                  </c:otherwise>
                </c:choose>
            </a>
            <ul class="dropdown-menu dropdown-menu-end" >
              <li><a class="dropdown-item" href="<c:url value='/profile'/>">Профиль</a></li>
              <li><a class="dropdown-item" href="<c:url value='/travel/list'/>">Мои путешествия</a></li>
              <li><a class="dropdown-item" href="<c:url value='/favourites'/>">Избранные</a></li>
              <li><hr class="dropdown-divider"></li>
<%--              <li><a class="dropdown-item" href="<c:url value='/signout'/>">Выйти</a></li>--%>
              <li><a class="dropdown-item" href="<c:url value='/signout'/>" data-bs-toggle="modal" data-bs-target="#signOutModal">Выйти</a></li>
            </ul>
          </li>
        </ul>
      </c:if>

      <c:if test="${user == null}">
        <nav class="navbar bg-body-tertiary">
          <form class="container-fluid justify-content-start">
                <a class="btn btn-outline-success me-2" href="<c:url value='/signin'/>">Вход</a>
                <a class="btn btn-outline-success me-2" href="<c:url value='/registration'/>">Регистрация</a>
          </form>
        </nav>
      </c:if>
    </div>
  </div>
</nav>
<div class="hero-container">
  <img src="<c:url value='https://res.cloudinary.com/dkiovijcy/image/upload/v1733936812/IMG_4770_b5kw5g.jpg'/>"
       alt="Travel Background"
       style="width: 100%; height: auto;">
  <div class="hero-text">
    <h1>Добро пожаловать в TravelMate!</h1>
    <p>Планируйте путешествия своей мечты с легкостью.</p>
  </div>
</div>
<div class="modal fade" id="signOutModal" tabindex="-1" aria-labelledby="signOutModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="signOutModalLabel">Выход из аккаунта</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>Вы уверены, что хотите выйти из аккаунта?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
        <a href="<c:url value='/signout'/>" class="btn btn-danger">Выйти</a>
      </div>
    </div>
  </div>
</div>


<div class="contents">

