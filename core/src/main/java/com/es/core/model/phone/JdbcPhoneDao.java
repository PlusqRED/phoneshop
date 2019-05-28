package com.es.core.model.phone;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {

    //language=SQL
    private final static String FIND_BY_ID =
            "select * from PUBLIC.PHONES " +
                    "where id = ?";

    //language=SQL
    private final static String FIND_ALL =
            "select * from PUBLIC.PHONES offset ? limit ?";

    //language=SQL
    private final static String FIND_COLORS_BY_PHONE_ID =
            "select * from PUBLIC.PHONE2COLOR " +
                    "join PUBLIC.COLORS on PUBLIC.PHONE2COLOR.COLORID = PUBLIC.COLORS.ID " +
                    "where PUBLIC.PHONE2COLOR.PHONEID = ?";

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
        phone.setColors(new HashSet<>(findPhoneColors(phone.getId())));
        return Optional.of(phone);
    }

    @Override
    public void update(Phone model) {

    }

    @Override
    public void delete(Phone model) {

    }

    public List<Phone> findAll(int offset, int limit) {
        List<Phone> phones = jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Phone.class), offset, limit);
        phones.forEach(phone -> phone.setColors(new HashSet<>(findPhoneColors(phone.getId()))));
        return phones;
    }

    @Override
    public List<Color> findPhoneColors(Long phoneId) {
        if (phoneId == null) {
            throw new IllegalArgumentException("phoneId cannot be null");
        }
        return jdbcTemplate.query(FIND_COLORS_BY_PHONE_ID, new BeanPropertyRowMapper<>(Color.class), phoneId);
    }
}
