package com.example.mvps.api;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * 图片下载的接口
 */
public interface ImageApi {

    @GET
    Flowable<ResponseBody> downImage(@Url String url);

}
