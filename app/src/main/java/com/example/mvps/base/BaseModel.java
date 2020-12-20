package com.example.mvps.base;

import com.example.mvps.interfaces.IModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseModel implements IModel {
    CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void close() {
        compositeDisposable.clear();
    }
}
