package com.example.mvps.interfaces;

public interface Callback<T> {

    void fail(String msg);

    void success(T t);

}
