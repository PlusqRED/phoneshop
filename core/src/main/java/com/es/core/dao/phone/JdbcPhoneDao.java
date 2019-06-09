package com.es.core.dao.phone;

import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JdbcPhoneDao implements PhoneDao {

    //language=SQL
    private final static String FIND_BY_ID =
            "select * from PUBLIC.PHONES " +
                    "where id = ?";

    //language=SQL
    private final static String FIND_PHONES_WITH_STOCK_AND_PRICE =
            "select PUBLIC.PHONES.*, " +
                    "STOCK from PUBLIC.PHONES " +
                    "join STOCKS on STOCKS.PHONEID = ID " +
                    "where STOCK > 0 AND PRICE IS NOT NULL " +
                    "offset ? limit ?";

    //language=SQL
    private final static String FIND_ALL =
            "select PHONES.*, " +
                    "COLORS.ID as COLOR_ID, " +
                    "COLORS.CODE as COLOR_CODE " +
                    "from (" + FIND_PHONES_WITH_STOCK_AND_PRICE + ") PHONES " +
                    "left join PHONE2COLOR on PHONES.ID = PHONE2COLOR.PHONEID " +
                    "left join COLORS on PHONE2COLOR.COLORID = COLORS.ID";

    //language=SQL
    private final static String GET_STOCK_BY_ID = "select STOCK " +
            "from STOCKS " +
            "join PHONES on STOCKS.PHONEID = PHONES.ID " +
            "where ID = ?";

    //language=SQL
    private final static String PRODUCTS_AMOUNT = "select count(PHONES.ID) as AMOUNT from PUBLIC.PHONES";

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
        return jdbcTemplate.query(FIND_ALL, new PhoneResultSetExtractor(), offset, limit);
    }

    @Override
    public Integer getPhoneStockById(Long id) {
        return jdbcTemplate.queryForObject(GET_STOCK_BY_ID, (resultSet, i) -> resultSet.getInt("STOCK"), id);
    }

    @Override
    public Long getProductAmount() {
        return jdbcTemplate.queryForObject(PRODUCTS_AMOUNT, (resultSet, i) -> resultSet.getLong("AMOUNT"));
    }

    @Override
    public Long getProductAmountSearchBased(String search) {
        return jdbcTemplate.queryForObject("select count(PHONES.ID) as AMOUNT from (" +
                        "select PUBLIC.PHONES.*, STOCK from PUBLIC.PHONES join STOCKS on STOCKS.PHONEID = ID " +
                        "where STOCK > 0 AND PRICE IS NOT NULL" +
                        Arrays.stream(getWords(search))
                                .collect(Collectors.joining("%' OR LOWER(PHONES.MODEL) like '%", " and (LOWER(PHONES.MODEL) like '%", "%' ")) +
                        ")) PHONES left join PHONE2COLOR on PHONES.ID = PHONE2COLOR.PHONEID " +
                        "left join COLORS on PHONE2COLOR.COLORID = COLORS.ID",
                (resultSet, i) -> resultSet.getLong("AMOUNT"));
    }

    @Override
    public List<Phone> findAllBySearchQuery(String searchQuery, int offset, int limit) {
        return jdbcTemplate.query("select PHONES.*, COLORS.ID as COLOR_ID, " +
                        "COLORS.CODE as COLOR_CODE from (" +
                        "select PUBLIC.PHONES.*, STOCK from PUBLIC.PHONES join STOCKS on STOCKS.PHONEID = ID " +
                        "where STOCK > 0 AND PRICE IS NOT NULL" +
                        Arrays.stream(getWords(searchQuery))
                                .collect(Collectors.joining("%' OR LOWER(PHONES.MODEL) like '%", " and (LOWER(PHONES.MODEL) like '%", "%' ")) +
                        ") offset ? limit ?)" +
                        "PHONES left join PHONE2COLOR on PHONES.ID = PHONE2COLOR.PHONEID " +
                        "left join COLORS on PHONE2COLOR.COLORID = COLORS.ID",
                new PhoneResultSetExtractor(), offset, limit);
    }

    private String[] getWords(String searchQuery) {
        return searchQuery
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", " ").split(" ");
    }
}
