package com.nhekfqn.seed.jerseyangular.protocol.login;

import com.nhekfqn.seed.jerseyangular.protocol.base.Response;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LoginResponse extends Response {

    private String newAuthToken;

    public LoginResponse(String newAuthToken) {
        this.newAuthToken = newAuthToken;
    }

    public String getNewAuthToken() {
        return newAuthToken;
    }

}
