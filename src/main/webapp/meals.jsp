<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<table border="2" bordercolor="black" style="border-collapse: collapse; width: 30% ">
    <caption style="height: 40px; font-weight: bold; font-size: 20px; border: 2px solid black; line-height: 40px; border-bottom: 0">Meals</caption>
    <tr style="background-color: #ddebf0">
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Действие</th>
    </tr>
    <c:forEach var="meal" items="${list}">
        <tr style="color:${meal.excess ? "FF0000" : "008000"};">
            <td>${meal.date} ${meal.time}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <a href="meals?action=edit&id=${meal.id}">Update</a>
                <a href="meals?action=delete&id=${meal.id}">Delete</a>
            </td>
        </tr>

    </c:forEach>
</table>

<form action = "meals" method="get">
    <input type="hidden" name="action" value="add">
    <input type="submit" value="Добавить новую запись">
</form>

</body>
</html>
