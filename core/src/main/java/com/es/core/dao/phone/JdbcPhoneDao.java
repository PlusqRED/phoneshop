package com.es.core.dao.phone;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class JdbcPhoneDao implements PhoneDao {

    //language=SQL
    private final static String FIND_BY_ID =
            "select * from PUBLIC.PHONES " +
                    "where id = ?";

    //language=SQL
    private final static String FIND_ALL =
            "select PHONES.*, COLORS.ID as COLOR_ID, COLORS.CODE as COLOR_CODE from PUBLIC.PHONES " +
                    "join PHONE2COLOR on PHONES.ID = PHONE2COLOR.PHONEID " +
                    "join COLORS on PHONE2COLOR.COLORID = COLORS.ID " +
                    "offset ? limit ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void save(final Phone phone) {

    }

    @Override
    public Optional<Phone> find(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Phone phone = jdbcTemplate.queryForObject(FIND_BY_ID, new BeanPropertyRowMapper<>(Phone.class), id);
        return Optional.of(phone);
    }

    @Override
    public void update(Phone model) {

    }

    @Override
    public void delete(Phone model) {

    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(FIND_ALL, (ResultSetExtractor<List<Phone>>) resultSet -> {
            BeanPropertyRowMapper<Phone> phoneBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Phone.class);
            Map<Integer, Phone> phones = new HashMap<>();
            for (int i = 0; resultSet.next(); ++i) {
                int phoneId = resultSet.getInt("ID");
                Phone phone = phones.get(phoneId);
                if (phone == null) {
                    phone = phoneBeanPropertyRowMapper.mapRow(resultSet, i);
                    phone.setColors(new HashSet<>());
                    phones.put(phoneId, phone);
                }
                RowMapper<Color> colorRowMapper = (rs, i1) -> Color.builder()
                        .id(rs.getLong("COLOR_ID"))
                        .code(rs.getString("COLOR_CODE"))
                        .build();

                Color color = colorRowMapper.mapRow(resultSet, i);
                phone.getColors().add(color);
            }
            return new ArrayList<>(phones.values());
        }, offset, limit);
    }
}
