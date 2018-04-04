package com.king.tv.base;

/**
 * 简单的fragment就继承这个basefragment
 */
public abstract class SimpleFragment extends BaseFragment<BaseView,BasePresenter<BaseView>> {

    @Override
    public BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>(getApp());
    }

}
