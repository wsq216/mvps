package com.example.mvps.ui.tongpao;

import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.example.mvps.R;
import com.example.mvps.adapter.VideoAdapter;
import com.example.mvps.adapter.ViewPagerAdapter;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.base.BaseFragment;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.example.mvps.presenter.RecommendPreserenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoItemHotFragment extends BaseFragment<RecommendPreserenter> {

    @BindView(R.id.mRec_hot)
    RecyclerView mRecHot;

    private VideoAdapter adapter;
    private List<TPVideoBean.DataBean.ListBean> list = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;

    public void setList(List<TPVideoBean.DataBean.ListBean> list) {
        this.list = list;
    }

    @Override
    public int getLayout() {
        return R.layout.hot_layout;
    }

    @Override
    public void initView() {
        mRecHot.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new VideoAdapter(getActivity(), list);
        mRecHot.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        adapter.setiListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                videoPopup(pos);
            }
        });
    }

    @Override
    public RecommendPreserenter createPersenter() {
        return null;
    }

    @Override
    public void initData() {
        adapter.notifyDataSetChanged();
    }

    private void videoPopup(int pos) {
        View view = View.inflate(getActivity(), R.layout.popup_video_layout, null);
        PopupWindow window = new PopupWindow(view, -1, -2);
        List<TPVideoBean.DataBean.ListBean> listBeans = new ArrayList<>();
        for (int i = 0; i < list.size()-pos-1; i++) {
            listBeans.add(list.get(i));
        }

        ViewPager2 view_pager = view.findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(getActivity(),listBeans);
        view_pager.setAdapter(viewPagerAdapter);
        view_pager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        window.showAtLocation(mRecHot, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPagerAdapter.onDestroyVideo();
    }

    @Override
    public void fila(String error) {

    }
}
