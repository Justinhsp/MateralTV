package com.king.tv.ui.fragment;

import android.os.Bundle;

import com.king.tv.R;
import com.king.tv.base.BaseFragment;
import com.king.tv.base.BasePresenter;
import com.king.tv.base.BaseView;

/**
 * 播放器
 */
public class VideoFragment extends BaseFragment<BaseView,BasePresenter<BaseView>> {
    //播放地址
    private String url;
    //是否全屏
    private boolean isFull;


    /**
     * 返回fragment唯一实例
     * @param url
     * @param isFull
     * @return
     */
    public static VideoFragment newInstance(String url,boolean isFull){
        Bundle args=new Bundle();
        VideoFragment fragment=new VideoFragment();
        fragment.url=url;
        fragment.isFull=isFull;
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 返回对应布局文件
     * @return
     */
    @Override
    public int getRootViewId() {
        return R.layout.fragment_video;
    }

    /**
     * 初始化UI
     */
    @Override
    public void initUI() {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    /**
     * 返回对应Presenter
     * @return
     */
    @Override
    public BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>(getApp());
    }
}
