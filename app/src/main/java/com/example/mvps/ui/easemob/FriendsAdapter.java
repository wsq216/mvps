package com.example.mvps.ui.easemob;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.data.EMUserInfo;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.List;

public class FriendsAdapter extends BaseAdapter<EMUserInfo> {

    private final List list;
    private final Context context;

    public FriendsAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
        this.context = context;
    }

    @Override
    protected int getLagout() {
        return R.layout.layout_friend_item;
    }

    @Override
    protected void bindData(EMUserInfo data, VH holder) {
        TextView txtUserName = (TextView) holder.getViewById(R.id.txt_username);
        TextView sum = (TextView) holder.getViewById(R.id.sum);

        if(!TextUtils.isEmpty(data.getNickname())){
            txtUserName.setText(data.getNickname());
        }else{
            txtUserName.setText(data.getUid());
        }

        ImageView imgheader = (ImageView) holder.getViewById(R.id.img_header);
        String header = data.getHeader();
        if(!TextUtils.isEmpty(header)){
            Glide.with(imgheader).load(header).into(imgheader);
        }

        //获取未读消息数量
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(data.getUid());
        if (conversation!=null){
            int unreadMsgCount = conversation.getUnreadMsgCount();
        if (unreadMsgCount!=0){
            sum.setText(unreadMsgCount+"");
            }else{
            sum.setText("0");

        }
        }else {
            sum.setText("");
        }

        Button btn = (Button) holder.getViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iItemViewClick != null){
                    iItemViewClick.itemViewClick(v.getId(),data.getUid());
                }

            }
        });
    }

}
