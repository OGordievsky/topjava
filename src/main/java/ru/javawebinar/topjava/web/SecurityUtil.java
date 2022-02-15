package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int userId = 0;

    public static void setAuthUser(int userId) {
        SecurityUtil.userId = userId;
    }

    public static int authUserId() {
        return SecurityUtil.userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}