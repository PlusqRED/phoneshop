package com.es.core.mode.phone;

import com.es.core.model.phone.Color;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/test-context.xml")
public class JdbcPhoneDaoTest {

    @Resource
    private PhoneDao phoneDao;

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

    @Test(expected = IllegalArgumentException.class)
    public void findPhoneColorsNullCheck() {
        phoneDao.findPhoneColors(null);
    }

    @Test
    public void testFindPhoneColors() {
        Long id = 1000L;
        List<Color> phoneColors = phoneDao.findPhoneColors(id);
        assertEquals(phoneColors.size(), 2);
    }
}
