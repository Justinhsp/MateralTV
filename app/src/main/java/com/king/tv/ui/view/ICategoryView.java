package com.king.tv.ui.view;

import com.king.tv.base.BaseView;
import com.king.tv.bean.LiveCategory;

import java.util.List;

public interface ICategoryView  extends BaseView{

    void onGetLiveCategory(List<LiveCategory> list);
}
