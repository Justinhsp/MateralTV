package com.king.tv.presenter;

import com.king.base.util.LogUtils;
import com.king.tv.App;
import com.king.tv.base.BasePresenter;
import com.king.tv.bean.AppStart;
import com.king.tv.bean.Recommend;
import com.king.tv.ui.Iview.IRecommendView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecommendPresenter extends BasePresenter<IRecommendView> {

    public RecommendPresenter(App app) {
        super(app);
    }

    /**
     * 推荐
     */
    public  void getRecommend(){
        if (isViewAttached()) {
            getView().showProgress();
        }
        getAppComponent().getApiService()
                .getRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Recommend>() {
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
                    public void onNext(Recommend recommend) {
                        LogUtils.d("Recommend推荐数据============"+recommend);
                        if (isViewAttached()){
                            getView().OnGetRecommend(recommend);
                            if (recommend!=null){
                                if (isViewAttached())
                                    getView().onGetRooms(recommend.getRoom());
                            }
                        }

                    }
                });
    }

    /**
     * 广告轮播图
     */
    public void getBanner(){
        getAppComponent().getApiService()
                .getAppStartInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AppStart>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AppStart appStart) {
                            if (appStart!=null){
                                if (isViewAttached())
                                    getView().OnGetBanner(appStart.getBanners());
                            }

                    }
                });
    }


}
