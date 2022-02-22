package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

//    public static final int USER_ID = 100000;
//    public static final int FOREIGN_ID = 99999;

    private static int mealId = UserTestData.GUEST_ID;
    public static final Meal meal1 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal7 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal meal8 = new Meal(++mealId, LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак", 600);
    public static final Meal meal9 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак Admin", 500);
    public static final Meal meal10 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед Admin", 1000);
    public static final Meal meal11 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин Admin", 500);
    public static final Meal meal12 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение Admin", 100);
    public static final Meal meal13 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак Admin", 1000);
    public static final Meal meal14 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед Admin", 500);
    public static final Meal meal15 = new Meal(++mealId, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин Admin", 410);
    public static final Meal meal16 = new Meal(++mealId, LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Завтрак Admin", 600);

    public static final LocalDate START_DATE = LocalDate.of(2020, Month.JANUARY, 30);
    public static final LocalDate END_DATE = LocalDate.of(2020, Month.JANUARY, 31);
    public static final LocalDateTime DUPLICATE_DATE_TIME = LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.FEBRUARY, 2, 11, 0), "testData", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = meal1;
        updated.setDateTime( LocalDateTime.of(2020, Month.FEBRUARY, 1, 12, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(330);
        return updated;
    }


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

}
