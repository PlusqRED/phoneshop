package com.es.core.service.user;

import com.es.core.model.user.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
