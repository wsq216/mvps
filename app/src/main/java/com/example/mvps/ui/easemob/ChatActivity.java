package com.example.mvps.ui.easemob;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvps.R;
import com.example.mvps.app.Constants;
import com.example.mvps.data.EMUserInfo;
import com.example.mvps.utils.SpUtils;
import com.example.mvps.utils.UserInfoManager;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.recy_chat)
    RecyclerView recyChat;
    @BindView(R.id.input_word)
    EditText inputWord;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.image)
    ImageView image;
    private String user;
    private String selfId;
    private List<EMMessage> list;
    private ChatsAdapter chatAdapter;
    private Uri data1;
    private ChatAdapter chatsAdapter;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        initData();
        initMsgListner();

    }

    private void initData() {
        user = getIntent().getStringExtra("user");
        nickname = getIntent().getStringExtra("nickname");

//        Toast.makeText(this, user, Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(user)) {
            return;
        }

        if (TextUtils.isEmpty(nickname)){
            txtTitle.setText(user);

        }else{
            txtTitle.setText(nickname);
        }


        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(user);

        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
//        List<EMMessage> messagess = conversation.loadMoreMsgFromDB(startMsgId, pagesize);

        selfId = EMClient.getInstance().getCurrentUser();
        list = new ArrayList<>();

        if (conversation!=null) {
            List<EMMessage> messages = conversation.getAllMessages();
            if (messages!=null){
                list.addAll(messages);
            }
        }


        recyChat.setLayoutManager(new LinearLayoutManager(this));

//        chatAdapter = new ChatsAdapter(this, list, user, selfId);
        chatsAdapter = new ChatAdapter(this, list, selfId);

        recyChat.setAdapter(chatsAdapter);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });

    }


    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        String content = inputWord.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, user);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

        list.add(message);
//        chatAdapter.notifyDataSetChanged();
        chatsAdapter.notifyDataSetChanged();

        inputWord.setText("");

    }


    private void initMsgListner() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            if (messages != null) {
                list.addAll(messages);
                recyChat.post(new Runnable() {
                    @Override
                    public void run() {
//                        chatAdapter.notifyDataSetChanged();
                        chatsAdapter.notifyDataSetChanged();

                    }
                });
            }

//            if (user.equals(messages.get(0).getFrom())) {
//                //好友
//                messages.get(0).getBody();
//                if (messages.get(0).getType() == EMMessage.Type.TXT) {
//                    EMTextMessageBody textMessageBody = (EMTextMessageBody) messages.get(0).getBody();
//                    textMessageBody.getMessage();
//                } else if (messages.get(0).getType() == EMMessage.Type.LOCATION) {
//                    //定位销
//                }else if (messages.get(0).getType()==EMMessage.Type.IMAGE){
//                    //消息状态变动
//                    EMImageMessageBody imgBody = (EMImageMessageBody) messages.get(0).getBody();
//                    //获取图片文件在服务器的路径
//                    String imgRemoteUrl = imgBody.getRemoteUrl();
//                    //获取图片缩略图在服务器的路径
//                    String thumbnailUrl = imgBody.getThumbnailUrl();
//                    //本地图片文件的资源路径
//                    Uri imgLocalUri = imgBody.getLocalUri();
//                    //本地图片缩略图资源路径
//                    Uri thumbnailLocalUri = imgBody.thumbnailLocalUri();
//                }
//            }

        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息

            //收到透传消息
            for(EMMessage item:messages){
                if(item.getType() == EMMessage.Type.CMD){
                    EMCmdMessageBody msg = (EMCmdMessageBody) item.getBody();
                    if(Constants.ACTION_UPDATEHEADER.equals(msg.action())){
                        //刷新界面更新用户头像
                        String action = msg.action();
                        if(!TextUtils.isEmpty(action)){
                            String uid = item.getFrom();
                            SpUtils.getInstance().setValue(uid,action);
                            EMUserInfo user = UserInfoManager.getInstance().getUserInfoByUid(uid);
                            if(user != null){
                                user.setHeader(action);
                            }
                        }

                    }else if(Constants.ACTION_UPDATENICKNAME.equals(msg.action())){
                        //刷新界面更新用户昵称

                    }
                }
            }
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            Toast.makeText(ChatActivity.this, "onMessageDelivered", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==RESULT_OK){
            data1 = data.getData();
            image.setImageURI(data1);
            //imageUri为图片本地资源标志符，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
            EMMessage message = EMMessage.createImageSendMessage(data1, true, user);
//            message.setChatType(EMMessage.ChatType.GroupChat);
            EMClient.getInstance().chatManager().sendMessage(message);
            list.add(message);
//            chatAdapter.notifyDataSetChanged();
            chatsAdapter.notifyDataSetChanged();

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}