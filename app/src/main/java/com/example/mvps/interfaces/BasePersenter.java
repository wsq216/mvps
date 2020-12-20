package com.example.mvps.interfaces;

public interface BasePersenter<V extends BeanView> {
    void attachView(V view);
    void unAttachView();

}
