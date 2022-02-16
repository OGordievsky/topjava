package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll with id={}", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
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

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, SecurityUtil.authUserId());
        assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal);
    }

    public List<MealTo> getFilter(LocalDate minDateValue, LocalDate maxDateValue, LocalTime minTimeValue, LocalTime maxTimeValue) {
        log.info("get filter by positions: startDate {} endDate {} & startTime{} endTime{}", minDateValue, maxDateValue, minTimeValue, maxTimeValue);
        LocalDate startDate = minDateValue != null ? minDateValue : LocalDate.MIN;
        LocalDate endDate = maxDateValue != null ? maxDateValue : LocalDate.MAX;
        LocalTime startTime = minTimeValue != null ? minTimeValue : LocalTime.MIN;
        LocalTime endTime = maxTimeValue != null ? maxTimeValue : LocalTime.MAX;
        return MealsUtil.getTos(service.getDateFilter(SecurityUtil.authUserId(), startDate, endDate), SecurityUtil.authUserCaloriesPerDay()).stream()
                .filter(mealTo -> DateTimeUtil.timeIsBetweenHalfOpen(mealTo.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}
