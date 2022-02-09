package ru.javawebinar.topjava.repository;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalMemoryMealRepository {

    private static final AtomicInteger index = new AtomicInteger(0);
    private static final Map<Integer, Meal> localMealsMap = new ConcurrentHashMap<>();

    public LocalMemoryMealRepository() {
        initialiseList();
    }

    private void initialiseList() {
        localMealsMap.putAll(Stream.of(
                new Meal(LocalMemoryMealRepository.getIndexKey(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalMemoryMealRepository.getIndexKey(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalMemoryMealRepository.getIndexKey(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalMemoryMealRepository.getIndexKey(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalMemoryMealRepository.getIndexKey(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalMemoryMealRepository.getIndexKey(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalMemoryMealRepository.getIndexKey(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new Meal(LocalMemoryMealRepository.getIndexKey(), LocalDateTime.of(2020, Month.FEBRUARY, 1, 14, 0), "Dinner", 410)
        ).collect(Collectors.toMap(Meal::getId, Function.identity())));
    }

    public static int getIndexKey() {
        return index.getAndIncrement();
    }

    public List<Meal> getAll() {
        return new ArrayList<>(localMealsMap.values());
    }

    public Meal getById(int index) {
        return LocalMemoryMealRepository.localMealsMap.get(index);
    }

    public Meal add(Meal meal) {
        return LocalMemoryMealRepository.localMealsMap.put(meal.getId(), meal);
    }

    public Meal update(Meal meal) {
        return LocalMemoryMealRepository.localMealsMap.put(meal.getId(), meal);
    }

    public Meal delete(Meal meal) {
        return LocalMemoryMealRepository.localMealsMap.remove(meal.getId());
    }
}
