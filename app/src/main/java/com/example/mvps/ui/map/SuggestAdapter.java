package com.example.mvps.ui.map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.mvps.R;
import com.example.mvps.base.BaseAdapter;


import java.util.List;

public class SuggestAdapter extends BaseAdapter<SuggestionResult.SuggestionInfo> {


    private final List list;
    private final Context context;

    public SuggestAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
        this.context = context;
    }

    @Override
    protected int getLagout() {
        return R.layout.item_poi;
    }

    @Override
    protected void bindData(SuggestionResult.SuggestionInfo suggestionInfo, VH holder) {
        TextView poi = (TextView) holder.getViewById(R.id.tv_poi);
        poi.setText(suggestionInfo.getKey());
    }

}
