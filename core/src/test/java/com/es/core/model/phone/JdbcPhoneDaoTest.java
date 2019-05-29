package com.es.core.model.phone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/test-context.xml")
public class JdbcPhoneDaoTest {

    @Resource
    private JdbcPhoneDao phoneDao;

    @Test(expected = IllegalArgumentException.class)
    public void findPhoneByIdNullCheck() {
        phoneDao.find(null);
    }

    @Test
    public void testFindPhoneById() {
        Long id = 1000L;
        Optional<Phone> phone = phoneDao.find(id);
        assertTrue(phone.isPresent());
        assertEquals(phone.get().getId(), id);
    }

    @Test
    public void testFindAll() {
        List<Phone> all = phoneDao.findAll(10, 10);
        assertFalse(all.isEmpty());
    }
}
