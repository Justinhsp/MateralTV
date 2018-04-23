package com.king.tv.ui.Iview;

import com.king.tv.base.BaseView;
import com.king.tv.bean.Banner;
import com.king.tv.bean.Recommend;

import java.util.List;

public interface IRecommendView extends BaseView {
    //推荐数据
    void OnGetRecommend(Recommend recommed);
    //房间数据
    void onGetRooms(List<Recommend.RoomBean> list);
    //广告轮播
    void OnGetBanner(List<Banner> list);
}
