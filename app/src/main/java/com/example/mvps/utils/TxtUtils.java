package com.example.mvps.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class TxtUtils {

    public static void setPhotoView(Context context, PhotoView photoView, String image) {
        if (photoView != null && !TextUtils.isEmpty(image)) {
            Glide.with(context).load(image)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(photoView);
        }
    }

    public static void setImageView(ImageView imageView, String image) {
        if (imageView != null && !TextUtils.isEmpty(image)) {
            Glide.with(imageView).load(image).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(imageView);
        }
    }

    public static void setImageView(ImageView imageView, int image) {
        if (imageView != null) {
            Glide.with(imageView).load(image).into(imageView);
        }
    }

    public static void setTextView(TextView textView, String word) {
        if (textView != null && !TextUtils.isEmpty(word)) {
            textView.setText(word);
        }
    }
    public static void setTextView(TextView textView, int number) {
        if (textView != null ) {
            textView.setText(number+"");
        }
    }

    public static void setTextView(TextView textView, String word, Boolean bool) {
        if (textView != null && !TextUtils.isEmpty(word)) {
            int color = Color.parseColor("#FF945EF4");
            int i = word.indexOf("#");
            int i1 = word.lastIndexOf("抽");
            int i2 = word.indexOf("@");
            int i3 = word.lastIndexOf(" ");
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder(word);
            stringBuilder.setSpan(new ForegroundColorSpan(color), i, i1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            stringBuilder.setSpan(new ForegroundColorSpan(color), i2, i3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(stringBuilder);
        }
    }

    public static int setBannerView(Banner banner, List<String> bannerBanner, List<String> listTitle) {
        final int[] _position = {0};
        if (banner != null && bannerBanner.size() > 0 && listTitle.size() > 0 && bannerBanner.size() == listTitle.size()) {

            banner.setImages(bannerBanner).setBannerTitles(listTitle).setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setBannerAnimation(Transformer.CubeIn)
                    .setDelayTime(3000)
                    .setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            _position[0] = position;
                        }
                    })
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context).load(path).apply(RequestOptions.bitmapTransform(new RoundedCorners(20))).into(imageView);
                        }
                    }).start();
            return _position[0];
        }
        return -1;
    }


}