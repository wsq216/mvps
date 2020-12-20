package com.example.mvps.ui;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvps.R;
import com.example.mvps.base.BaseActivity;
import com.example.mvps.data.tongpaohome.BannerBean;
import com.example.mvps.data.tongpaohome.Hot_userBase;
import com.example.mvps.data.tongpaohome.PersonalBean;
import com.example.mvps.data.tongpaohome.RecommendBean;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.example.mvps.data.tongpaohome.TopicBean;
import com.example.mvps.fragment.PersonalDataFragment;
import com.example.mvps.interfaces.tongpao.TongPoa;
import com.example.mvps.presenter.RecommendPreserenter;
import com.example.mvps.utils.Watermark;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyActivity extends BaseActivity<RecommendPreserenter> implements TongPoa.View {


    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.nickName)
    TextView nickName;
    @BindView(R.id.signature)
    TextView signature;
    @BindView(R.id.rewardnumber)
    TextView rewardnumber;
    @BindView(R.id.articelnumber)
    TextView articelnumber;
    @BindView(R.id.dynamicnumber)
    TextView dynamicnumber;
    @BindView(R.id.headUrl)
    ImageView headUrl;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.pager)
    ViewPager pager;

    @Override
    protected RecommendPreserenter createPersenter() {
        return new RecommendPreserenter(this);
    }

    @Override
    protected void initData() {
        presenter.getPersonal();
    }

    @Override
    protected void initView() {
        List<Fragment> list=new ArrayList<>();
        list.add(new PersonalDataFragment());
        list.add(new PersonalDataFragment());
        list.add(new PersonalDataFragment());
        list.add(new PersonalDataFragment());
        list.add(new PersonalDataFragment());
        pager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
        tab.getTabAt(0).setText("资料");
        tab.getTabAt(1).setText("动态");
        tab.getTabAt(2).setText("活动");
        tab.getTabAt(3).setText("社团");
        tab.getTabAt(4).setText("文章");

        Watermark.getInstance().show( this,"Fantasy BlogDemo" );
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my;
    }

    @Override
    public void getRecommend(RecommendBean recommendBean) {

    }

    @Override
    public void getBanner(BannerBean bannerBean) {

    }

    @Override
    public void getTopic(TopicBean topicBean) {

    }

    @Override
    public void getHot_user(Hot_userBase hot_userBase) {

    }

    @Override
    public void getPersonal(PersonalBean personalBean) {
        if (personalBean != null) {
            PersonalBean.DataBean data = personalBean.getData();

            Glide.with(this).load(data.getHeadUrl()).into(image);
            Glide.with(this).load(data.getHeadUrl()).apply(new RequestOptions().circleCrop()).into(headUrl);
            nickName.setText(data.getNickName());
            signature.setText(data.getSignature());
            rewardnumber.setText(data.getRewardnumber() + "");
            articelnumber.setText(data.getArticelnumber() + "");
            dynamicnumber.setText(data.getDynamicnumber() + "");
        }
    }

    @Override
    public void getVideoReturn(TPVideoBean result) {

    }

    @Override
    public void fila(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}