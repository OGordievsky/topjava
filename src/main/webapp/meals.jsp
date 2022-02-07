<%--
  Created by IntelliJ IDEA.
  User: og87
  Date: 05.02.2022
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<table border="2" bordercolor="black" style="border-collapse: collapse; ">
    <caption style="height: 40px; font-weight: bold; font-size: 20px; border: 2px solid black; line-height: 40px; border-bottom: 0">Meals</caption>
    <tr style="background-color: #ddebf0">
        <th>ID</th>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Действие</th>
    </tr>
    <c:forEach var="meal" items="${list}">
        <tr style="color:${meal.excess ? "FF0000" : "008000"};">
            <td>${meal.id}</td>
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <a href="meals?action=edit&id=${meal.id}">edit</a>
                <a href="meals?action=delete&id=${meal.id}">delete</a>
            </td>
        </tr>

    </c:forEach>
</table>

<form action = "editMeal.jsp">
    <input type="submit" value="Добавить новую запись">
</form>

</body>
</html>
