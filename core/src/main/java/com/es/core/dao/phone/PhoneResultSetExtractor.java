package com.es.core.dao.phone;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneResultSetExtractor implements ResultSetExtractor<List<Phone>> {

    private static final BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);

    private static final RowMapper<Color> colorRowMapper = (rs, i1) -> Color.builder()
            .id(rs.getLong("COLOR_ID"))
            .code(rs.getString("COLOR_CODE"))
            .build();

    @Override
    public List<Phone> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Phone> phones = new HashMap<>();
        for (int i = 0; resultSet.next(); ++i) {
            int phoneId = resultSet.getInt("ID");
            Phone phone = phoneBeanPropertyRowMapper.mapRow(resultSet, i);
            Phone hashedPhone = phones.putIfAbsent(phoneId, phone);
            Color color = colorRowMapper.mapRow(resultSet, i);
            if (hashedPhone != null) {
                addColor(color, hashedPhone);
            } else {
                addColor(color, phone);
            }
        }
        return new ArrayList<>(phones.values());
    }

    private void addColor(Color color, Phone phone) {
        if (color.getCode() != null) {
            phone.getColors().add(color);
        }
    }

    public static class PhoneRowMapper implements RowMapper<Phone> {
        @Override
        public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
            Phone phone = phoneBeanPropertyRowMapper.mapRow(resultSet, i);
            do {
                Color color = colorRowMapper.mapRow(resultSet, i);
                phone.getColors().add(color);
            } while (resultSet.next());
            return phone;
        }
    }

}
