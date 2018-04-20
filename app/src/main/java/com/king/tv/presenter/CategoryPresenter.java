package com.king.tv.presenter;

import com.king.base.util.LogUtils;
import com.king.tv.App;
import com.king.tv.bean.LiveCategory;
import com.king.tv.ui.Iview.ICategoryView;
import com.king.tv.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CategoryPresenter extends BasePresenter<ICategoryView> {

    @Inject
    public CategoryPresenter(App app) {
        super(app);
    }

    /**
     * 获取全部分类列表
     */
    public void getAllCategories(){
         getView().showProgress();
         getAppComponent().getApiService()
                 .getAllCategories()
                 .subscribeOn(Schedulers.io())//指定耗时操作切换到子线程
                 .observeOn(AndroidSchedulers.mainThread()) //指定UI操作切换到ui线程
                 .subscribe(new Observer<List<LiveCategory>>() {
                     //请求完成调用
                     @Override
                     public void onCompleted() {
                         if (isViewAttached())
                             getView().OnCompleted();
                     }
                    //请求失败调用
                     @Override
                     public void onError(Throwable e) {
                        if (isViewAttached())
                            getView().OnError(e);
                     }
                     //RxJava普通链式调用方法
                     @Override
                     public void onNext(List<LiveCategory> list) {
                         LogUtils.d("Response分类列表数据===="+list);
                         if (isViewAttached())
                             getView().onGetLiveCategory(list);
                     }
                 });


    }


}
