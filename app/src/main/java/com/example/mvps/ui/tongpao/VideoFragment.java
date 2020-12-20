package com.example.mvps.ui.tongpao;

import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.mvps.R;
import com.example.mvps.base.BaseFragment;
import com.example.mvps.data.tongpaohome.BannerBean;
import com.example.mvps.data.tongpaohome.Hot_userBase;
import com.example.mvps.data.tongpaohome.PersonalBean;
import com.example.mvps.data.tongpaohome.RecommendBean;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.example.mvps.data.tongpaohome.TopicBean;
import com.example.mvps.interfaces.tongpao.TongPoa;
import com.example.mvps.presenter.RecommendPreserenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoFragment extends BaseFragment<RecommendPreserenter> implements TongPoa.View {

    @BindView(R.id.RB_hotspot)
    RadioButton RBHotspot;
    @BindView(R.id.RB_find)
    RadioButton RBFind;
    @BindView(R.id.mFrame_void)
    FrameLayout mFrameVoid;
    @BindView(R.id.Rg)
    RadioGroup Rg;

    List<TPVideoBean.DataBean.ListBean> listVideo = new ArrayList<>();
    private VideoItemHotFragment hotFragment;
    private VideoItemFindFragment findFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager manager;

    @Override
    public int getLayout() {
        return R.layout.fragment_void;
    }

    @Override
    public void initView() {
        hotFragment = new VideoItemHotFragment();
        findFragment = new VideoItemFindFragment();
    }

    @Override
    public RecommendPreserenter createPersenter() {
        return new RecommendPreserenter(this);
    }

    @Override
    public void initData() {
        presenter.getVideo();

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

    }

    @Override
    public void getVideoReturn(TPVideoBean result) {
        if (result.getData().getList().size() > 0) {
            listVideo.addAll(result.getData().getList());

            hotFragment.setList(listVideo);
            findFragment.setList(listVideo);
        }
        initFragment();

    }

    private void initFragment() {

        manager = getActivity().getSupportFragmentManager();
        fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.mFrame_void, hotFragment)
                .add(R.id.mFrame_void, findFragment)
                .hide(findFragment).commit();
        //按钮的监听与fragment结合
        Rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentTransaction transaction = manager.beginTransaction();
                switch (i) {
                    case R.id.RB_hotspot:
                        transaction.show(hotFragment).hide(findFragment);
                        break;
                    case R.id.RB_find:
                        transaction.show(findFragment).hide(hotFragment);
                        break;
                }
                transaction.commit();
            }
        });
    }

    @Override
    public void fila(String error) {

    }
}
