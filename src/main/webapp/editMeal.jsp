<%--
  Created by IntelliJ IDEA.
  User: og87
  Date: 07.02.2022
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Добавить/Редактировать</title>
</head>
<body>
<table border="2" bordercolor="black" style="border-collapse: collapse; ">
    <tr style="background-color: #ddebf0">
        <th>${action == "edit" ? "Edit ID =" : "Add"} ${meal.id}</th>
    </tr>
    <tr>
        <td>
            <form action="meals?id=${meal.id}" method="post" }>
                <input required type="date" name="date" placeholder="date" value=${meal.date}>
                <input required type="time" name="time" placeholder="time" value=${meal.time}>
                <input required type="text" name="description" placeholder="description" value=${meal.description}>
                <input required type="number" name="calories" placeholder="calories" value=${meal.calories}>
                <input type="submit" value="Сохранить">
            </form>
        </td>
    </tr>
</table>
</body>
</html>