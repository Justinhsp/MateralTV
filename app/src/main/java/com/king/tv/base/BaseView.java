package com.king.tv.base;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface BaseView  extends MvpView{
    /**
     * 显示加载条
     */
    void showProgress();

    /**
     * 数据加载完成
     */
    void OnCompleted();

    /**
     * 加载失败
     * @param e
     */
    void OnError(Throwable e);
}
