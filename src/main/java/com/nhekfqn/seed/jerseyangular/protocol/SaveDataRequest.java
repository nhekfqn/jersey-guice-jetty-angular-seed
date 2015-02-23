package com.nhekfqn.seed.jerseyangular.protocol;

import com.nhekfqn.seed.jerseyangular.protocol.base.AuthorizedRequest;

public class SaveDataRequest extends AuthorizedRequest {

    private String dataKey;
    private String product;
    private int amount;

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
