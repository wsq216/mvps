package com.example.mvps.presenter;

import com.example.mvps.base.BasePresenter;
import com.example.mvps.data.CityData;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.IHome;
import com.example.mvps.model.HomeModel;

public class HomePreserenter extends BasePresenter<IHome.View> implements IHome.Presenter {

    IHome.View view;
    private final IHome.Model homeModel;

    public HomePreserenter(IHome.View view) {
        homeModel = new HomeModel();
        this.view = view;
    }

    @Override
    public void getCity() {
        this.homeModel.getCity(new Callback() {
            @Override
            public void fail(String msg) {

            }

            @Override
            public void success(Object o) {
                view.getCityReturn((CityData) o);
            }
        });
    }
}
