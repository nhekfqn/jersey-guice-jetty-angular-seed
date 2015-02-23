package com.nhekfqn.seed.jerseyangular.protocol.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

public class Response {

    @JsonIgnore
    public String getDetails() {
        return ToStringBuilder.reflectionToString(this); // todo: check style, check recursion
    }

}
