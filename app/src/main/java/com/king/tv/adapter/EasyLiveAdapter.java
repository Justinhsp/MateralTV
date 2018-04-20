package com.king.tv.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.king.tv.R;
import com.king.tv.bean.LiveInfo;

import java.util.List;

public class EasyLiveAdapter extends RecyclerArrayAdapter<LiveInfo> {

    private boolean isShowStatus;

    public EasyLiveAdapter(Context context, List<LiveInfo> objects,Boolean isShowStatus) {
        super(context, objects);
        this.isShowStatus=isShowStatus;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new LiveViewHolder(parent);
    }


    public class LiveViewHolder extends BaseViewHolder<LiveInfo>{

        public LiveViewHolder(ViewGroup parent) {
            super(parent, R.layout.list_live_item);
        }
    }


}
