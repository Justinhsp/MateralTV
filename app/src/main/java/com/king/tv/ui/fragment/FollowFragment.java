package com.king.tv.ui.fragment;

import android.os.Bundle;

import com.king.tv.R;
import com.king.tv.base.SimpleFragment;

/**
 * 关注
 */
public class FollowFragment  extends SimpleFragment {

    /**
     * 返回fragment的单个实例
     * @return
     */
    public  static  FollowFragment newInstance(){
        Bundle args=new Bundle();
        FollowFragment fragment=new FollowFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 返回对应布局文件
     * @return
     */
    @Override
    public int getRootViewId() {
        return R.layout.fragment_follow;
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
}
