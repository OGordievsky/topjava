<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Добавить/Редактировать</title>
</head>
<body>
<table border="2" bordercolor="black" style="border-collapse: collapse; ">
    <tr style="background-color: #ddebf0">
        <th>${meal != null ? "Редактировать" : "Добавить новую запись"}</th>
    </tr>
    <tr>
        <td>
            <form action=meals method="post" }>
                <input type="hidden" name="id" value="${meal.id}">
                <input required type="date" name="date" placeholder="date" value=${meal.date}>
                <input required type="time" name="time" placeholder="time" value=${meal.time}>
                <input required type="text" name="description" placeholder="description" value="${meal.description}" size="40">
                <input required type="number" name="calories" placeholder="calories" value=${meal.calories}>
                <input type="submit" value="Сохранить">
                <button type="button" onclick="window.location.href = 'meals';">Отменить</button>
            </form>
        </td>
    </tr>
</table>
</body>
</html>