package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> meals1 = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );
    public static final List<Meal> meals2 = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак2", 500),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Обед2", 1000),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 0), "Ужин2", 500),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 0, 0), "Еда на граничное значение2", 100),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 10, 0), "Завтрак2", 1000),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 13, 0), "Обед2", 500),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 20, 0), "Ужин2", 410)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilterDateTimeTos(Collection<Meal> meals, int caloriesPerDay, LocalDate startDate, LocalDate endDate,
                                                          LocalTime startTime, LocalTime endTime) {
        return getTos(meals.stream()
                .filter(meal -> DateTimeUtil.dateIsBetweenClosed(meal.getDateTime().toLocalDate(), startDate, endDate))
                .filter(meal -> DateTimeUtil.timeIsBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList()), caloriesPerDay);
    }

    public static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }


}
