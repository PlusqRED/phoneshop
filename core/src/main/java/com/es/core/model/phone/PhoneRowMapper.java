package com.es.core.model.phone;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneRowMapper implements RowMapper<Phone> {

    private Phone phone;

    @Override
    public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
        if (phone == null || resultSet.getLong("ID") != phone.getId()) {
            phone = Phone.builder().id(resultSet.getLong("ID"))
                    .brand(resultSet.getString("BRAND"))
                    .build();
        } else {
            phone.getColors().add(Color.builder()
                    .id(resultSet.getLong("COLOR_ID"))
                    .code("COLOR_CODE").build());
        }
        return phone;
    }
}
