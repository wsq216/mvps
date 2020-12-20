package com.example.mvps.fragment;

import android.widget.TextView;

import com.example.mvps.R;
import com.example.mvps.base.BaseFragment;
import com.example.mvps.data.tongpaohome.BannerBean;
import com.example.mvps.data.tongpaohome.Hot_userBase;
import com.example.mvps.data.tongpaohome.PersonalBean;
import com.example.mvps.data.tongpaohome.RecommendBean;
import com.example.mvps.data.tongpaohome.TopicBean;
import com.example.mvps.interfaces.tongpao.TongPoa;
import com.example.mvps.presenter.RecommendPreserenter;

import butterknife.BindView;

public class PersonalDataFragment extends BaseFragment<RecommendPreserenter> implements TongPoa.View {

    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.city)
    TextView city;

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

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_personal_data;
    }

    @Override
    public void fila(String error) {

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
        if (personalBean!=null) {
            PersonalBean.DataBean data = personalBean.getData();
            sex.setText("性别：男");
            birthday.setText("生日："+data.getBirthday()+"\t星座："+"");
            city.setText("所在地区："+data.getCity());
        }else{
            return;
        }
    }
}