package com.king.tv.ui.Iview;

import com.king.tv.base.BaseView;
import com.king.tv.bean.LiveInfo;

import java.util.List;

public interface ILiveListView extends BaseView {
      //获取直播列表
     void OnGetLiveList(List<LiveInfo> list);
     //获取更多直播列表
     void  OnGetMoreLiveList(List<LiveInfo> list);
}
