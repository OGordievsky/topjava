package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final int userCaloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll with id={}", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), userCaloriesPerDay);
    }

    public Meal get(int id) {
        log.info("get {} with id={}", id, SecurityUtil.authUserId());
        return service.get(SecurityUtil.authUserId(), id);
    }

    public Meal create(Meal meal) {
        log.info("create {} with id={}", meal, SecurityUtil.authUserId());
        checkNew(meal);
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete {} with id={}", id, SecurityUtil.authUserId());
        service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal, SecurityUtil.authUserId());
        service.update(SecurityUtil.authUserId(), meal);
    }

    public List<MealTo> getFilter(LocalDate minDateValue, LocalDate maxDateValue, LocalTime minTimeValue, LocalTime maxTimeValue) {
        log.info("get filter by positions: startDate {} endDate {} & startTime{} endTime{}", minDateValue, maxDateValue, minTimeValue, maxTimeValue);
        return MealsUtil.getTos(service.getFilter(SecurityUtil.authUserId(), minDateValue, maxDateValue, minTimeValue, maxTimeValue), userCaloriesPerDay);
    }
}
