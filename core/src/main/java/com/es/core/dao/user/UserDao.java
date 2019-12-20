package com.es.core.dao.user;

import com.es.core.model.user.User;

public interface UserDao {
    void save(User user);

    User findByUsername(String username);
}
