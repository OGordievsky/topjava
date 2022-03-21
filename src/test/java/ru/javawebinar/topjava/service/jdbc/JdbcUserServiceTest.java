package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Override
    public void setJpaUtil(JpaUtil jpaUtil){
        this.jpaUtil = null;
    }

    @Override
    public void setup() {
        cacheManager.getCache("users").clear();
    }

    @Override
    public void getAll() {
        List<User> all = service.getAll();
        USER_MATCHER_IGNORE_ROLES.assertMatch(all, admin, guest, user);
    }

    @Test
    public void addRole(){
        service.addRoles(GUEST_ID, Role.USER);
        USER_MATCHER.assertMatch(service.get(GUEST_ID), guestWithUserRole);
    }

    @Test
    public void removeRole(){
        service.removeRoles(ADMIN_ID, Role.USER);
        USER_MATCHER.assertMatch(service.get(ADMIN_ID), adminWithoutUserRole);
    }
}