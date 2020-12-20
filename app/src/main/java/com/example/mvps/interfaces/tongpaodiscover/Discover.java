package com.example.mvps.interfaces.tongpaodiscover;

import com.example.mvps.data.index.IndexBean;
import com.example.mvps.data.tongpaodiscover.HotBean;
import com.example.mvps.data.tongpaodiscover.NavigationBean;
import com.example.mvps.data.tongpaodiscover.NewsBean;
import com.example.mvps.interfaces.BasePersenter;
import com.example.mvps.interfaces.BeanView;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.IModel;

public interface Discover {

    //home业务下的 v层接口
    interface View extends BeanView {
        void getHot(HotBean hotBean);
        void getNavigation(NavigationBean navigationBean);
        void getNews(NewsBean newsBean);
    }

    //home业务下 P层接口
    interface Presenter  extends BasePersenter<View> {
        void getHot();
        void getNavigation();
        void getNews(int path);

    }

    //home业务下的model
    interface Model extends IModel {
        void getHot(Callback callback);
        void getNavigation(Callback callback);
        void getNews(Callback callback,int path);

    }

}
