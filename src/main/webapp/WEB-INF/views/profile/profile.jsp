<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

    <style>
        .card img {
            max-width: 100%; /* Ограничивает размер изображения */
            height: auto;
        }
    </style>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-3">
            <ul class="nav flex-column nav-pills">
                <li class="nav-item">
                    <a class="nav-link ${tab == 'personal' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/profile?tab=personal">Персональная информация</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${tab == 'account' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/profile?tab=account">Управление аккаунтом</a>
                </li>
            </ul>
        </div>
        <div class="col-md-9">
            <c:choose>
                <c:when test="${tab == 'personal'}">
                    <jsp:include page="personalInfo.jsp" />
                </c:when>
                <c:when test="${tab == 'account'}">
                    <jsp:include page="profileAccount.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="profileAccount.jsp" />
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/views/footer/_footer.jsp" %>

