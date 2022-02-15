package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
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
        Map<Integer, Meal> tempMealMap = repository.containsKey(userId) ? repository.get(userId) : new ConcurrentHashMap<>();
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            tempMealMap.put(meal.getId(), meal);
            repository.put(userId, tempMealMap);
            return meal;
        } else if (tempMealMap.containsKey(meal.getId()) && tempMealMap.get(meal.getId()).getUserId() == userId) {
            meal.setUserId(userId);
            tempMealMap.put(meal.getId(), meal);
            repository.put(userId, tempMealMap);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        if (repository.containsKey(userId)) {
            Map<Integer, Meal> tempMealMap = repository.get(userId);
            Meal tempMealId = tempMealMap.get(id);
            if (tempMealId != null && tempMealId.getUserId() == userId) {
                tempMealId = tempMealMap.remove(id);
            }
            if (tempMealMap.isEmpty()) {
                repository.remove(userId);
            } else {
                repository.put(userId, tempMealMap);
            }
            return tempMealId != null;
        }
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        if (repository.containsKey(userId)) {
            Map<Integer, Meal> tempMealMap = repository.get(userId);
            Meal mealId = tempMealMap.get(id);
            if (mealId != null && mealId.getUserId() == userId) {
                return mealId;
            }
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        if (repository.containsKey(userId)) {
            Map<Integer, Meal> tempMealMap = repository.get(userId);
            return tempMealMap.values().stream()
                    .sorted(Collections.reverseOrder(Comparator.comparing(Meal::getDateTime)))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Meal> getFilter(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        if (repository.containsKey(userId)) {
            Map<Integer, Meal> tempMealMap = repository.get(userId);
            return tempMealMap.values().stream()
                    .filter(meal -> DateTimeUtil.dateIsBetweenClosed(meal.getDate(), startDate, endDate))
                    .filter(meal -> DateTimeUtil.timeIsBetweenHalfOpen(meal.getTime(), startTime, endTime))
                    .sorted(Collections.reverseOrder(Comparator.comparing(Meal::getDateTime)))
                    .collect(Collectors.toList());
        }
        return null;
    }
}

