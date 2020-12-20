package com.example.mvps.ui.easemob;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.data.EMUserInfo;
import com.example.mvps.utils.SpUtils;
import com.example.mvps.utils.UserInfoManager;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EaseMobActivity extends AppCompatActivity {

    @BindView(R.id.btn_yes)
    Button btnYes;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_pwd)
    EditText edPwd;
    private List<EMUserInfo> list;
    private FriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ease_mob);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        list = new ArrayList<>();

        rv.setLayoutManager(new LinearLayoutManager(this));

        friendsAdapter = new FriendsAdapter(list, this);

        rv.setAdapter(friendsAdapter);

        friendsAdapter.setiListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                EMUserInfo emUserInfo = list.get(pos);
                String nickname = emUserInfo.getNickname();
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(emUserInfo.getUid());
                if (conversation!=null){
                    //指定会话消息未读数清零
                    conversation.markAllMessagesAsRead();
                }
                Intent intent = new Intent(EaseMobActivity.this, ChatActivity.class);
                intent.putExtra("user", list.get(pos).getUid());
                intent.putExtra("nickname", nickname);
                startActivity(intent);
            }
        });

        friendsAdapter.addItemViewClick(new BaseAdapter.IItemViewClick<String>() {
            @Override
            public void itemViewClick(int viewid, String data) {
                Toast.makeText(EaseMobActivity.this, "" + viewid, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EaseMobActivity.this, UserDetailActivity.class);
                intent.putExtra("username", data);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btn_yes)
    public void onViewClicked() {

        String userName = edName.getText().toString();
        String password = edPwd.getText().toString();
        Log.i("TAG","环信login");
        if (!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(password))
        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                Log.d("main", "登录聊天服务器成功！");
                getFriends();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d("main", "onProgress: " + status);
            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！" + message);
            }
        });
    }

    private void getFriends() {
        try {
            list.clear();
            List<String> friends = EMClient.getInstance().contactManager().getAllContactsFromServer();
            for (String item : friends) {

                EMUserInfo user = new EMUserInfo();
                user.setUid(item);
                String header = SpUtils.getInstance().getString(item);
                if (!TextUtils.isEmpty(header)) {
                    user.setHeader(header);
                }
                String name = SpUtils.getInstance().getString("name" + item);
                if (!TextUtils.isEmpty(name)) {
                    user.setNickname(name);
                }

                list.add(user);
            }
            UserInfoManager.getInstance().addUsers(list);
            if (friends != null) {
                rv.post(new Runnable() {
                    @Override
                    public void run() {
                        friendsAdapter.notifyDataSetChanged();
                    }
                });
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        friendsAdapter.notifyDataSetChanged();

    }


}