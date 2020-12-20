package com.example.mvps.model;

import com.example.mvps.base.BaseModel;
import com.example.mvps.data.tongpaodiscover.HotBean;
import com.example.mvps.data.tongpaodiscover.NavigationBean;
import com.example.mvps.data.tongpaodiscover.NewsBean;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.tongpaodiscover.Discover;
import com.example.mvps.net.CommonSubscriber;
import com.example.mvps.net.HttpmManager;
import com.example.mvps.utils.RxUtils;

import io.reactivex.disposables.Disposable;

public class DiscoverModel extends BaseModel implements Discover.Model {
    @Override
    public void getHot(Callback callback) {
        Disposable disposable=HttpmManager.getHttpmManager().getDiscover()
                .getHot()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<HotBean>(callback) {
                    @Override
                    public void onNext(HotBean hotBean) {
                        if (hotBean!=null){
                            callback.success(hotBean);
                        }else{
                            callback.fail("请求失败");
                        }
                    }
                })
                ;
        addDisposable(disposable);
    }

    @Override
    public void getNavigation(Callback callback) {
        Disposable disposable=HttpmManager.getHttpmManager().getDiscover()
                .getNavigation()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<NavigationBean>(callback) {
                    @Override
                    public void onNext(NavigationBean navigationBean) {
                        if (navigationBean!=null){
                            callback.success(navigationBean);
                        }else{
                            callback.fail("请求失败");
                        }
                    }
                })
                ;
        addDisposable(disposable);
    }

    @Override
    public void getNews(Callback callback, int path) {
        Disposable disposable=HttpmManager.getHttpmManager().getDiscover()
                .getNews("discover/news_"+path+".json")
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<NewsBean>(callback) {
                    @Override
                    public void onNext(NewsBean newsBean) {
                        if (newsBean!=null){
                            callback.success(newsBean);
                        }else{
                            callback.fail("请求失败");
                        }
                    }
                });
        addDisposable(disposable);
    }
}
