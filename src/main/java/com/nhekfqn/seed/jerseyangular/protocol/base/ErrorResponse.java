package com.nhekfqn.seed.jerseyangular.protocol.base;

public class ErrorResponse extends Response {

    private String errorMessage;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
