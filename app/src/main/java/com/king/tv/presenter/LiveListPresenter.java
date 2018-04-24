package com.king.tv.presenter;

import com.king.base.util.LogUtils;
import com.king.base.util.StringUtils;
import com.king.tv.App;
import com.king.tv.base.BasePresenter;
import com.king.tv.bean.LiveInfo;
import com.king.tv.bean.LiveListResult;
import com.king.tv.bean.Page;
import com.king.tv.bean.SearchRequestBody;
import com.king.tv.bean.SearchResult;
import com.king.tv.ui.Iview.ILiveListView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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


    /**
     * 分批加载
     *
     * @param key
     * @param page
     */
    public void getLiveListByKey(String key, int page) {
        getLiveListByKey(key, page, Page.DEFAULT_SIZE);
    }

    /**
     * 搜索
     *
     * @param key
     * @param page
     * @param pageSize
     */
    private void getLiveListByKey(String key, final int page, int pageSize) {
        if (isViewAttached()) {
            getView().showProgress();
        }
        getAppComponent().getApiService()
                .search(SearchRequestBody.getInstance(new Page(page, key, pageSize)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<SearchResult, List<LiveInfo>>() {
                    @Override
                    public List<LiveInfo> call(SearchResult searchResult) {
                        LogUtils.d("Response搜素结果*******" + searchResult);
                        if (searchResult != null) {
                            if (searchResult.getData() != null) {
                                return searchResult.getData().getItems();
                            } else {
                                LogUtils.d(searchResult.toString());
                            }
                        }
                        return null;
                    }
                })
                .onErrorReturn(new Func1<Throwable, List<LiveInfo>>() {
                    @Override
                    public List<LiveInfo> call(Throwable throwable) {
                        LogUtils.d(throwable);
                        return null;
                    }
                })
                .subscribe(new Action1<List<LiveInfo>>() {
                    @Override
                    public void call(List<LiveInfo> list) {
                        if (isViewAttached()) {
                            if (page > 0)
                                getView().OnGetMoreLiveList(list);
                            else
                                getView().OnGetLiveList(list);
                        }
                    }
                });

    }


}
