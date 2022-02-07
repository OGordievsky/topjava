package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equalsIgnoreCase("edit")){
            Meal meal = UserMealRepository.getById(Integer.parseInt(req.getParameter("id")));
            req.setAttribute("meal", meal);
            req.setAttribute("action", action);
            req.getRequestDispatcher("editMeal.jsp").forward(req, resp);
        }
        else if (action != null && action.equalsIgnoreCase("delete")){
            UserMealRepository.remove(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect("meals");
        }
        else {
            List<MealTo> list = MealsUtil.filteredByStreams(UserMealRepository.getList(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
            req.setAttribute("list" ,list);
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime date = LocalDateTime.parse(req.getParameter("date") + "T"+ req.getParameter("time") + ":00");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String index = req.getParameter("id");

        if (index != null && !index.isEmpty()){
            int id = Integer.parseInt(index);
            Meal meal = UserMealRepository.getById(id);
            meal.setDateTime(date);
            meal.setDescription(description);
            meal.setCalories(calories);
            UserMealRepository.updateById(id, meal);
        }
        else {
            UserMealRepository.addValue(new Meal(date, description, calories));
        }
        doGet(req, resp);
    }
}
