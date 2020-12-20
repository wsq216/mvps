package com.example.mvps.api;

import com.example.mvps.data.tongpaodiscover.HotBean;
import com.example.mvps.data.tongpaodiscover.NavigationBean;
import com.example.mvps.data.tongpaodiscover.NewsBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DiscoverApi {
    String BASE_URL = "http://cdwan.cn:7000/tongpao/";

    //热门活动
    @GET("discover/hot_activity.json")
    Flowable<HotBean> getHot();

    @GET("discover/navigation.json")
    Flowable<NavigationBean> getNavigation();

    @GET
    Flowable<NewsBean> getNews(@Url String path);
}
