package com.nhekfqn.seed.jerseyangular.protocol;

import com.nhekfqn.seed.jerseyangular.protocol.base.AuthorizedRequest;

public class GetDataRequest extends AuthorizedRequest {

    private String dataKey;

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

}
