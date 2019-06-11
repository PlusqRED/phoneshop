package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/test-context.xml")
public class SortByTest {

    @Test
    public void testSort() {
        List<Phone> phonesToSort = new ArrayList<>();
        phonesToSort.add(Phone.builder().price(BigDecimal.ONE).build());
        phonesToSort.add(Phone.builder().price(BigDecimal.TEN).build());
        SortBy sortBy = SortBy.PRICE;
        sortBy.setSortOrder(false);
        sortBy.sort(phonesToSort);
        assertEquals(phonesToSort.get(0).getPrice(), BigDecimal.TEN);
    }
}
