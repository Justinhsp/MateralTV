package com.king.tv.presenter;

import com.king.base.util.LogUtils;
import com.king.base.util.StringUtils;
import com.king.tv.App;
import com.king.tv.base.BasePresenter;
import com.king.tv.bean.LiveInfo;
import com.king.tv.bean.LiveListResult;
import com.king.tv.ui.Iview.ILiveListView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LiveListPresenter extends BasePresenter<ILiveListView> {

    public LiveListPresenter(App app) {
        super(app);
    }

    /**
     * 如果传入的字符串为空
     * 就查询全部
     * 否则 根据tab的标题分类查询
     *
     * @param slug
     */
    public void getLiveList(String slug) {
        if (StringUtils.isBlank(slug))
            getLiveListAll();
        else
            getLiveListBySlug(slug);
    }

    /**
     * 查询全部直播列表
     */
    private void getLiveListAll() {
        if (isViewAttached()) {
            getView().showProgress();
        }
        getAppComponent().getApiService()
                .getLiveListResultAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LiveListResult>() {
                    @Override
                    public void onCompleted() {
                        if (isViewAttached())
                            getView().OnCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached())
                            getView().OnError(e);
                    }

                    @Override
                    public void onNext(LiveListResult liveListResult) {
                        LogUtils.d("Response全部直播列表==========" + liveListResult);
                        List<LiveInfo> list = null;
                        if (liveListResult != null) {
                            list = liveListResult.getData();
                        }
                        if (isViewAttached())
                            getView().OnGetLiveList(list);
                    }
                });

    }

    /**
     * 分类查询
     */
    private void getLiveListBySlug(final String slug) {
        if (isViewAttached()) {
            getView().showProgress();
        }
        getAppComponent().getApiService()
                .getLiveListResultByCategories(slug)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LiveListResult>() {
                    @Override
                    public void onCompleted() {
                        if (isViewAttached())
                            getView().OnCompleted();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached())
                            getView().OnError(e);
                    }
                    @Override
                    public void onNext(LiveListResult liveListResult) {
                        LogUtils.d("Respone分类查询======" + slug + ":" + liveListResult);
                        List<LiveInfo> list = null;
                        if (liveListResult != null) {
                            list = liveListResult.getData();
                        }
                        if (isViewAttached()) {
                            getView().OnGetLiveList(list);
                        }
                    }
                });


    }

}
