package com.example.mvps.interfaces;

import com.example.mvps.data.index.IndexBean;

public interface Index {

    //home业务下的 v层接口
    interface View extends BeanView {
        void getIndex(IndexBean indexBean);
    }

    //home业务下 P层接口
    interface Presenter  extends BasePersenter<View> {
        void getIndex();

    }

    //home业务下的model
    interface Model extends IModel {
        void getIndex(Callback callback);

    }

}
