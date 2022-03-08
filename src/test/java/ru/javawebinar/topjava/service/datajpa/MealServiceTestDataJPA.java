package ru.javawebinar.topjava.service.datajpa;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(Profiles.DATAJPA)

public class MealServiceTestDataJPA extends AbstractMealServiceTest {
    @Test
    @Transactional
    public void getUser() {
        Meal meal = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        User mealUser = Hibernate.unproxy(meal.getUser(), User.class);
        USER_MATCHER.assertMatch(mealUser, UserTestData.admin);
    }
}
