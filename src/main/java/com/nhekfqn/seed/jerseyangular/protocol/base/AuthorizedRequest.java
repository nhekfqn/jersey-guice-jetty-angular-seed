package com.nhekfqn.seed.jerseyangular.protocol.base;

public abstract class AuthorizedRequest extends Request {

    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
