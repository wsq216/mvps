package com.example.mvps.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.data.tongpaohome.RecommendBean;
import com.example.mvps.expandableTextView.ExpandableTextView;
import com.example.mvps.ui.BigActivity;
import com.example.mvps.ui.MyActivity;
import com.example.mvps.utils.DateUtils;
import com.example.mvps.utils.TxtUtils;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends BaseAdapter {


    private final List list;
    private final Context context;
    private ArrayList<String> imgs;
    private ImageView img;

    public RecommendAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
        this.context = context;
    }

    @Override
    protected int getLagout() {
        return R.layout.item_hot;
    }

    @Override
    protected void bindData(Object o, VH holder) {
        RecommendBean.DataBean dataBean= (RecommendBean.DataBean) o;
        RecommendBean.DataBean.PostDetailBean postDetail = dataBean.getPostDetail();
        img = (ImageView) holder.getViewById(R.id.img_icon);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyActivity.class);
                context.startActivity(intent);
            }
        });

        TextView name = (TextView) holder.getViewById(R.id.txt_name);
        ExpandableTextView expandableTextView = (ExpandableTextView) holder.getViewById(R.id.txt_text);
        TextView time = (TextView) holder.getViewById(R.id.txt_time);
        NineGridImageView<String> nineGridImageView = new NineGridImageView<String>(this.context,null);
        NineGridImageView<String> nineGrid = (NineGridImageView) holder.getViewById(R.id.nineGrid);
//        ImageLoader.loadImage(postDetail.getHeadUrl(),img);
        Glide.with(context).load(postDetail.getHeadUrl()).apply(new RequestOptions().circleCrop()).into(img);
        name.setText(postDetail.getNickName());

        String createTime = dataBean.getPostDetail().getCreateTime();
        Long dateToTime = DateUtils.getDateToTime(createTime, null);
        String standardDate = DateUtils.getStandardDate(dateToTime);
        TxtUtils.setTextView(time,standardDate);

        String content = postDetail.getContent();

        int i1 = content.indexOf("@");
        int i2 = content.indexOf(" ", i1);

        int i = content.indexOf("#");
        int indexOf = content.indexOf("#", i + 1)+1;

        int indexOf1 = content.indexOf("#", indexOf + 1);
        int indexOf2 = content.indexOf("#", indexOf1 + 1)+1;


        SpannableStringBuilder spannableString = new SpannableStringBuilder(content);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLUE);
        spannableString.setSpan(colorSpan,i1,i2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        ForegroundColorSpan colorSpans = new ForegroundColorSpan(Color.BLUE);
        spannableString.setSpan(colorSpans,i,indexOf, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        ForegroundColorSpan colorSpanss = new ForegroundColorSpan(Color.BLUE);
        spannableString.setSpan(colorSpanss,indexOf1,indexOf2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Log.i("TAG", "onClick: ");
            }
        };
        ClickableSpan clickableSpans = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Log.i("TAG", "onClicks: ");
            }
        };
        spannableString.setSpan(clickableSpan,i1,i2,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(clickableSpans,i,indexOf,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(clickableSpan,indexOf1,indexOf2,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        expandableTextView.setMovementMethod(LinkMovementMethod.getInstance());
        TxtUtils.setTextView(expandableTextView,spannableString);

        imgs = new ArrayList<>();
        List<RecommendBean.DataBean.PostDetailBean.ImagesBean> images = dataBean.getPostDetail().getImages();
        for (RecommendBean.DataBean.PostDetailBean.ImagesBean imagesBean:images) {
            imgs.add(imagesBean.getFilePath());
        }

        nineGrid.setAdapter(nineGridImageViewAdapter);

        nineGrid.setImagesData(imgs);

//        int viewWidth = getWindowManager().getDefaultDisplay().getWidth() - dp2px(this, 20f);
        expandableTextView.initWidth(500);
        expandableTextView.setMaxLines(6);
        expandableTextView.setHasAnimation(true);
        expandableTextView.setCloseInNewLine(true);
        expandableTextView.setOpenSuffixColor(context.getResources().getColor(R.color.colorAccent));
        expandableTextView.setCloseSuffixColor(context.getResources().getColor(R.color.colorAccent));
        expandableTextView.setOriginalText(spannableString);

    }

    private NineGridImageViewAdapter<String> nineGridImageViewAdapter=new NineGridImageViewAdapter<String>() {
        private Context context;

        @Override
        protected void onItemImageClick(Context context, int index, List list) {
            super.onItemImageClick(context, index, list);
            Toast.makeText(context, index+"", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, BigActivity.class);
            intent.putExtra("id",index);
            intent.putExtra("data", imgs);
            context.startActivity(intent);
        }

        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            Glide.with(context).load(s).into(imageView);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }
    };


}
