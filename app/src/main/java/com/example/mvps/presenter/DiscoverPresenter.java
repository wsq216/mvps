package com.example.mvps.presenter;

import com.example.mvps.base.BaseModel;
import com.example.mvps.base.BasePresenter;
import com.example.mvps.data.tongpaodiscover.HotBean;
import com.example.mvps.data.tongpaodiscover.NavigationBean;
import com.example.mvps.data.tongpaodiscover.NewsBean;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.tongpaodiscover.Discover;
import com.example.mvps.model.DiscoverModel;

public class DiscoverPresenter extends BasePresenter<Discover.View> implements Discover.Presenter {

    Discover.View view;
    private final Discover.Model discoverModel;

    public DiscoverPresenter(Discover.View view) {
        discoverModel = new DiscoverModel();
        this.view = view;
    }

    @Override
    public void getHot() {
        discoverModel.getHot(new Callback() {
            @Override
            public void fail(String msg) {
                view.fila(msg);
            }

            @Override
            public void success(Object o) {
                view.getHot((HotBean) o);
            }
        });
    }

    @Override
    public void getNavigation() {
        discoverModel.getNavigation(new Callback() {
            @Override
            public void fail(String msg) {
                view.fila(msg);
            }

            @Override
            public void success(Object o) {
                view.getNavigation((NavigationBean) o);
            }
        });
    }

    @Override
    public void getNews(int path) {
        discoverModel.getNews(new Callback() {
            @Override
            public void fail(String msg) {
                view.fila(msg);
            }

            @Override
            public void success(Object o) {
                view.getNews((NewsBean) o);
            }
        },path);
    }
}
