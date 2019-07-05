package com.es.core.service.user;


import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
