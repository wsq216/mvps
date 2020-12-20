package com.example.mvps.api;

import com.example.mvps.data.tongpaohome.BannerBean;
import com.example.mvps.data.tongpaohome.Hot_userBase;
import com.example.mvps.data.tongpaohome.PersonalBean;
import com.example.mvps.data.tongpaohome.RecommendBean;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.example.mvps.data.tongpaohome.TopicBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface TongPaoApi {
    String BASE_URL = "http://cdwan.cn:7000/tongpao/";

    @GET("home/recommend.json")
    Flowable<RecommendBean> getRecommend();

    @GET("home/banner.json")
    Flowable<BannerBean> getBanner();

    @GET("home/topic_discussed.json")
    Flowable<TopicBean> getTopic_discussed();

    @GET("home/hot_user.json")
    Flowable<Hot_userBase> getHot_user();


    @GET("home/personal.json")
    Flowable<PersonalBean> getPersonal();

    //首页视频栏数据接口：/home/video.json
    @GET("home/video.json")
    Flowable <TPVideoBean> getVideoData();
}
