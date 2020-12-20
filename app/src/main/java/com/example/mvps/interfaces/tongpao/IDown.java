package com.example.mvps.interfaces.tongpao;

import com.example.mvps.interfaces.Callback;

/**
 * 提供给所有的业务使用的公用接口
 */
public interface IDown {

    interface Model{
        void downImage(String url, Callback callback);
    }

}
