package com.example.mvps.ui.tongpao;

import android.widget.StackView;

import com.example.mvps.R;
import com.example.mvps.adapter.MyStackAdapter;
import com.example.mvps.base.BaseFragment;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.example.mvps.presenter.RecommendPreserenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoItemFindFragment extends BaseFragment<RecommendPreserenter> {


    @BindView(R.id.stackview)
    StackView stackview;
    private List<TPVideoBean.DataBean.ListBean> list = new ArrayList<>();
    private MyStackAdapter adapter;

    public void setList(List<TPVideoBean.DataBean.ListBean> list) {
        this.list = list;
    }

    @Override
    public int getLayout() {
        return R.layout.find_layout;
    }

    @Override
    public void initView() {
        // 为AdapterViewFlipper设置Adapter
        adapter = new MyStackAdapter(getContext(), list);
        stackview.setAdapter(adapter);
    }

    @Override
    public RecommendPreserenter createPersenter() {
        return null;
    }

    @Override
    public void initData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.onDestroyVideo();
    }

    @Override
    public void fila(String error) {

    }
}
