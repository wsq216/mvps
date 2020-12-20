package com.example.mvps.interfaces.tongpao;

import com.example.mvps.interfaces.BasePersenter;
import com.example.mvps.interfaces.BeanView;

public interface BigImgs {
    interface View extends BeanView {
        void getBing(String string);
    }
    interface Presenter extends BasePersenter<View> {
        void getBing(String url);
    }
}
