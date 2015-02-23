package com.nhekfqn.seed.jerseyangular.service;

import com.google.inject.Singleton;
import org.apache.commons.lang3.RandomStringUtils;

@Singleton
public class LoginService {

    // dummy implementation generating random user id for any username/password
    public String getUserId(String username, String password) {
        return RandomStringUtils.randomAlphanumeric(8).toLowerCase();
    }

}
