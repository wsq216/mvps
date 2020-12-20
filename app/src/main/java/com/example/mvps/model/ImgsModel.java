package com.example.mvps.model;

import com.example.mvps.base.BaseModel;
import com.example.mvps.interfaces.Callback;
import com.example.mvps.interfaces.tongpao.IDown;
import com.example.mvps.net.CommonSubscriber;
import com.example.mvps.net.HttpmManager;
import com.example.mvps.utils.ImageLoader;
import com.example.mvps.utils.RxUtils;

import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class ImgsModel extends BaseModel implements IDown.Model {
    @Override
    public void downImage(String url, Callback callback) {
        String[] arr = ImageLoader.splitUrl(url);
        String baseUrl = arr[0];
        String imgName = arr[1];
        String path = arr[2];

        Disposable disposable= HttpmManager.getHttpmManager().getBig(baseUrl)
                .downImage(url)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<ResponseBody>(callback) {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        InputStream inputStream = responseBody.byteStream();

                        try {
                            if (inputStream.available()>0) {
                                FileOutputStream fileOutputStream = new FileOutputStream(path);
                                byte[] bytes = new byte[1024 * 10];
                                int len=0;
                                while ((len=inputStream.read(bytes))!=-1){
                                    fileOutputStream.write(bytes,0,len);
                                }
                                fileOutputStream.close();
                            }
                            inputStream.close();
                            callback.success("下载成功");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            callback.success(path);
                        }
                    }
                });
        addDisposable(disposable);

    }
}
