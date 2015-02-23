package com.nhekfqn.seed.jerseyangular.protocol;

import com.nhekfqn.seed.jerseyangular.domain.Data;
import com.nhekfqn.seed.jerseyangular.protocol.base.Response;

public class GetDataResponse extends Response {

    private Data data;

    public GetDataResponse(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

}
