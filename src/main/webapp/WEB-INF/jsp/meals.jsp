<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script src="resources/js/topjava.common.js" defer></script>
<script src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron pt-4">
    <div class="container">
        <h3 class="text-center"><spring:message code="meal.title"/></h3>
        <div class="card border-dark">
            <div class="card-body pb-0">
                <form id="filter" method="get" action="meals/filter">
                    <div class="row">
                        <div class="col-2">
                            <label for="startDate"><spring:message code="meal.startDate"/></label>
                            <input type="date" id="startDate" class="form-control" name="startDate"
                                   value="${param.startDate}" autocomplete="off">
                        </div>
                        <div class="col-2">
                            <label for="endDate"><spring:message code="meal.endDate"/></label>
                            <input type="date" id="endDate" class="form-control" name="endDate" value="${param.endDate}"
                                   autocomplete="off">
                        </div>
                        <div class="offset-2 col-3">
                            <label for="startTime"><spring:message code="meal.startTime"/></label>
                            <input type="time" id="startTime" class="form-control" name="startTime"
                                   value="${param.startTime}" autocomplete="off">
                        </div>
                        <div class="col-3">
                            <label for="endTime"><spring:message code="meal.endTime"/></label>
                            <input type="time" id="endTime" class="form-control" name="endTime" value="${param.endTime}"
                                   autocomplete="off">
                        </div>
                    </div>
                </form>
            </div>
            <div class="card-footer text-right">
                <button class="btn btn-danger" type="reset" form="filter"><span class="fa fa-remove"></span> <spring:message
                        code="common.cancel"/></button>
                <button class="btn btn-primary" type="submit" form="filter"><span class="fa fa-filter"></span><spring:message
                        code="meal.filter"/></button>
            </div>
        </div>


        <hr>

        <table class="table table-striped" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${requestScope.meals}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
                <tr data-meal-excess="${meal.excess}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
                    <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>
                </tr>
            </c:forEach>
        </table>

    </div>
</div>


<form method="get" action="meals/filter">

    <button type="submit"><spring:message code="meal.filter"/></button>
</form>
<hr>
<a href="meals/create"><spring:message code="meal.add"/></a>


<jsp:include page="fragments/footer.jsp"/>
</body>
</html>