package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.LocalMemoryMealRepository;

import java.time.LocalDateTime;
import java.util.List;

public class MealServiceImpl implements MealService{

    LocalMemoryMealRepository repository = new LocalMemoryMealRepository();

    @Override
    public List<Meal> getAll() {
        return repository.getAll();
    }

    @Override
    public Meal getById(int id) {
        return repository.getById(id);
    }

    @Override
    public Meal update(int id, LocalDateTime localDateTime, String description, int calories) {
        Meal meal = getById(id);
        if (localDateTime != null){
            meal.setDateTime(localDateTime);
        }
        if (description != null){
            meal.setDescription(description);
        }
        meal.setCalories(calories);
        return repository.update(meal);
    }

    @Override
    public Meal add(LocalDateTime dateTime, String description, int calories) {
        return repository.add(new Meal(LocalMemoryMealRepository.getIndexKey(), dateTime, description, calories));
    }

    @Override
    public boolean delete(Meal meal) {
        return repository.delete(meal) == meal;
    }
}
