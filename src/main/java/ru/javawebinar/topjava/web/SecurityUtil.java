package ru.javawebinar.topjava.web;

import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static final AtomicInteger authUser = new AtomicInteger(0);

    public static void setAuthUser(int userId){
        authUser.set(userId);
    }

    public static int authUserId() {
        return authUser.get();
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}