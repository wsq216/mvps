package com.example.mvps.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.example.mvps.utils.TxtUtils;
import com.example.mvps.utils.TxtUtilss;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

public class ViewPagerAdapter extends BaseAdapter<TPVideoBean.DataBean.ListBean> {

    private final Context context;
    private StandardGSYVideoPlayer mGs;

    public ViewPagerAdapter(Context context, List<TPVideoBean.DataBean.ListBean> mData) {
        super(mData, context);
        this.context = context;
    }



    @Override
    protected int getLagout() {
        return R.layout.adapter_show_item;
    }

    @Override
    protected void bindData(TPVideoBean.DataBean.ListBean data, VH vh) {
        mGs = (StandardGSYVideoPlayer) vh.getViewById(R.id.mGs);
        ImageView video_stand_head = (ImageView) vh.getViewById(R.id.video_stand_head);
        TextView video_stand_name = (TextView) vh.getViewById(R.id.video_stand_name);
        TextView tv_context = (TextView) vh.getViewById(R.id.tv_context);


        mGs.setUp(data.getVideoPath(),false,data.getContent());

        video_stand_name.setText(data.getNickName());
        TxtUtils.setImageView(video_stand_head,data.getHeadUrl());
        TxtUtils.setTextView(tv_context,data.getContent());
        //增加封面
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_launcher);
        TxtUtils.setImageView(imageView,data.getCover());
        mGs.setThumbImageView(imageView);
    }

    public void onDestroyVideo() {
        GSYVideoManager.releaseAllVideos();//释放所有视频
        mGs.setVideoAllCallBack(null);
    }
}