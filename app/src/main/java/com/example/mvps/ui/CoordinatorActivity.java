package com.example.mvps.ui;

import android.content.Context;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvps.R;
import com.example.mvps.base.BaseActivity;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.base.BasePresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CoordinatorActivity extends BaseActivity {


    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        tab.addTab(tab.newTab().setText("首页"));

        List<String> list=new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add("item"+i);
        }
//
        rv.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(list, this);
        rv.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_coordinator;
    }

    @Override
    public void fila(String error) {

    }


    private class MyAdapter extends BaseAdapter {

        private final List list;

        public MyAdapter(List list, Context context) {
            super(list, context);
            this.list = list;
        }

        @Override
        protected int getLagout() {
            return R.layout.item_coord;
        }

        @Override
        protected void bindData(Object o, VH holder) {
            TextView text = (TextView) holder.getViewById(R.id.text);
            text.setText((String) o);
        }
    }
}