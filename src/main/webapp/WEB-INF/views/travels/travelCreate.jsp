<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Создание путешествие">
<%--    <div class="container mt-5">--%>
<%--        <h3>Создание путешествия</h3>--%>
<%--    </div>--%>
    <div class="container mt-5">
        <h1>Создание Путешествия</h1>
        <form action="<c:url value='/travel/create' />" method="post">
            <div class="form-group">
                <label for="name_travel">Название путешествия</label>
                <input type="text" class="form-control" id="name_travel" name="name_travel" required>
            </div>

            <div class="form-group">
                <label for="description">Описание</label>
                <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
            </div>

            <div class="form-group">
                <label for="start_date">Дата начала</label>
                <input type="date" class="form-control" id="start_date" name="start_date" required>
            </div>

            <div class="form-group">
                <label for="end_date">Дата окончания</label>
                <input type="date" class="form-control" id="end_date" name="end_date" required>
            </div>

            <div class="form-group">
                <label for="transport">Транспорт</label>
                <input type="text" class="form-control" id="transport" name="transport" required>
            </div>

            <div class="form-group">
                <label for="list_of_things">Список вещей</label>
                <textarea class="form-control" id="list_of_things" name="list_of_things" rows="3"></textarea>
            </div>

            <div class="form-group">
                <label for="notes">Дополнительная информация</label>
                <textarea class="form-control" id="notes" name="notes" rows="3"></textarea>
            </div>

            <button type="submit" class="btn btn-primary">Создать Путешествие</button>
        </form>
    </div>
</t:mainLayout>
