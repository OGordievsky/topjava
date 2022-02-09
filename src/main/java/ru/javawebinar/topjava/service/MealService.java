package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    List<Meal> getAll();
    Meal getById(int id);
    Meal update(int id, LocalDateTime localDateTime, String description, int calories);
    Meal add(LocalDateTime localDateTime, String description, int calories);
    boolean delete(Meal meal);
}