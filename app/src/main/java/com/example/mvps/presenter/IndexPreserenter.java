package com.example.mvps.presenter;

import com.example.mvps.base.BasePresenter;
import com.example.mvps.data.index.IndexBean;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.Index;
import com.example.mvps.model.IndexModel;

public class IndexPreserenter extends BasePresenter<Index.View> implements Index.Presenter {

    Index.View view;
    private final Index.Model homeModel;

    public IndexPreserenter(Index.View view) {
        homeModel = new IndexModel();
        this.view = view;
    }



    @Override
    public void getIndex() {
        this.homeModel.getIndex(new Callback() {
            @Override
            public void fail(String msg) {
                view.fila(msg);
            }

            @Override
            public void success(Object o) {
                view.getIndex((IndexBean) o);
            }
        });
    }
}
