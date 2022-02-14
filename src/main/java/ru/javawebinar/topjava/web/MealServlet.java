package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;


    @Override
    public void init() {
        try (
                ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealRestController = appCtx.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("user") != null){
            SecurityUtil.setAuthUser(Integer.parseInt(request.getParameter("user")));
            response.sendRedirect("meals");
        } else {
            Integer id = request.getParameter("id").isEmpty() ? null : Integer.valueOf(request.getParameter("id"));
            Integer userId = SecurityUtil.authUserId();

            Meal meal = new Meal(id,
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if (meal.isNew()) {
                mealRestController.create(userId, meal);
            } else {
                mealRestController.update(userId, meal);
            }
            response.sendRedirect("meals");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Integer userId = SecurityUtil.authUserId();
        Integer userCaloriesPerDay = SecurityUtil.authUserCaloriesPerDay();

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(userId, id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", userCaloriesPerDay) :
                        mealRestController.get(userId, getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("getAll by filter {}", userId);
                List<MealTo> mealList = getFilteredList(request, mealRestController.getAll(userId), userCaloriesPerDay);
                request.setAttribute("meals", mealList);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll by {}", userId);
                request.setAttribute("meals",
                        MealsUtil.getTos(mealRestController.getAll(userId), userCaloriesPerDay));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private List<MealTo> getFilteredList(HttpServletRequest request, Collection<Meal> meals, int caloriesPerDay) {
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");
        String timeFrom = request.getParameter("timeFrom");
        String timeTo = request.getParameter("timeTo");

        Collection<MealTo> tempList = MealsUtil.getTos(meals, caloriesPerDay);
        if (dateFrom != null && dateTo != null && !dateFrom.isEmpty() && !dateTo.isEmpty()) {
            LocalDate localDateFrom = LocalDate.parse(dateFrom);
            LocalDate localDateTo = LocalDate.parse(dateTo);
            tempList = (MealsUtil.getFilteredDate(tempList, localDateFrom, localDateTo));
        }
        if (timeFrom != null && timeTo != null && !timeFrom.isEmpty() && !timeTo.isEmpty()) {
            LocalTime localTimeFrom = LocalTime.parse(timeFrom);
            LocalTime localTimeTo = LocalTime.parse(timeTo);
            tempList = (MealsUtil.getFilteredTime(tempList, localTimeFrom, localTimeTo));
        }
        return new ArrayList<>(tempList);
    }
}
