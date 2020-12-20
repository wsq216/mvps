package com.example.mvps.api;

import com.example.mvps.data.CityData;
import com.example.mvps.data.WeatherData;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface ServiceApi {
    String BASE_URL = "https://jisutqybmf.market.alicloudapi.com/weather/";

    @GET("city")
    Flowable<CityData> getCity();

    @GET("query")
    Flowable<WeatherData> queryWeathcer(@QueryMap Map<String, String> map);


}
