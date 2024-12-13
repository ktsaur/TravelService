<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/header/_header.jsp" %>

<div class="container mt-5">
    <div class="card shadow border-0">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">Создание нового путешествия</h4>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/travel/create" method="post" class="needs-validation" novalidate>

                <div class="mb-3">
                    <label for="name_travel" class="form-label">Название путешествия:</label>
                    <input type="text" name="name_travel" id="name_travel" class="form-control" placeholder="Введите название" required>
                    <div class="invalid-feedback">Пожалуйста, укажите название.</div>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Описание:</label>
                    <textarea name="description" id="description" class="form-control" rows="3" placeholder="Добавьте описание" required></textarea>
                    <div class="invalid-feedback">Пожалуйста, добавьте описание.</div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="start_date" class="form-label">Дата начала:</label>
                        <input type="date" name="start_date" id="start_date" class="form-control" required>
                        <div class="invalid-feedback">Пожалуйста, выберите дату начала.</div>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label for="end_date" class="form-label">Дата окончания:</label>
                        <input type="date" name="end_date" id="end_date" class="form-control" required>
                        <div class="invalid-feedback">Пожалуйста, выберите дату окончания.</div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="transport" class="form-label">Транспорт:</label>
                    <input type="text" name="transport" id="transport" class="form-control" placeholder="Введите транспорт" required>
                    <div class="invalid-feedback">Пожалуйста, укажите транспорт.</div>
                </div>

                <div class="mb-3">
                    <label for="list_of_things" class="form-label">Список вещей:</label>
                    <textarea name="list_of_things" id="list_of_things" class="form-control" rows="3" placeholder="Укажите список вещей" required></textarea>
                    <div class="invalid-feedback">Пожалуйста, добавьте список вещей.</div>
                </div>

                <div class="mb-3">
                    <label for="notes" class="form-label">Заметки:</label>
                    <textarea name="notes" id="notes" class="form-control" rows="3" placeholder="Добавьте заметки (необязательно)"></textarea>
                </div>

                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-success">Создать</button>
                    <a href="${pageContext.request.contextPath}/travel/list" class="btn btn-secondary">Отмена</a>
                </div>

            </form>
        </div>
    </div>
</div>

<script>
    // Bootstrap validation script
    (function () {
        'use strict'
        const forms = document.querySelectorAll('.needs-validation')

        Array.from(forms).forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
    })()
</script>

<%@include file="/WEB-INF/views/footer/_footer.jsp" %>

