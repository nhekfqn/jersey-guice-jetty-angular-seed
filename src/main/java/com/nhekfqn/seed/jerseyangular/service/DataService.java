package com.nhekfqn.seed.jerseyangular.service;

import com.google.inject.Singleton;
import com.nhekfqn.seed.jerseyangular.domain.Data;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class DataService {

    private Map<String, Data> dataByKey = new HashMap<>();

    public Data getData(String dataKey) {
        return dataByKey.get(dataKey);
    }


    public void saveData(String dataKey, String product, int amount) {
        Data data = new Data();
        data.setProduct(product);
        data.setAmount(amount);

        dataByKey.put(dataKey, data);
    }

}
