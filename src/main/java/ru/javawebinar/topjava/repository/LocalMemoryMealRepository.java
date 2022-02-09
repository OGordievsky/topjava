package ru.javawebinar.topjava.repository;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LocalMemoryMealRepository implements MealService {

    private final AtomicInteger index = new AtomicInteger(0);
    private final Map<Integer, Meal> localMealsMap = new ConcurrentHashMap<>();

    public LocalMemoryMealRepository() {
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        add(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 14, 0), "Dinner", 410));
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(localMealsMap.values());
    }

    @Override
    public Meal getById(int id) {
        return localMealsMap.get(id);
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(index.getAndIncrement());
        return localMealsMap.put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int id) {
        return localMealsMap.remove(id) != null;
    }

    @Override
    public Meal update(Meal meal) {
        return localMealsMap.put(meal.getId(), meal);
    }
}
