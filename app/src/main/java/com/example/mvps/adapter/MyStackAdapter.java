package com.example.mvps.adapter;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;


import com.bumptech.glide.Glide;
import com.example.mvps.R;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

public class MyStackAdapter extends BaseAdapter {

    private Context mContext;
    private List<TPVideoBean.DataBean.ListBean> list;
    private StandardGSYVideoPlayer standard;

    public MyStackAdapter(Context mContext, List<TPVideoBean.DataBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 该方法返回的View代表了每个列表项
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            // 创建一个StandardGSYVideoPlayer
            standard = new StandardGSYVideoPlayer(mContext);
            // 设置StandardGSYVideoPlayer的缩放类型
            standard.setScaleX(StandardGSYVideoPlayer.AUTOFILL_TYPE_TOGGLE);
            standard.setScaleY(StandardGSYVideoPlayer.AUTOFILL_TYPE_TOGGLE);
            // 为StandardGSYVideoPlayer设置布局参数
            standard.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            convertView = standard;
        } else {
            standard = (StandardGSYVideoPlayer) convertView;
        }

        standard.setUp(list.get(position).getVideoPath(),false,list.get(position).getContent());

        //增加封面
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_launcher);
//        TxtUtils.setImageView(imageView,list.get(position).getCover());
        Glide.with(mContext).load(list.get(position).getCover()).into(imageView);
        standard.setThumbImageView(imageView);
        //增加title
        standard.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        standard.getBackButton().setVisibility(View.VISIBLE);
        //是否可以滑动调整
        standard.setIsTouchWiget(true);
        //设置返回按键功能
        standard.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //释放所有
                standard.setVideoAllCallBack(null);
            }
        });
        standard.startPlayLogic();
        return convertView;
    }

    public void onDestroyVideo() {
        GSYVideoManager.releaseAllVideos();//释放所有视频
        standard.setVideoAllCallBack(null);
    }
}
