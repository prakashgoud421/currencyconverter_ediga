package com.ediga.currencyconverter.API;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

import static com.ediga.currencyconverter.API.RetrofitInstance.CURRENCY_TOKEN;

public interface CurrencyAPI {

    @GET("/api/list?access_key="+CURRENCY_TOKEN+"&format=1")
    Call<List<String>> getCurrencyTypes();

    @GET("/api/live?access_key="+CURRENCY_TOKEN+"&format=1")
    Call<ResponseBody> getCurrencyRates();
}

