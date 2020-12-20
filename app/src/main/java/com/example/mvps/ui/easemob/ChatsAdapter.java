package com.example.mvps.ui.easemob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvps.R;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import org.w3c.dom.Text;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final List<EMMessage> list;
    private final String user;
    private final String selfId;

    private final int ITEM_LEFT=0;
    private final int ITEM_RIGHT=1;

    public ChatsAdapter(Context context, List<EMMessage> list, String user, String selfId) {
        this.context = context;
        this.list = list;
        this.user = user;
        this.selfId = selfId;
    }

    @Override
    public int getItemViewType(int position) {
        String from = list.get(position).getFrom();
        if (from.equals(selfId)){
            return ITEM_RIGHT;
        }else{
            return ITEM_LEFT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_RIGHT){
            return new RightViewHolder(LayoutInflater.from(context).inflate(R.layout.item_easemob_right,parent,false));
        }else {
            return new LeftViewHolder(LayoutInflater.from(context).inflate(R.layout.item_easemob_left,parent,false));
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        EMMessage emMessage = list.get(position);

        switch (itemViewType){
            case ITEM_LEFT:
                LeftViewHolder leftViewHolder= (LeftViewHolder) holder;
                Glide.with(context).load(R.drawable.s1).apply(new RequestOptions().circleCrop()).into(leftViewHolder.img);
                if (emMessage.getType()==EMMessage.Type.TXT){
                    EMTextMessageBody textMessageBody = (EMTextMessageBody) emMessage.getBody();

                    leftViewHolder.imgs.setVisibility(View.GONE);
                    leftViewHolder.text.setVisibility(View.VISIBLE);
                    leftViewHolder.text.setText(textMessageBody.getMessage());
                }else {
                    EMImageMessageBody imageMessageBody = (EMImageMessageBody) emMessage.getBody();
                    leftViewHolder.imgs.setVisibility(View.VISIBLE);
                    leftViewHolder.text.setVisibility(View.GONE);
                    String path = imageMessageBody.getRemoteUrl();
                    Glide.with(context).load(path).into(leftViewHolder.imgs);
                }
                break;
            case ITEM_RIGHT:
                RightViewHolder rightViewHolder= (RightViewHolder) holder;
                Glide.with(context).load(R.drawable.s1).apply(new RequestOptions().circleCrop()).into(rightViewHolder.img);
                if (emMessage.getType()==EMMessage.Type.TXT){
                    EMTextMessageBody textMessageBody = (EMTextMessageBody) emMessage.getBody();
                    rightViewHolder.imgs.setVisibility(View.GONE);
                    rightViewHolder.text.setVisibility(View.VISIBLE);
                    rightViewHolder.text.setText(textMessageBody.getMessage());
                }else if (emMessage.getType()==EMMessage.Type.IMAGE){
                    EMImageMessageBody imageMessageBody = (EMImageMessageBody) emMessage.getBody();
                    rightViewHolder.imgs.setVisibility(View.VISIBLE);
                    rightViewHolder.text.setVisibility(View.GONE);
                    String path = imageMessageBody.getRemoteUrl();
                    Glide.with(context).load(path).into(rightViewHolder.imgs);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class LeftViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final ImageView imgs;
        private final TextView text;

        public LeftViewHolder(View inflate) {
            super(inflate);
            img = inflate.findViewById(R.id.img);
            imgs = inflate.findViewById(R.id.imgs);
            text = inflate.findViewById(R.id.text);
        }
    }

    private class RightViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img;
        private final ImageView imgs;
        private final TextView text;

        public RightViewHolder(View inflate) {
            super(inflate);
            img = inflate.findViewById(R.id.img);
            imgs = inflate.findViewById(R.id.imgs);
            text = inflate.findViewById(R.id.text);
        }
    }
}
