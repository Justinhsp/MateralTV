package com.king.tv.ui.fragment;

import android.os.Bundle;

import com.king.tv.R;
import com.king.tv.base.SimpleFragment;

/**
 * 我的
 */
public class MineFragment  extends SimpleFragment{

    /**
     * 返回唯一fragment实例
     * @return
     */
    public static MineFragment newInstance(){
        Bundle args=new Bundle();
        MineFragment fragment=new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 返回对应布局文件
     * @return
     */
    @Override
    public int getRootViewId() {
        return R.layout.fragment_mine;
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
