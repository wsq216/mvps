package com.example.mvps.ui.tongpao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvps.R;
import com.example.mvps.data.tongpaodiscover.NewsBean;

import java.util.LinkedList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter {

    private final List<NewsBean.DataBean.ListBean> list;
    private final Context context;
    private int path;

    public NewsAdapter(List<NewsBean.DataBean.ListBean> list, Context context, int path) {

        this.list = list;
        this.context = context;
        this.path = path;
    }

    private final int ITEM_IMGAES = 0;
    private final int ITEM_IMAGE = 1;
    private final int ITEM_TEXT = 2;


    @Override
    public int getItemViewType(int position) {
        if (position%3==0 && path <= 3){
            return ITEM_IMGAES;
        }else if (path<=3 && position%3==1){
            return ITEM_IMAGE;
        }if (path==4 && position==1){
            return ITEM_IMAGE;
        }else  {
            return ITEM_TEXT;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_IMGAES){
            return new ImagesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_discover_imags,parent,false));
        }else if (viewType==ITEM_IMAGE){
            return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_discover_imag,parent,false));
        }else {
            return new TextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_discover_text,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        NewsBean.DataBean.ListBean listBean = list.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new RoundedCorners(10));
        switch (itemViewType){
            case ITEM_IMGAES:
                ImagesViewHolder imagesViewHolder= (ImagesViewHolder) holder;
                imagesViewHolder.createTime.setText(listBean.getCreateTime());
                imagesViewHolder.title.setText(listBean.getTitle());
                List<NewsBean.DataBean.ListBean.FilePathListBean> filePathList = listBean.getFilePathList();
                Glide.with(context).load(filePathList.get(0).getFilePath()).apply(requestOptions).into(imagesViewHolder.filePath1);
                Glide.with(context).load(filePathList.get(1).getFilePath()).apply(requestOptions).into(imagesViewHolder.filePath2);
                Glide.with(context).load(filePathList.get(2).getFilePath()).apply(requestOptions).into(imagesViewHolder.filePath3);
                break;
            case ITEM_IMAGE:
                ImageViewHolder imageViewHolder= (ImageViewHolder) holder;
                imageViewHolder.createTime.setText(listBean.getCreateTime());
                imageViewHolder.title.setText(listBean.getTitle());
                List<NewsBean.DataBean.ListBean.FilePathListBean> filePathLists = listBean.getFilePathList();
                Glide.with(context).load(filePathLists.get(0).getFilePath()).apply(requestOptions).into(imageViewHolder.filePath);
                break;
            case ITEM_TEXT:
                TextViewHolder textViewHolder= (TextViewHolder) holder;
                textViewHolder.createTime.setText(listBean.getCreateTime());
                textViewHolder.title.setText(listBean.getTitle());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ImagesViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final ImageView filePath1;
        private final ImageView filePath2;
        private final ImageView filePath3;
        private final TextView createTime;

        public ImagesViewHolder(View inflate) {
            super(inflate);
            title = inflate.findViewById(R.id.title);
            filePath1 = inflate.findViewById(R.id.filePath1);
            filePath2 = inflate.findViewById(R.id.filePath2);
            filePath3 = inflate.findViewById(R.id.filePath3);
            createTime = inflate.findViewById(R.id.createTime);
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final ImageView filePath;
        private final TextView createTime;

        public ImageViewHolder(View inflate) {
            super(inflate);
            title = inflate.findViewById(R.id.title);
            filePath = inflate.findViewById(R.id.filePath);
            createTime = inflate.findViewById(R.id.createTime);
        }
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView createTime;

        public TextViewHolder(View inflate) {
            super(inflate);
            inflate.findViewById(R.id.title);
            title = inflate.findViewById(R.id.title);
            createTime = inflate.findViewById(R.id.createTime);

        }
    }
}
