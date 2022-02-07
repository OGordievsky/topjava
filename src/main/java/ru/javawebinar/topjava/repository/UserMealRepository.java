package ru.javawebinar.topjava.repository;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealRepository {
    private static final Map<Integer, Meal> hashMap = new ConcurrentHashMap<>(Stream.of(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 14, 0), "Dinner", 410)
    ).collect(Collectors.toMap(Meal::getId, Function.identity())));

    public static List<Meal> getList() {
        return new ArrayList<>(hashMap.values());
    }

    public static Meal getById(Integer index) {
        return UserMealRepository.hashMap.get(index);
    }

    public static void addValue(Meal meal) {
        UserMealRepository.hashMap.put(meal.getId(), meal);
    }

    public static void updateById(Integer index, Meal meal) {
        UserMealRepository.hashMap.put(index, meal);
    }

    public static void remove(Integer index) {
        UserMealRepository.hashMap.remove(index);
    }
}
