package com.example.mvps.ui.easemob;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.example.mvps.R;
import com.example.mvps.utils.GlideEngine;
import com.example.mvps.utils.SpUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;


import java.util.List;

public class UserDetailActivity extends AppCompatActivity {

    String bucketName = "2002a";
    String ossPoint = "http://oss-cn-beijing.aliyuncs.com";

    String key = "LTAI4GH6Gy8tFbbXJ3vatsAn";  //appkey
    String secret = "YjWyqsTnHX8336jRZ1vg7FSWaojPf8";  //密码

    String username;
    ImageView imghead;

    private OSS ossClient;
    private TextView name;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        imghead = findViewById(R.id.img_head);
        name = findViewById(R.id.name);
        btn = findViewById(R.id.btn);
        initData();
        initView();
        initOss();
        imghead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhoto();
            }
        });
    }

    private void initView() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String na = name.getText().toString();
                SpUtils.getInstance().setValue("name"+username,na);
                Toast.makeText(UserDetailActivity.this, "成功", Toast.LENGTH_SHORT).show();
                sendCmdMessage();
            }
        });
    }

    private void initOss(){
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(key, secret, "");
        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。
        ossClient = new OSSClient(getApplicationContext(), ossPoint, credentialProvider);
    }

    private void initData(){
        Intent intent = getIntent();
        if(intent.hasExtra("username")){
            username = intent.getStringExtra("username");
        }

        String head = SpUtils.getInstance().getString(username);
        if(!TextUtils.isEmpty(head)){
            Glide.with(imghead).load(head).into(imghead);
        }
    }

    private void openPhoto(){
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                .maxSelectNum(1)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // onResult Callback
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() == 0) return;
                //获取本地图片的选择地址，上传到服务器
                //头像的压缩和二次采样
                //把选中的图片插入到列表
                for(LocalMedia item:selectList){
                    uploadHead(item.getPath());
                }
                break;
            default:
                break;
        }
    }

    /**
     * oss上传
     * @param path
     */
    private void uploadHead(String path){

        String fileName = path.substring(path.lastIndexOf("/")+1,path.length());
        PutObjectRequest put = new PutObjectRequest(bucketName, fileName, path);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                //上次进度
                Log.i("oss_upload",currentSize+"/"+totalSize);
                // 进度百分比的计算
                // int p = (int) (currentSize/totalSize*100);
                if(currentSize == totalSize){
                    //完成
                    String headUrl = request.getUploadFilePath();
                    //
                    Log.i("HeadUrl",headUrl);
                    //request.getUploadFilePath()
                }

            }
        });
        OSSAsyncTask task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                //成功的回调中读取相关的上传文件的信息  生成一个url地址
                String url = ossClient.presignPublicObjectURL(request.getBucketName(),request.getObjectKey());
                //调用服务器接口 把url上传到服务器的接口
                SpUtils.getInstance().setValue(username,url);

                updateHead(url);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    private void sendCmdMessage() {
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        String action = "updateUserDetail";//action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);

        //这里的获取好友列表 一定要在子线程中运行， 否则会报303:unknown server error，百度的
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取所有好友  要给所有好友发送透传消息，更换用户信息
                    List<String> allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    for (String uid : allContactsFromServer) {
                        cmdMsg.setTo(uid);
                        cmdMsg.addBody(cmdBody);
                        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("TAG", "sendCmdMessage: " + e);
                }
            }
        }).start();
    }

    private void updateHead(String url){
        imghead.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(imghead).load(url).into(imghead);
            }
        });
    }

}