package com.es.core.dao.phone;

import com.es.core.model.phone.Phone;
import org.springframework.dao.DataAccessException;
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
            "select PHONES.*, COLORS.ID as COLOR_ID, COLORS.CODE as COLOR_CODE from PUBLIC.PHONES " +
                    "left join PHONE2COLOR on PHONES.ID = PHONE2COLOR.PHONEID " +
                    "left join COLORS on PHONE2COLOR.COLORID = COLORS.ID where PHONES.ID = ?";

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

    //language=SQL
    private final static String DECREASE_PHONE_STOCK_BY_ID =
            "update STOCKS set STOCK = ((select STOCK from STOCKS where PHONEID = ?) - ?) where PHONEID = ?";

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void save(final Phone phone) {

    }

    @Override
    public Optional<Phone> find(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        try {
            return Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID, new PhoneResultSetExtractor.PhoneRowMapper(), id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
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
        final String firstPartQuery = "select count(distinct PHONES.ID) as AMOUNT from (" +
                "select PUBLIC.PHONES.*, STOCK from PUBLIC.PHONES join STOCKS on STOCKS.PHONEID = ID " +
                "where STOCK > 0 and PRICE is not null";
        final String lastPartQuery = ")) PHONES left join PHONE2COLOR on PHONES.ID = PHONE2COLOR.PHONEID " +
                "left join COLORS on PHONE2COLOR.COLORID = COLORS.ID";
        return jdbcTemplate.queryForObject(firstPartQuery
                        .concat(Arrays.stream(getWords(search))
                                .collect(Collectors.joining(
                                        "%' OR LOWER(PHONES.MODEL) like '%",
                                        " and (LOWER(PHONES.MODEL) like '%",
                                        "%' ")))
                        .concat(lastPartQuery),
                (resultSet, i) -> resultSet.getLong("AMOUNT"));
    }

    @Override
    public List<Phone> findAllBySearchQuery(String searchQuery, int offset, int limit) {
        final String firstPartQuery = "select PHONES.*, COLORS.ID as COLOR_ID, " +
                "COLORS.CODE as COLOR_CODE from (" +
                "select PUBLIC.PHONES.*, STOCK from PUBLIC.PHONES join STOCKS on STOCKS.PHONEID = ID " +
                "where STOCK > 0 and PRICE is not null";
        final String lastPartQuery = ") offset ? limit ?)" +
                "PHONES left join PHONE2COLOR on PHONES.ID = PHONE2COLOR.PHONEID " +
                "left join COLORS on PHONE2COLOR.COLORID = COLORS.ID";
        return jdbcTemplate.query(firstPartQuery
                        .concat(Arrays.stream(getWords(searchQuery))
                                .collect(Collectors.joining(
                                        "%' OR LOWER(PHONES.MODEL) like '%",
                                        " and (LOWER(PHONES.MODEL) like '%",
                                        "%' ")))
                        .concat(lastPartQuery),
                new PhoneResultSetExtractor(), offset, limit);
    }

    @Override
    public void decreasePhoneStockById(Long phoneId, Long quantity) {
        jdbcTemplate.update(DECREASE_PHONE_STOCK_BY_ID, phoneId, quantity, phoneId);
    }

    private String[] getWords(String searchQuery) {
        return searchQuery
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", " ").split(" ");
    }
}
