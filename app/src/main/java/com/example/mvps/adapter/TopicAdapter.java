package com.example.mvps.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.data.tongpaohome.TopicBean;

import java.util.List;

public class TopicAdapter extends BaseAdapter {
    private final List list;
    private final Context context;

    public TopicAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
        this.context = context;
    }

    @Override
    protected int getLagout() {
        return R.layout.item_topic;
    }

    @Override
    protected void bindData(Object o, BaseAdapter.VH holder) {
        TopicBean.DataBean dataBean= (TopicBean.DataBean) o;
        ImageView image = (ImageView) holder.getViewById(R.id.img_icon);
        TextView txt_tag = (TextView) holder.getViewById(R.id.txt_tag);
        TextView txt_name = (TextView) holder.getViewById(R.id.txt_name);
        TextView txt_pop = (TextView) holder.getViewById(R.id.txt_pop);
        Glide.with(context).load(dataBean.getImageUrl()).apply(new RequestOptions().transform(new RoundedCorners(10))).into(image);
        txt_name.setText("#"+dataBean.getName()+"#");
        txt_pop.setText(dataBean.getAttentionNum()+"人参与");

    }
}
