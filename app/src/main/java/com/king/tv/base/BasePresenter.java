package com.king.tv.base;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.king.base.util.LogUtils;
import com.king.tv.App;
import com.king.tv.greendao.DaoSession;
import com.king.tv.ioc.component.AppComponent;

import javax.inject.Inject;

public class BasePresenter<V extends BaseView> extends MvpBasePresenter<V> {
    private App app;

    private DaoSession mDaoSession;

    private AppComponent mAppComponent;

    @Inject
    public BasePresenter(App app) {
        super();
        this.app = app;
        mDaoSession = app.getDaoSession();
        mAppComponent = app.getAppCommponent();
    }


    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public App getApp() {
        return getApp();
    }

    @Override
    public boolean isViewAttached() {
        LogUtils.d("isViewAttached:" + super.isViewAttached());
        return super.isViewAttached();
    }


}
