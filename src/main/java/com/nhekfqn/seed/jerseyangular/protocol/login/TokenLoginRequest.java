package com.nhekfqn.seed.jerseyangular.protocol.login;

import com.nhekfqn.seed.jerseyangular.protocol.base.Request;

public class TokenLoginRequest extends Request {

    private String oldAuthToken;

    public String getOldAuthToken() {
        return oldAuthToken;
    }

    public void setOldAuthToken(String oldAuthToken) {
        this.oldAuthToken = oldAuthToken;
    }

}
