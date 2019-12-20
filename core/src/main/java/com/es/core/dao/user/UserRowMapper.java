package com.es.core.dao.user;

import com.es.core.model.user.Role;
import com.es.core.model.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("ID"))
                .username(resultSet.getString("USERNAME"))
                .password(resultSet.getString("PASSWORD"))
                .role(Role.valueOf(resultSet.getString("ROLE"))).build();

    }
}
