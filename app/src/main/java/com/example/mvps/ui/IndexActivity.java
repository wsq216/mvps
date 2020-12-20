package com.example.mvps.ui;

import android.util.Log;
import android.widget.TextView;

import com.example.mvps.R;
import com.example.mvps.base.BaseActivity;
import com.example.mvps.data.index.IndexBean;
import com.example.mvps.interfaces.Index;
import com.example.mvps.presenter.IndexPreserenter;

public class IndexActivity extends BaseActivity<IndexPreserenter> implements Index.View {


    private TextView text;

    @Override
    protected IndexPreserenter createPersenter() {
        return new IndexPreserenter(this);
    }

    @Override
    protected void initData() {
        presenter.getIndex();
    }

    @Override
    protected void initView() {
        text=findViewById(R.id.text);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_index;
    }

    @Override
    public void fila(String error) {
        Log.d("TAG", "fila: " + error);
    }


    @Override
    public void getIndex(IndexBean indexBean) {
        Log.d("TAG", "getIndex: "+indexBean.getData().getBanner().get(0).getName());
    }
}
