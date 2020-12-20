package com.example.mvps.ui.easemob;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;
import com.example.mvps.data.EMUserInfo;
import com.example.mvps.utils.SpUtils;
import com.example.mvps.utils.UserInfoManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;


import java.util.List;

public class ChatAdapter extends BaseAdapter<EMMessage> {

    private List list;
    private final String selfId;
    private final Context context;


    public ChatAdapter(Context context, List list, String selfId) {
        super(list, context);

        this.context = context;
        this.list = list;
        this.selfId = selfId;
    }


    /**
     * 由于基类没有处理多布局
     * @return
     */
    @Override
    protected int getLagout() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage msg = (EMMessage) getData().get(position);
        if(selfId.equals(msg.getFrom())){
            return 2;
        }else{
            return 1;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 1){
            view = LayoutInflater.from(context).inflate(R.layout.item_easemob_left,parent,false);
        }else if(viewType == 2){
            view = LayoutInflater.from(context).inflate(R.layout.item_easemob_right,parent,false);
        }
        VH vh = new VH(view);

        return vh;
    }

    @Override
    protected void bindData(EMMessage emMessage, VH vh) {
        TextView txtWord = (TextView) vh.getViewById(R.id.text);
        ImageView imgIcon = (ImageView) vh.getViewById(R.id.img);
        ImageView imgHeader = (ImageView) vh.getViewById(R.id.imgs);
        if(selfId.equals(emMessage.getFrom())){
            String header = SpUtils.getInstance().getString(selfId);
            if(!TextUtils.isEmpty(header)){
//                Glide.with(context).load(header).into(imgIcon);
                Glide.with(context).load(header).apply(new RequestOptions().circleCrop()).into(imgIcon);
            }
        }else{
            String from = emMessage.getFrom();
            EMUserInfo user = UserInfoManager.getInstance().getUserInfoByUid(from);
            if(user != null){
                if(!TextUtils.isEmpty(user.getHeader())){
                    Glide.with(context).load(user.getHeader()).into(imgIcon);
                }
            }
        }



        if(emMessage.getType() == EMMessage.Type.TXT){
            txtWord.setVisibility(View.VISIBLE);
            imgHeader.setVisibility(View.GONE);
            EMTextMessageBody textMessageBody = (EMTextMessageBody) emMessage.getBody();
            txtWord.setText(textMessageBody.getMessage());
        }else if(emMessage.getType() == EMMessage.Type.IMAGE){
            txtWord.setVisibility(View.GONE);
            imgHeader.setVisibility(View.VISIBLE);
            EMImageMessageBody imageMessageBody = (EMImageMessageBody) emMessage.getBody();
            String path = imageMessageBody.getRemoteUrl();
            Glide.with(context).load(path).into(imgHeader);
        }
    }
//
//
//    @Override
//    protected void bindData(Object data, VH vh) {
//        EMMessage msg = (EMMessage) data;
//        TextView txtWord = (TextView) vh.getViewById(R.id.txt_word);
//        ImageView imgIcon = (ImageView) vh.getViewById(R.id.img_icon);
//        ImageView imgHeader = (ImageView) vh.getViewById(R.id.img_header);
//        if(selfUid.equals(msg.getFrom())){
//            String header = SpUtils.getInstance().getString(selfUid);
//            if(!TextUtils.isEmpty(header)){
//                Glide.with(imgHeader).load(header).into(imgHeader);
//            }
//        }else{
//            EMUserInfo user = UserInfoManager.getInstance().getUserInfoByUid(msg.getFrom());
//            if(user != null){
//                if(!TextUtils.isEmpty(user.getHeader())){
//                    Glide.with(imgHeader).load(user.getHeader()).into(imgHeader);
//                }
//            }
//        }
//
//        if(msg.getType() == EMMessage.Type.TXT){
//            txtWord.setVisibility(View.VISIBLE);
//            imgIcon.setVisibility(View.GONE);
//            EMTextMessageBody textMessageBody = (EMTextMessageBody) msg.getBody();
//            txtWord.setText(textMessageBody.getMessage());
//        }else if(msg.getType() == EMMessage.Type.IMAGE){
//            txtWord.setVisibility(View.GONE);
//            imgIcon.setVisibility(View.VISIBLE);
//            EMImageMessageBody imageMessageBody = (EMImageMessageBody) msg.getBody();
//            String path = imageMessageBody.getThumbnailUrl();
//            Glide.with(imgIcon).load(path).into(imgIcon);
//        }
//    }
//
//
//    /**
//     * 如果消息是自己发送的 1   消息是其他人的 2
//     * @param position
//     * @return
//     */

}
