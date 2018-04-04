package com.king.tv.ui.fragment;

import android.os.Bundle;

import com.king.tv.ui.view.ICategoryView;
import com.king.tv.R;
import com.king.tv.base.BaseFragment;
import com.king.tv.bean.LiveCategory;
import com.king.tv.presenter.CategoryPresenter;

import java.util.List;

/**
 * 首页
 */
public class HomeFragment  extends BaseFragment<ICategoryView,CategoryPresenter> implements ICategoryView{

    /**
     * 返回单个fragment实例
     * @return
     */
    public static HomeFragment newInstance(){
        Bundle args=new Bundle();
        HomeFragment fragment=new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 返回对应布局文件
     * @return
     */
    @Override
    public int getRootViewId() {
        return R.layout.fragment_home;
    }

    /**
     * 返回对应presenter
     * @return
     */
    @Override
    public CategoryPresenter createPresenter() {
        return new CategoryPresenter(getApp());
    }


    /**
     * 初始化ui
     */
    @Override
    public void initUI() {

    }

    @Override
    public void onGetLiveCategory(List<LiveCategory> list) {

    }


    @Override
    public void initData() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void OnCompleted() {

    }

    @Override
    public void OnError(Throwable e) {

    }
}
