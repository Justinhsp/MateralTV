package com.king.tv.presenter;

import com.king.base.util.LogUtils;
import com.king.tv.App;
import com.king.tv.base.BasePresenter;
import com.king.tv.bean.Room;
import com.king.tv.bean.RoomLine;
import com.king.tv.ui.Iview.IRoomView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RoomPresenter extends BasePresenter<IRoomView> {

    public RoomPresenter(App app) {
        super(app);
    }

    public void enterRoom(String uid) {
        enterRoom(uid, false);
    }

    /**
     * 播放
     *
     * @param uid
     * @param isShowing
     */
    private void enterRoom(String uid, final boolean isShowing) {
        if (isViewAttached())
            getView().showProgress();
        getAppComponent().getApiService()
                .enterRoom(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Room>() {
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
                    public void onNext(Room room) {
                        LogUtils.d("Response进入房间*************" + room);
                        if (isViewAttached())
                            getView().enterRoom(room);
                        if (room != null) {
                            String url = null;
                            RoomLine roomLine = room.getLive().getWs();
                            RoomLine.FlvBean flvBean = roomLine.getFlv();
                            LogUtils.d("flv" + flvBean);
                            if (flvBean != null) {
                                url = flvBean.getValue(isShowing).getSrc();
                            } else {
                                url = roomLine.getHls().getValue(isShowing).getSrc();
                            }
                            if (isViewAttached())
                                getView().playUrl(url);
                        }
                    }
                });

    }

}
