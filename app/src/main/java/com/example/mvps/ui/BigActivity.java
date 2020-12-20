package com.example.mvps.ui;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.example.mvps.R;
import com.example.mvps.base.BaseActivity;
import com.example.mvps.interfaces.tongpao.BigImgs;
import com.example.mvps.presenter.ImgsPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class BigActivity extends BaseActivity<ImgsPresenter> implements BigImgs.View {


    @BindView(R.id.txt_page)
    TextView txtPage;
    @BindView(R.id.page)
    ViewPager page;
    @BindView(R.id.txt_return)
    TextView txtReturn;
    @BindView(R.id.txt_down)
    TextView txtDown;
    private PhotoView image;
    private ArrayList<String> data;
    private int id;

    @Override
    protected ImgsPresenter createPersenter() {
        return new ImgsPresenter(this);
    }

    @Override
    protected void initData() {
        page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                txtPage.setText((position + 1) + "/" + data.size());
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        page.setCurrentItem(id);
        txtReturn.setOnClickListener(BigActivity.this::onViewClicked);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        data = (ArrayList<String>) intent.getSerializableExtra("data");
        id = intent.getIntExtra("id", -1);
        page.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = LayoutInflater.from(BigActivity.this).inflate(R.layout.item_big, null);
                image = view.findViewById(R.id.image);
                Glide.with(BigActivity.this).load(data.get(position)).into(image);
                image.disenable();
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                super.destroyItem(container, position, object);
                container.removeView((View) object);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_big;
    }

    @Override
    public void fila(String error) {

    }

    @OnClick({R.id.txt_return, R.id.txt_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_return:
                finish();
                break;
            case R.id.txt_down:
                downImg();
                break;
        }
    }

    private void downImg() {
        String url=data.get(id);
        presenter.getBing(url);
    }

    @Override
    public void getBing(String string) {
        Log.d("TAG", "getBing: "+string);
    }
}
