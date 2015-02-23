package com.nhekfqn.seed.jerseyangular.protocol.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Request {

    public String getDetails() {
        return ToStringBuilder.reflectionToString(this); // todo: check style
    }

}
