<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - TravelMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-image: url('https://res.cloudinary.com/dkiovijcy/image/upload/v1733936454/IMG_4766_vsg2b5.jpg');
            background-size: cover;
            background-position: center;
            height: 100vh;
        }
        .login-container {
            max-width: 400px;
            margin: auto;
            padding: 30px;
            background-color: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .login-title {
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="container d-flex justify-content-center align-items-center h-100">
    <div class="login-container">
        <h2 class="login-title">Добро пожаловать в TravelMate!</h2>
        <form action="<c:url value='/signin' />" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Имя пользователя</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Введите имя пользователя" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Пароль</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль" required>
            </div>
            <div class="d-grid">
                <button type="submit" class="btn btn-primary">Войти</button>
            </div>
            <div class="mt-3 text-center">
                <p>У вас еще нет аккаунта? <a href="<c:url value='/registration' />">Зарегистрироваться</a></p>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>



