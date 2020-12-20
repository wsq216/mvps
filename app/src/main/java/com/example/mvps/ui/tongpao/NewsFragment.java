package com.example.mvps.ui.tongpao;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvps.R;
import com.example.mvps.base.BaseFragment;
import com.example.mvps.data.tongpaodiscover.HotBean;
import com.example.mvps.data.tongpaodiscover.NavigationBean;
import com.example.mvps.data.tongpaodiscover.NewsBean;
import com.example.mvps.interfaces.tongpaodiscover.Discover;
import com.example.mvps.presenter.DiscoverPresenter;
import com.hyphenate.chat.EMOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class NewsFragment extends BaseFragment<DiscoverPresenter> implements Discover.View {


    @BindView(R.id.rv)
    RecyclerView rv;
    private List<NewsBean.DataBean.ListBean> list;
    private NewsAdapter newsAdapter;
    private int path;

    @Override
    protected DiscoverPresenter createPersenter() {
        return new DiscoverPresenter(this);
    }

    @Override
    protected void initData() {
        presenter.getNews(path);
    }

    @Override
    protected void initView() {
        path = getArguments().getInt("path");

        list = new ArrayList<>();

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        newsAdapter = new NewsAdapter(list,getActivity(),path);

        rv.setAdapter(newsAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }

    @Override
    public void fila(String error) {

    }

    @Override
    public void getHot(HotBean hotBean) {

    }

    @Override
    public void getNavigation(NavigationBean navigationBean) {

    }

    @Override
    public void getNews(NewsBean newsBean) {
        list.clear();
        List<NewsBean.DataBean.ListBean> listBeans = newsBean.getData().getList();
        list.addAll(listBeans);
        newsAdapter.notifyDataSetChanged();
    }
}