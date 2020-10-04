package com.ediga.currencyconverter.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://apilayer.net/api/live/";
    //Please change Token Incase of Expiry / Limit over
    protected static final String CURRENCY_TOKEN = "8aeaa678d55e9e7df19bc1ffc8f53f69";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}
