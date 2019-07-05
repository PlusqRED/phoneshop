package com.es.core.dao.user;

import com.es.core.model.user.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    //language=SQL
    private final static String FIND_BY_USERNAME =
            "select * from PUBLIC.USERS where USERS.USERNAME = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("ID")
                .execute(getUserParameters(user));
    }

    private Map<String, Object> getUserParameters(User user) {
        Map<String, Object> userParameters = new HashMap<>();
        userParameters.put("USERNAME", user.getUsername());
        userParameters.put("PASSWORD", user.getPassword());
        userParameters.put("ROLE", user.getRole().toString());
        return userParameters;
    }

    @Override
    public User findByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_USERNAME, new UserRowMapper(), username);
        } catch (DataAccessException e) {
            return null;
        }
    }
}
