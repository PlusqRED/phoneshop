package com.es.core.service.user;


public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
