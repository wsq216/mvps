package com.example.mvps.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.example.mvps.utils.TxtUtilss;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

public class VideoAdapter extends BaseAdapter {

    private final Context context;
    private StandardGSYVideoPlayer mGs;

    public VideoAdapter(Context context, List mData) {
        super(mData, context);
        this.context = context;
    }


    @Override
    protected int getLagout() {
        return R.layout.adapter_video_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        TPVideoBean.DataBean.ListBean bean = (TPVideoBean.DataBean.ListBean) data;
        ImageView frame = (ImageView) vh.getViewById(R.id.void_frame);
        TextView title = (TextView) vh.getViewById(R.id.void_title);
        ImageView head = (ImageView) vh.getViewById(R.id.video_head);
        TextView name = (TextView) vh.getViewById(R.id.video_name);
        RadioButton love = (RadioButton) vh.getViewById(R.id.video_love);
        TextView count = (TextView) vh.getViewById(R.id.video_count);


        Glide.with(context).load(bean.getCover()).apply(new RequestOptions().circleCrop()).into(frame);
        TxtUtilss.setTextView(title,bean.getContent());
        Glide.with(context).load(bean.getHeadUrl()).apply(new RequestOptions().circleCrop()).into(head);
        TxtUtilss.setTextView(name,bean.getNickName());
        count.setText(bean.getLikeNumber()+"");
    }
}
