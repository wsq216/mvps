package com.example.mvps.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvps.interfaces.BeanView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BeanView {

    protected P presenter;
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        initView();
        presenter = createPersenter();
        if (presenter!=null){
            presenter.attachView(this);
        }

        initData();

    }

    protected abstract P createPersenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayout();

    /**
     * 界面销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null){
            unbinder.unbind();
        }
        //释放p关联的v的引用
        if(presenter != null){
            presenter.unAttachView();
        }
    }
}
