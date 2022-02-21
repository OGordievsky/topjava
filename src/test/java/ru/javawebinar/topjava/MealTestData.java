package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal[] meals = {
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
            new Meal(8, LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак", 600),
            new Meal(9, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак Admin", 500),
            new Meal(10, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед Admin", 1000),
            new Meal(11, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин Admin", 500),
            new Meal(12, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение Admin", 100),
            new Meal(13, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак Admin", 1000),
            new Meal(14, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед Admin", 500),
            new Meal(15, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин Admin", 410),
            new Meal(16, LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак Admin", 600)
    } ;

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "testData", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = meals[0];
        updated.setDateTime( LocalDateTime.of(2020, Month.FEBRUARY, 1, 12, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }

}
