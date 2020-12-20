package com.example.mvps.model;

import com.example.mvps.base.BaseModel;
import com.example.mvps.data.CityData;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.IHome;
import com.example.mvps.net.CommonSubscriber;
import com.example.mvps.net.HttpmManager;
import com.example.mvps.utils.RxUtils;

import io.reactivex.disposables.Disposable;

public class  HomeModel extends BaseModel implements IHome.Model {
    @Override
    public void getCity(Callback callback) {
        Disposable disposable= HttpmManager.getHttpmManager().getApi()
                .getCity()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<CityData>(callback) {
                    @Override
                    public void onNext(CityData cityData) {
                        callback.success(cityData);
                    }
                });
        addDisposable(disposable);
    }
}
