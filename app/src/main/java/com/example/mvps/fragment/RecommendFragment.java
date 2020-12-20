package com.example.mvps.fragment;

import android.content.Context;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvps.R;
import com.example.mvps.adapter.RecommendAdapter;
import com.example.mvps.adapter.TopicAdapter;
import com.example.mvps.base.BaseFragment;
import com.example.mvps.data.tongpaohome.BannerBean;
import com.example.mvps.data.tongpaohome.Hot_userBase;
import com.example.mvps.data.tongpaohome.PersonalBean;
import com.example.mvps.data.tongpaohome.RecommendBean;
import com.example.mvps.data.tongpaohome.TopicBean;
import com.example.mvps.interfaces.tongpao.TongPoa;
import com.example.mvps.presenter.RecommendPreserenter;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends BaseFragment<RecommendPreserenter> implements TongPoa.View {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerview_talk)
    RecyclerView recyclerviewTalk;
    @BindView(R.id.recyclerview_recommend)
    RecyclerView recyclerviewRecommend;
    @BindView(R.id.recyclerview_hotuser)
    RecyclerView recyclerviewHotuser;
    private TopicAdapter topicAdapter;
    private List<TopicBean.DataBean> listTopic;
    private List<Hot_userBase.DataBean> listHot;
    private RecommendAdapter recommendAdapter;
    private List<RecommendBean.DataBean> listRecommend;


    @Override
    protected RecommendPreserenter createPersenter() {
        return new RecommendPreserenter(this);
    }

    @Override
    protected void initData() {
        RecommendPreserenter recommendPreserenter = new RecommendPreserenter(this);
        recommendPreserenter.getBanner();
        recommendPreserenter.getRecommend();
        recommendPreserenter.getTopic();
        recommendPreserenter.getHot_user();


    }

    @Override
    protected void initView() {

        recyclerviewTalk.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        listTopic = new ArrayList<>();
        topicAdapter = new TopicAdapter(listTopic,getActivity());
        recyclerviewTalk.setAdapter(topicAdapter);

        recyclerviewHotuser.setLayoutManager(new LinearLayoutManager(getActivity()));
        listHot = new ArrayList<>();
        listRecommend = new ArrayList<>();
        recommendAdapter = new RecommendAdapter(listRecommend,getActivity());
        recyclerviewHotuser.setAdapter(recommendAdapter);


    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void getRecommend(RecommendBean recommendBean) {
        RecommendBean.DataBean data = recommendBean.getData();
        listRecommend.add(data);
        recommendAdapter.notifyDataSetChanged();
    }

    @Override
    public void getBanner(BannerBean bannerBean) {
        List<BannerBean.DataBean.ListBean> list = bannerBean.getData().getList();
        banner.setImages(list).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                BannerBean.DataBean.ListBean listBean= (BannerBean.DataBean.ListBean) path;
                Glide.with(context).load(listBean.getBanner()).apply(new RequestOptions().transform(new RoundedCorners(20))).into(imageView);
            }
        }).start();
    }

    @Override
    public void getTopic(TopicBean topicBean) {
        List<TopicBean.DataBean> data = topicBean.getData();
        listTopic.addAll(data);
        topicAdapter.notifyDataSetChanged();
    }

    @Override
    public void getHot_user(Hot_userBase hot_userBase) {
//        List<Hot_userBase.DataBean> data = hot_userBase.getData();
//        listHot.addAll(data);
//        hotAdapter.notifyDataSetChanged();
    }

    @Override
    public void getPersonal(PersonalBean personalBean) {

    }

    @Override
    public void fila(String error) {

    }
}
