package com.king.tv.ui.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.king.base.adapter.ViewPagerFragmentAdapter;
import com.king.base.util.LogUtils;
import com.king.tv.R;
import com.king.tv.base.BaseFragment;
import com.king.tv.bean.Room;
import com.king.tv.help.DensityUtil;
import com.king.tv.presenter.RoomPresenter;
import com.king.tv.ui.Iview.IRoomView;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 房间
 */
public class RoomFragment extends BaseFragment<IRoomView, RoomPresenter> implements IRoomView {
    @BindView(R.id.frameVideo)
    FrameLayout frameVideo;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tvRoomTitle)
    TextView tvRoomTitle;
    @BindView(R.id.ivFullScreen)
    ImageView ivFullScreen;
    @BindView(R.id.rlRoomInfo)
    RelativeLayout rlRoomInfo;
    @BindView(R.id.videoContent)
    RelativeLayout videoContent;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.tvFollow)
    TextView tvFollow;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.llRoomChat)
    LinearLayout llRoomChat;


    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    //存放title的集合
    private List<CharSequence> listTitle;
    //存放fragment的集合
    private List<Fragment> listData;
    //主播
    private AnchorInfoFragment anchorInfoFragment;
    //播放器
    private  VideoFragment videoFragment;
    //关键字|参数
    private String uid;
    private Room room;


    /**
     * 返回fragment唯一实例
     *
     * @param uid
     * @return
     */
    public static RoomFragment newInstance(String uid) {
        Bundle args = new Bundle();
        RoomFragment fragment = new RoomFragment();
        fragment.uid = uid;
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 返回对应布局文件
     *
     * @return
     */
    @Override
    public int getRootViewId() {
        return R.layout.fragment_room;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initUI() {
           //设置播放器的高度
            updateVideoLayoutParams();
           //标题
           listTitle=new ArrayList<>();
           listTitle.add(getText(R.string.room_chat));
           listTitle.add(getText(R.string.room_ranking));
           listTitle.add(getText(R.string.room_anchor));
           //fragment
           listData=new ArrayList<>();
           listData.add(ChatFragment.newInstance());
           listData.add(RankFragment.newInstance());
           anchorInfoFragment=AnchorInfoFragment.newInstance(room);
           viewPagerFragmentAdapter=new ViewPagerFragmentAdapter(getFragmentManager(),listData,listTitle);
           viewPager.setAdapter(viewPagerFragmentAdapter);
           tablayout.setupWithViewPager(viewPager);
    }

    /**
     * 动态设置播放器的高度
     */
    private void updateVideoLayoutParams() {
        ViewGroup.LayoutParams lp = videoContent.getLayoutParams();
        if(isLandscape()){
            lp.height = DensityUtil.getDisplayMetrics(context).heightPixels;
        }else{
            lp.height = (int)(DensityUtil.getDisplayMetrics(context).widthPixels / 16.0f * 9.0f);
        }
        videoContent.setLayoutParams(lp);
    }

    public boolean isLandscape(){
        return getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
          getPresenter().enterRoom(uid);
    }

    /**
     * 返回对应presenter
     *
     * @return
     */
    @Override
    public RoomPresenter createPresenter() {
        return new RoomPresenter(getApp());
    }

    /**
     * 进入房间
     *
     * @param room
     */
    @Override
    public void enterRoom(Room room) {
         this.room=room;
        LogUtils.d("room");
        anchorInfoFragment.onUpdateAnchor(room);
        viewPagerFragmentAdapter.notifyDataSetChanged();
    }

    /**
     * 播放
     *
     * @param url
     */
    @Override
    public void playUrl(String url) {
       LogUtils.d("播放URL*********"+url);
       if (videoFragment==null){
           videoFragment=VideoFragment.newInstance(url,false);
       }
        replaceChildFragment(R.id.frameVideo,videoFragment);
    }

    /**
     * 加载条
     */
    @Override
    public void showProgress() {

    }

    /**
     * 请求完成
     */
    @Override
    public void OnCompleted() {

    }

    /**
     * 请求失败
     *
     * @param e
     */
    @Override
    public void OnError(Throwable e) {

    }

}
