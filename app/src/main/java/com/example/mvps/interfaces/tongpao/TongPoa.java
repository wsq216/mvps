package com.example.mvps.interfaces.tongpao;

import com.example.mvps.data.tongpaohome.BannerBean;
import com.example.mvps.data.tongpaohome.Hot_userBase;
import com.example.mvps.data.tongpaohome.PersonalBean;
import com.example.mvps.data.tongpaohome.RecommendBean;
import com.example.mvps.data.tongpaohome.TPVideoBean;
import com.example.mvps.data.tongpaohome.TopicBean;
import com.example.mvps.interfaces.BasePersenter;
import com.example.mvps.interfaces.BeanView;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.IModel;

public interface TongPoa {

    //home业务下的 v层接口
    interface View extends BeanView {
        void getRecommend(RecommendBean recommendBean);
        void getBanner(BannerBean bannerBean);
        void getTopic(TopicBean topicBean);
        void getHot_user(Hot_userBase hot_userBase);
        void getPersonal(PersonalBean personalBean);
        void getVideoReturn(TPVideoBean result);
    }

    //home业务下 P层接口
    interface Presenter  extends BasePersenter<View> {
        void getRecommend();
        void getBanner();
        void getTopic();
        void getHot_user();
        void getPersonal();
        void getVideo();
    }

    //home业务下的model
    interface Model extends IModel {
        void getRecommend(Callback callback);
        void getBanner(Callback callback);
        void getTopic(Callback callback);
        void getHot_user(Callback callback);
        void getPersonal(Callback callback);
        void getVideo(Callback callback);

        //获取天气数据 --> p层调用m层的接口
//        void getWeatcher(Map<String, String> map, Callback callback);
    }

}
