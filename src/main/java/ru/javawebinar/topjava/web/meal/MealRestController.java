package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());


    private MealService service;

    public MealRestController(MealService service){
        this.service = service;
    }

    public Collection<Meal> getAll(int userId) {
        log.info("getAll with id={}", userId);
        return service.getAll(userId);
    }

    public Meal get(int userId, int id) {
        log.info("get {} with id={}", id, userId);
        return service.get(userId, id);
    }

    public Meal create(int userId, Meal meal) {
        log.info("create {} with id={}", meal, userId);
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void delete(int userId, int id) {
        log.info("delete {} with id={}", id, userId);
        service.delete(userId, id);
    }

    public void update(int userId, Meal meal) {
        log.info("update {} with id={}",  meal, userId);
        service.update(userId, meal);
    }
}
