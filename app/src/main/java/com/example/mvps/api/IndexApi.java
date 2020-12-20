package com.example.mvps.api;

import com.example.mvps.data.index.IndexBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface IndexApi {

    String indexApi="https://cdplay.cn/";

    @GET("api/index")
    Flowable<IndexBean> getIndex();

}
