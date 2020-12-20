package com.example.mvps.presenter;

import com.example.mvps.base.BasePresenter;
import com.example.mvps.data.tongpaohome.BannerBean;
import com.example.mvps.data.tongpaohome.Hot_userBase;
import com.example.mvps.data.tongpaohome.PersonalBean;
import com.example.mvps.data.tongpaohome.RecommendBean;
import com.example.mvps.data.tongpaohome.TopicBean;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.tongpao.TongPoa;
import com.example.mvps.model.RecommendModel;

public class RecommendPreserenter extends BasePresenter<TongPoa.View> implements TongPoa.Presenter {

    TongPoa.View view;
    private final TongPoa.Model homeModel;

    public RecommendPreserenter(TongPoa.View view) {
        homeModel = new RecommendModel();
        this.view = view;
    }


    @Override
    public void getRecommend() {
        this.homeModel.getRecommend(new Callback() {
            @Override
            public void fail(String msg) {
                view.fila(msg);
            }

            @Override
            public void success(Object o) {
                view.getRecommend((RecommendBean) o);
            }
        });
    }

    @Override
    public void getBanner() {
        this.homeModel.getBanner(new Callback() {
            @Override
            public void fail(String msg) {
                view.fila(msg);

            }

            @Override
            public void success(Object o) {
                view.getBanner((BannerBean) o);
            }
        });
    }

    @Override
    public void getTopic() {
        this.homeModel.getTopic(new Callback() {
            @Override
            public void fail(String msg) {
                view.fila(msg);

            }

            @Override
            public void success(Object o) {
                view.getTopic((TopicBean) o);
            }
        });
    }

    @Override
    public void getHot_user() {
        this.homeModel.getHot_user(new Callback() {
            @Override
            public void fail(String msg) {
                view.fila(msg);
            }

            @Override
            public void success(Object o) {
                view.getHot_user((Hot_userBase) o);
            }
        });
    }

    @Override
    public void getPersonal() {
        this.homeModel.getPersonal(new Callback() {
            @Override
            public void fail(String msg) {
                view.fila(msg);
            }

            @Override
            public void success(Object o) {
                view.getPersonal((PersonalBean) o);
            }
        });
    }
}
