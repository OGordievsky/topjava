<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h1><a href="index.html">Home</a></h1>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <form action=meals method="get">
        <input type="hidden" name="action" value="filter">
        <table border="2" style="border-collapse: collapse; width: 30% ">
            <caption
                    style="height: 40px; font-weight: bold; font-size: 20px; border: 2px solid black; line-height: 40px; border-bottom: 0">
                Filter
            </caption>
            <tr>
                <th>От даты (включая)</th>
                <th>До даты (включая)</th>
                <th>От времени (включая)</th>
                <th>До времени (исключая)</th>
            </tr>
            <tr>
                <td><label>
                    <input type="date" name="dateFrom" placeholder="date_from" value="${param.dateFrom}">
                </label></td>
                <td><label>
                    <input type="date" name="dateTo"  placeholder="date_to" value="${param.dateTo}">
                </label></td>
                <td><label>
                    <input type="time" name="timeFrom" placeholder="timeFrom" value="${param.timeFrom}">
                </label></td>
                <td><label>
                    <input type="time" name="timeTo"  placeholder="timeTo" value="${param.timeTo}">
                </label></td>
            </tr>
            <tr>
                <td colspan="4"><input type="submit" value="Отфильтровать">
                    <button type="button" onclick="window.location.href = 'meals';">Отменить</button>
                </td>
            </tr>
        </table>
    </form>
    <br><br>
    <table border="2" bordercolor="black" style="border-collapse: collapse; width: 30% ">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<script type="module">
    const inputTimeFrom = document.querySelector('input[name="timeFrom"]');
    const inputTimeTo = document.querySelector('input[name="timeTo"]');
    const inputDateFrom = document.querySelector('input[name="dateFrom"]');
    const inputDateTo = document.querySelector('input[name="dateTo"]');

    inputTimeTo.addEventListener('focus', onTimeToFocus);
    inputTimeFrom.addEventListener('focus', onTimeFromFocus);
    inputDateTo.addEventListener('focus', onDateToFocus);
    inputDateFrom.addEventListener('focus', onDateFromFocus);

    function onTimeToFocus() {
        inputTimeTo.setAttribute('min', inputTimeFrom.value);
    }
    function onTimeFromFocus() {
        inputTimeFrom.setAttribute('max', inputTimeTo.value);
    }
    function onDateToFocus() {
        inputDateTo.setAttribute('min', inputDateFrom.value);
    }
    function onDateFromFocus() {
        inputDateFrom.setAttribute('max', inputDateTo.value);
    }
</script>
</body>
</html>