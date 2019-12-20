package com.es.core.dao.user;

import com.es.core.model.user.Role;
import com.es.core.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/test-context.xml")
public class JdbcUserDaoTest {

    @Resource
    private JdbcUserDao jdbcUserDao;


    @Test
    public void testSave() {
        User user = User.builder().username("test" + Math.random()).password("test").role(Role.ROLE_ADMIN).build();
        assertNull(jdbcUserDao.findByUsername(user.getUsername()));
        jdbcUserDao.save(user);
        assertNotNull(jdbcUserDao.findByUsername(user.getUsername()));
    }

    @Test
    public void testFindByUsername() {
        User user = User.builder().username("test" + Math.random()).password("test").role(Role.ROLE_ADMIN).build();
        jdbcUserDao.save(user);
        assertNotNull(jdbcUserDao.findByUsername(user.getUsername()));
    }
}
