package com.example.mvps.model;

import com.example.mvps.base.BaseModel;
import com.example.mvps.data.index.IndexBean;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.Index;
import com.example.mvps.net.CommonSubscriber;
import com.example.mvps.net.HttpmManager;
import com.example.mvps.utils.RxUtils;

import io.reactivex.disposables.Disposable;

public class IndexModel extends BaseModel implements Index.Model {
    @Override
    public void getIndex(Callback callback) {
        Disposable disposable= HttpmManager.getHttpmManager().getIndex()
                .getIndex()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<IndexBean>(callback) {
                    @Override
                    public void onNext(IndexBean indexBean) {
                        callback.success(indexBean);
                    }
                });
        addDisposable(disposable);
    }
}
