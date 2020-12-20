package com.example.mvps.presenter;

import com.example.mvps.base.BasePresenter;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.tongpao.BigImgs;
import com.example.mvps.interfaces.tongpao.IDown;
import com.example.mvps.model.ImgsModel;

public class ImgsPresenter extends BasePresenter<BigImgs.View> implements BigImgs.Presenter {
    BigImgs.View view;
    IDown.Model model;

    public ImgsPresenter(BigImgs.View view) {
        model=new ImgsModel();
        this.view = view;
    }

    @Override
    public void getBing(String url) {
        model.downImage(url, new Callback() {
            @Override
            public void fail(String msg) {

            }

            @Override
            public void success(Object o) {
                if (view!=null){
                    view.getBing((String) o);
                }
            }
        });
    }
}
