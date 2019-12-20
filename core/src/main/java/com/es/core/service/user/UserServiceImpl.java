package com.es.core.service.user;

import com.es.core.dao.user.UserDao;
import com.es.core.model.user.Role;
import com.es.core.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void save(User user) {
        user.setRole(Role.ROLE_USER);
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
