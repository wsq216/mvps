package com.example.mvps.model;

import com.example.mvps.base.BaseModel;
import com.example.mvps.data.tongpaohome.BannerBean;
import com.example.mvps.data.tongpaohome.Hot_userBase;
import com.example.mvps.data.tongpaohome.PersonalBean;
import com.example.mvps.data.tongpaohome.RecommendBean;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.example.mvps.data.tongpaohome.TopicBean;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.tongpao.TongPoa;
import com.example.mvps.net.CommonSubscriber;
import com.example.mvps.net.HttpmManager;
import com.example.mvps.utils.RxUtils;

import io.reactivex.disposables.Disposable;

public class RecommendModel extends BaseModel implements TongPoa.Model {

    @Override
    public void getRecommend(Callback callback) {
        Disposable disposable= HttpmManager.getHttpmManager().getRecommend()
                .getRecommend()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<RecommendBean>(callback) {
                    @Override
                    public void onNext(RecommendBean recommendBean) {
                        callback.success(recommendBean);
                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void getBanner(Callback callback) {
        Disposable disposable= HttpmManager.getHttpmManager().getRecommend()
                .getBanner()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<BannerBean>(callback) {
                    @Override
                    public void onNext(BannerBean bannerBean) {
                        callback.success(bannerBean);

                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void getTopic(Callback callback) {
        Disposable disposable= HttpmManager.getHttpmManager().getRecommend()
                .getTopic_discussed()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<TopicBean>(callback) {
                    @Override
                    public void onNext(TopicBean topicBean) {
                        callback.success(topicBean);
                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void getHot_user(Callback callback) {
        Disposable disposable= HttpmManager.getHttpmManager().getRecommend()
                .getHot_user()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<Hot_userBase>(callback) {
                    @Override
                    public void onNext(Hot_userBase hot_userBase) {
                        callback.success(hot_userBase);

                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void getPersonal(Callback callback) {
        Disposable disposable= HttpmManager.getHttpmManager().getRecommend()
                .getPersonal()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<PersonalBean>(callback) {
                    @Override
                    public void onNext(PersonalBean personalBean) {
                        if (personalBean!=null) {
                            callback.success(personalBean);
                        }
                    }
                });
        addDisposable(disposable);
    }

    @Override
    public void getVideo(Callback callback) {
        Disposable disposable= HttpmManager.getHttpmManager().getRecommend()
                .getVideoData()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<TPVideoBean>(callback) {
                    @Override
                    public void onNext(TPVideoBean personalBean) {
                        if (personalBean!=null) {
                            callback.success(personalBean);
                        }
                    }
                });
        addDisposable(disposable);
    }
}
