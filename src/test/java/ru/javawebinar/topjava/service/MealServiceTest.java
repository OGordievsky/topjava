package ru.javawebinar.topjava.service;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.GUEST_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void delete() {
        service.delete(meal1.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(START_DATE, END_DATE, USER_ID), meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), meal8, meal7, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    public void update() {
        Meal meal = getUpdated();
        service.update(meal, USER_ID);
        assertMatch(service.get(meal.getId(), USER_ID), meal);
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void createDuplicate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, DUPLICATE_DATE_TIME, "duplicate dataTime", 1200), USER_ID));
    }

    @Test
    public void deleteForeign() {
        assertThrows(NotFoundException.class, () -> service.delete(9, GUEST_ID));
    }

    @Test
    public void getForeign() {
        assertThrows(NotFoundException.class, () -> service.get(1, GUEST_ID));
    }

    @Test
    public void updateForeign() {
        Meal meal = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(meal, GUEST_ID));
    }
}