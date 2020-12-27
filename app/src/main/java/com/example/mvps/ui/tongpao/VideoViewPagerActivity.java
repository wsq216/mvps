package com.example.mvps.ui.tongpao;

import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;

import com.example.mvps.R;
import com.example.mvps.adapter.ViewPagerAdapter;
import com.example.mvps.base.BaseActivity;
import com.example.mvps.base.BasePresenter;
import com.example.mvps.data.tongpaohome.TPVideoBean;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoViewPagerActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos", 0);
        List<TPVideoBean.DataBean.ListBean> list = (List<TPVideoBean.DataBean.ListBean>) intent.getSerializableExtra("list");

        viewPagerAdapter = new ViewPagerAdapter(this, list);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager.setCurrentItem(pos);
    }

    @Override
    protected int getLayout() {
        return R.layout.popup_video_layout;
    }

    @Override
    public void fila(String error) {

    }
    @Override
    public void onStop() {
        super.onStop();
        viewPagerAdapter.onDestroyVideo();
    }

}
