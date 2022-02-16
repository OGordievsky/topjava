package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals1.forEach(meal -> save(1, meal));
        MealsUtil.meals2.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer, Meal> userMealMap = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            userMealMap.put(meal.getId(), meal);
            repository.put(userId, userMealMap);
            return meal;
        } else if (userMealMap.containsKey(meal.getId())) {
            meal.setUserId(userId);
            userMealMap.put(meal.getId(), meal);
            repository.put(userId, userMealMap);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer, Meal> userMealMap = repository.get(userId);
        Meal mealId = userMealMap != null ? userMealMap.get(id) : null;
        if (mealId != null) {
            mealId = userMealMap.remove(id);
        }
        if (userMealMap != null && userMealMap.isEmpty()) {
            repository.remove(userId);
        } else {
            repository.put(userId, userMealMap);
        }
        return mealId != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer, Meal> userMealMap = repository.get(userId);
        return userMealMap != null ? userMealMap.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMealMap = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        return userMealMap.values().stream()
                .sorted(Collections.reverseOrder(Comparator.comparing(Meal::getDateTime)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getDateFilter(int userId, LocalDate startDate, LocalDate endDate) {
        Map<Integer, Meal> tempMealMap = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        return tempMealMap.values().stream()
                .filter(meal -> DateTimeUtil.dateIsBetweenClosed(meal.getDate(), startDate, endDate))
                .sorted(Collections.reverseOrder(Comparator.comparing(Meal::getDateTime)))
                .collect(Collectors.toList());
    }
}

