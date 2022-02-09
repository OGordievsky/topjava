package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
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

    private final MealService service = new MealServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equalsIgnoreCase("edit")) {
            Meal meal = service.getById(Integer.parseInt(req.getParameter("id")));
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("editMeal.jsp").forward(req, resp);
        } else if (action != null && action.equalsIgnoreCase("delete")) {
            Meal meal = service.getById(Integer.parseInt(req.getParameter("id")));
            service.delete(meal);
            resp.sendRedirect("meals");
        } else if (action != null && action.equalsIgnoreCase("add")) {
            req.getRequestDispatcher("editMeal.jsp").forward(req, resp);
        } else {
            List<MealTo> list = MealsUtil.filteredByStreams(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("list", list);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(req.getParameter("date")), LocalTime.parse(req.getParameter("time")));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String index = req.getParameter("id");

        if (index != null && !index.isEmpty()) {
            int id = Integer.parseInt(index);
            service.update(id, dateTime, description, calories);
        } else {
            service.add(dateTime, description, calories);
        }
        resp.sendRedirect("meals");
    }
}
