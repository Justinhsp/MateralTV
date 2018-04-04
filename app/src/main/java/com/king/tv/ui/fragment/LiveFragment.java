package com.king.tv.ui.fragment;

import android.os.Bundle;

import com.king.tv.R;
import com.king.tv.base.BaseFragment;
import com.king.tv.base.BasePresenter;
import com.king.tv.base.BaseView;

/**
 * 直播
 */
public class LiveFragment extends BaseFragment<BaseView,BasePresenter<BaseView>> {

    private String title;
    private String slug;
    private boolean isTabLive;

    /**
     * 返回单个fragment实例
     * @param title
     * @param slug
     * @param isTabLive
     * @return
     */
    public static  LiveFragment newInstance(String title,String slug,boolean isTabLive){
        Bundle args=new Bundle();
        LiveFragment fragment=new LiveFragment();
        fragment.title=title;
        fragment.slug=slug;
        fragment.isTabLive=isTabLive;
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 返回对应布局文件
     * @return
     */
    @Override
    public int getRootViewId() {
        return R.layout.fragment_live;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {

    }

    @Override
    public BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<BaseView>(getApp());
    }
}
