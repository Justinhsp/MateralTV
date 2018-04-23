package com.king.tv.ui.Iview;

import com.king.tv.base.BaseView;
import com.king.tv.bean.LiveCategory;

import java.util.List;

public interface ICategoryView  extends BaseView{
     //直播
    void onGetLiveCategory(List<LiveCategory> list);
}
