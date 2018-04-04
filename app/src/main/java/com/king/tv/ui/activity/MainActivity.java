package com.king.tv.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.king.base.util.ToastUtils;
import com.king.tv.R;
import com.king.tv.base.PureActivity;
import com.king.tv.ui.fragment.FollowFragment;
import com.king.tv.ui.fragment.HomeFragment;
import com.king.tv.ui.fragment.LiveFragment;
import com.king.tv.ui.fragment.MineFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends PureActivity {
    @BindView(R.id.fragmentContent)
    FrameLayout fragmentContent;
    @BindView(R.id.rbHome)
    RadioButton rbHome;
    @BindView(R.id.rbLive)
    RadioButton rbLive;
    @BindView(R.id.rbFollw)
    RadioButton rbFollw;
    @BindView(R.id.rbMe)
    RadioButton rbMe;


    private HomeFragment homeFragment;
    private LiveFragment liveFragment;
    private FollowFragment followFragment;
    private MineFragment mineFragment;
    private boolean isExit;


    /**
     * 绑定ButterKnife
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    /**
     * 返回对应布局文件
     *
     * @return
     */
    @Override
    public int getRootViewId() {
        return R.layout.activity_main;
    }


    /**
     * 初始化UI
     */
    @Override
    public void initUI() {
        showHomeFragment();
    }


    public void showHomeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, homeFragment);
        }
        commitShowFragment(fragmentTransaction, homeFragment);
    }

    public void showLiveFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (liveFragment == null) {
            liveFragment = LiveFragment.newInstance(getString(R.string.tab_live), null, true);
            fragmentTransaction.add(R.id.fragmentContent, liveFragment);
        }
        commitShowFragment(fragmentTransaction, liveFragment);
    }

    public void showFollowFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (followFragment == null) {
            followFragment = FollowFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, followFragment);
        }
        commitShowFragment(fragmentTransaction, followFragment);
    }

    public void showMineFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (mineFragment == null) {
            mineFragment = MineFragment.newInstance();
            fragmentTransaction.add(R.id.fragmentContent, mineFragment);
        }
        commitShowFragment(fragmentTransaction, mineFragment);
    }

    /**
     * 显示fragment
     *
     * @param fragmentTransaction
     * @param fragment
     */
    public void commitShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    /**
     * 影藏全部fragment
     *
     * @param fragmentTransaction
     */
    public void hideAllFragment(FragmentTransaction fragmentTransaction) {
        hideFragment(fragmentTransaction, homeFragment);
        hideFragment(fragmentTransaction, liveFragment);
        hideFragment(fragmentTransaction, followFragment);
        hideFragment(fragmentTransaction, mineFragment);
    }

    /**
     * 隐藏单个fragment
     *
     * @param fragmentTransaction
     * @param fragment
     */
    private void hideFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }

    /**
     * 替换fragment
     *
     * @param id
     * @param fragment
     */
    public void replaceFragment(@IdRes int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    /**
     * 点击底部RadioButton
     * 切换fragment
     * @param view
     */
    @OnClick({com.king.tv.R.id.rbHome, com.king.tv.R.id.rbLive, com.king.tv.R.id.rbFollw, com.king.tv.R.id.rbMe})
    public void onClick(View view) {
        switch (view.getId()) {
            case com.king.tv.R.id.rbHome:
                showHomeFragment();
                break;
            case com.king.tv.R.id.rbLive:
                showLiveFragment();
                break;
            case com.king.tv.R.id.rbFollw:
                showFollowFragment();
                break;
            case com.king.tv.R.id.rbMe:
                showMineFragment();
                break;
        }
    }



    @Override
    public void onBackPressed() {

        if(!isExit){
            ToastUtils.showToast(context,R.string.press_again_to_exit);
            isExit = true;
            EventBus.getDefault().post(isExit);
        }else{
            super.onBackPressed();
        }

    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventExit(Boolean isBool){
        SystemClock.sleep(1000);
        isExit = false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        isExit = false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
