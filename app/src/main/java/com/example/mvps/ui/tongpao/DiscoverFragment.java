package com.example.mvps.ui.tongpao;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvps.R;
import com.example.mvps.adapter.HotAdapter;
import com.example.mvps.base.BaseFragment;
import com.example.mvps.base.BasePresenter;
import com.example.mvps.data.tongpaodiscover.HotBean;
import com.example.mvps.data.tongpaodiscover.NavigationBean;
import com.example.mvps.data.tongpaodiscover.NewsBean;
import com.example.mvps.interfaces.tongpaodiscover.Discover;
import com.example.mvps.presenter.DiscoverPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements Discover.View {


    @BindView(R.id.img_robe)
    ImageView imgRobe;
    @BindView(R.id.robe)
    LinearLayout robe;
    @BindView(R.id.img_organization)
    ImageView imgOrganization;
    @BindView(R.id.organization)
    LinearLayout organization;
    @BindView(R.id.img_ranking)
    ImageView imgRanking;
    @BindView(R.id.ranking)
    LinearLayout ranking;
    @BindView(R.id.rv_)
    RecyclerView rv;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.pager)
    ViewPager pager;
    private List<HotBean.DataBean> list;
    private HotAdapter hotAdapter;

    @Override
    protected DiscoverPresenter createPersenter() {
        return new DiscoverPresenter(this);
    }

    @Override
    protected void initData() {
        presenter.getHot();
        presenter.getNavigation();
    }

    @Override
    protected void initView() {
        //热门活动布局
        list = new ArrayList<>();

        rv.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        hotAdapter = new HotAdapter(list,getActivity());

        rv.setAdapter(hotAdapter);


        robe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "0", Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(this).load(R.drawable.s1).apply(new RequestOptions().circleCrop()).into(imgRobe);
        Glide.with(this).load(R.drawable.s1).apply(new RequestOptions().circleCrop()).into(imgOrganization);
        Glide.with(this).load(R.drawable.s1).apply(new RequestOptions().circleCrop()).into(imgRanking);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    public void fila(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    private static final String TAG = "DiscoverFragment";
    @Override
    public void getHot(HotBean hotBean) {
        List<HotBean.DataBean> data = hotBean.getData();
        list.addAll(data);
        hotAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNavigation(NavigationBean navigationBean) {
        List<NavigationBean.DataBean> data = navigationBean.getData();
        List<Fragment> list=new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            NavigationBean.DataBean dataBean = data.get(i);
            NewsFragment newsFragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("path",dataBean.getType());
            newsFragment.setArguments(bundle);
            list.add(newsFragment);
        }

        pager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        tab.setupWithViewPager(pager);
        for (int i = 0; i < data.size(); i++) {
            tab.getTabAt(i).setText(data.get(i).getName());
        }

    }

    @Override
    public void getNews(NewsBean newsBean) {

    }
}