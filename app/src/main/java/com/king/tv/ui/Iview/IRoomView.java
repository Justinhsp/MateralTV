package com.king.tv.ui.Iview;

import com.king.tv.base.BaseView;
import com.king.tv.bean.Room;

public interface IRoomView extends BaseView {
    void enterRoom(Room room);

    void playUrl(String url);
}
