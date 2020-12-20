package com.example.mvps.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<D> extends RecyclerView.Adapter {

    List<D> list;
    Context context;




    public BaseAdapter(List<D> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(getLagout(),parent,false);
        VH vh = new VH(view);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iListClick!=null){
                    iListClick.itemClick(vh.getLayoutPosition());
                }
            }
        });
        return vh;
    }

    protected abstract int getLagout();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindData(list.get(position),(VH)holder);
    }

    protected abstract void bindData(D d, VH holder);

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected  List<D> getData(){
        return list;
    }

    //定义回调接口
    public interface IListClick{
        void itemClick(int pos);
    }

    public IListClick iListClick;

    public void setiListClick(IListClick iListClick) {
        this.iListClick = iListClick;
    }

    protected IItemViewClick iItemViewClick;

    public interface IItemViewClick<String>{
        //条目中的元素点击
        void itemViewClick(int viewid,String data);
    }

    public void addItemViewClick(IItemViewClick click){
        this.iItemViewClick = click;
    }


    public class VH extends RecyclerView.ViewHolder{

        SparseArray views=new SparseArray();


        public VH(View view) {
            super(view);

        }
        //查找item的view
        public View getViewById(int id){
            View view = (View) views.get(id);
            if(view == null){
                view = itemView.findViewById(id);
                views.append(id,view);
            }
            return view;
        }

    }
}
