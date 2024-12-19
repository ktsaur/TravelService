<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>

    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/js/bootstrap.bundle.min.js"/>"></script>

    <style>
        body {
            background: url('https://res.cloudinary.com/dkiovijcy/image/upload/v1733936454/IMG_4766_vsg2b5.jpg') no-repeat center center fixed;
            background-size: cover;
        }
        .registration-container {
            max-width: 400px;
            margin: 5% auto;
            padding: 30px;
            background: rgba(255, 255, 255, 0.8);
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
        }
        .form-title {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
    </style>

    <link rel="stylesheet" href="<c:url value="/style/bootstrap.css"/>">
    <link rel="stylesheet" href="<c:url value="/style/_nav.scss"/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
</head>
<body>

<div class="container">
    <div class="registration-container">
        <h2 class="form-title">Регистрация</h2>
        <form action="<c:url value='/registration' />" method="POST">
            <div class="mb-3">
                <label for="username" class="form-label">Имя пользователя</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Введите ваше имя" required>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Электронная почта</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Введите вашу почту" required>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Пароль</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль" required>
            </div>

            <div class="d-grid">
                <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            </div>

            <p class="text-center mt-3">Уже есть аккаунт? <a href="<c:url value='/signin' />">Войти</a></p>
        </form>
    </div>
</div>

