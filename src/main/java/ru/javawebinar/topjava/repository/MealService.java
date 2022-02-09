package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    List<Meal> getAll();
    Meal getById(int id);
    Meal update(Meal meal);
    Meal add(Meal meal);
    boolean delete(int id);
}