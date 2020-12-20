package com.example.mvps.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.data.tongpaodiscover.HotBean;

import org.w3c.dom.Text;

import java.util.List;


public class HotAdapter extends BaseAdapter<HotBean.DataBean> {

    private final List list;
    private final Context context;

    public HotAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
        this.context = context;
    }

    @Override
    protected int getLagout() {
        return R.layout.item_discover_hot;
    }

    @Override
    protected void bindData(HotBean.DataBean dataBean, VH holder) {
        ImageView imageView = (ImageView) holder.getViewById(R.id.img_icon);
        TextView name = (TextView) holder.getViewById(R.id.txt_name);
        TextView pop = (TextView) holder.getViewById(R.id.txt_pop);
        TextView tag = (TextView) holder.getViewById(R.id.txt_tag);
        Glide.with(context).load(dataBean.getCover()).apply(new RequestOptions().transform(new RoundedCorners(10))).into(imageView);
        name.setText(dataBean.getTitle());
        pop.setText(dataBean.getLocation());
        tag.setText(dataBean.getApplyCutOffTime());
    }
}
