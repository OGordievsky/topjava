package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.Validator;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.javawebinar.topjava.util.ValidationUtil.validateEntirety;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final Validator validator;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, Validator validator) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.validator = validator;
    }


    @Override
    @Transactional
    public User save(User user) {
        validateEntirety(validator, user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            Set<Role> roles = user.getRoles();
            if (roles != null && !roles.isEmpty()) {
                jdbcTemplate.batchUpdate("INSERT INTO user_roles VALUES (?, ?)", new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, user.getId());
                        ps.setString(2, roles.toArray(new Role[0])[i].name());
                    }

                    @Override
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
            }
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0 || jdbcTemplate.batchUpdate("UPDATE user_roles SET role = ? WHERE user_id = ?",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Set<Role> roles = user.getRoles();
                        if (roles != null && !roles.isEmpty()) {
                            ps.setString(1, user.getRoles().toArray(new Role[0])[i].name());
                        } else {
                            ps.setString(1, null);
                        }
                        ps.setInt(2, user.getId());
                    }

                    public int getBatchSize() {
                        return 1;
                    }
                })[0] == 0) {
            return null;
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return jdbcTemplate.queryForStream("SELECT u.*, ur.role AS roles FROM users u LEFT JOIN user_roles ur ON ur.user_id = u.id WHERE id=?", ROW_MAPPER, id)
                .reduce((u1, u2) -> {
                    u1.setRoles(Stream.concat(u1.getRoles().stream(), u2.getRoles().stream()).collect(Collectors.toSet()));
                    return u1;
                }).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return jdbcTemplate.queryForStream("SELECT u.*, ur.role AS roles FROM users u LEFT JOIN user_roles ur ON ur.user_id = u.id WHERE email=?", ROW_MAPPER, email)
                .reduce((u1, u2) -> {
                    Set<Role> roles = u1.getRoles();
                    roles.addAll(u2.getRoles());
                    u1.setRoles(roles);
                    return u1;
                }).orElse(null);

    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
    }
}