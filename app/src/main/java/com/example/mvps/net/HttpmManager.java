package com.example.mvps.net;

import com.example.mvps.api.DiscoverApi;
import com.example.mvps.api.ImageApi;
import com.example.mvps.api.IndexApi;
import com.example.mvps.api.ServiceApi;
import com.example.mvps.api.TongPaoApi;
import com.umeng.commonsdk.debug.D;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpmManager {
    private static HttpmManager httpmManager;

    public static HttpmManager getHttpmManager() {
        if (httpmManager==null){
            synchronized (HttpmManager.class){
                if (httpmManager==null){
                    httpmManager = new HttpmManager();
                }
            }
        }
        return httpmManager;
    }


    private ServiceApi serviceApi;

    private TongPaoApi tongPaoApi;

    private IndexApi indexApi;

    private ImageApi imageApi;

    private DiscoverApi discoverApi;

    private Map<String,Retrofit> map = new HashMap<>();  //retrofit请求对象的对象池


    public Retrofit getRetrofit(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOk())
                .build();

    }


    private OkHttpClient getOk() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();
    }

    public ServiceApi getApi(){
        if (serviceApi==null){
            serviceApi= getRetrofit(ServiceApi.BASE_URL).create(ServiceApi.class);
        }
        return serviceApi;
    }

    public TongPaoApi getRecommend(){
        if (tongPaoApi==null){
            tongPaoApi= getRetrofit(TongPaoApi.BASE_URL).create(TongPaoApi.class);
        }
        return tongPaoApi;
    }

    public DiscoverApi getDiscover(){
        if (discoverApi==null){
            discoverApi= getRetrofit(DiscoverApi.BASE_URL).create(DiscoverApi.class);
        }
        return discoverApi;
    }

    public IndexApi getIndex(){
        if (indexApi==null){
            indexApi=getRetrofit(IndexApi.indexApi).create(IndexApi.class);
        }
        return indexApi;
    }

    public ImageApi getBig(String url){
        Retrofit retrofit=map.get(url);
        if (retrofit!=null){
            imageApi=retrofit.create(ImageApi.class);
        }else{
            retrofit=getRetrofit(url);
            imageApi=retrofit.create(ImageApi.class);
            map.put(url,retrofit);
        }
        return imageApi;
    }


    private class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request build = chain.request().newBuilder()
                    .addHeader("Authorization","APPCODE 964e16aa1ae944e9828e87b8b9fbd30a")
                    .build();

            return chain.proceed(build);
        }
    }

}
