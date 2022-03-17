package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

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
    public void createWithException() throws Exception {
        Assume.assumeFalse(true);
    }
}