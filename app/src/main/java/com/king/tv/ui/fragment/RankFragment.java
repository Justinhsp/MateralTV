package com.king.tv.ui.fragment;

import android.os.Bundle;

import com.king.tv.R;
import com.king.tv.base.SimpleFragment;

/**
 * 排行
 */
public class RankFragment extends SimpleFragment {

    /**
     * 返回fragment的唯一实例
     * @return
     */
    public static  RankFragment newInstance(){
        Bundle args=new Bundle();
        RankFragment fragment=new RankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 返回对应布局文件
     * @return
     */
    @Override
    public int getRootViewId() {
        return R.layout.fragment_rank;
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
