package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.LocalMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private final MealService service = new LocalMemoryMealRepository();
    private static final int CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            List<MealTo> list = MealsUtil.filteredByStreams(service.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            req.setAttribute("list", list);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        } else if (action.equalsIgnoreCase("edit")) {
            Meal meal = service.getById(Integer.parseInt(req.getParameter("id")));
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("editMeal.jsp").forward(req, resp);
        } else if (action.equalsIgnoreCase("delete")) {
            service.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("add")) {
            req.getRequestDispatcher("editMeal.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String index = req.getParameter("id");
        Meal meal = new Meal(LocalDateTime.of(LocalDate.parse(req.getParameter("date")), LocalTime.parse(req.getParameter("time"))),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));

        if (index != null && !index.isEmpty()) {
            meal.setId(Integer.parseInt(index));
            service.update(meal);
        } else {
            service.add(meal);
        }
        resp.sendRedirect("meals");
    }
}
