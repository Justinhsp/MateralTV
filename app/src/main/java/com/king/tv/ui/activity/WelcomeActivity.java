package com.king.tv.ui.activity;

import android.view.animation.Animation;

import com.king.base.SplashActivity;
import com.king.tv.R;

public class WelcomeActivity extends SplashActivity {

    /**
     * 返回对应布局文件
     *
     * @return
     */
    @Override
    public int getContentViewId() {
        return R.layout.activity_welcome;
    }

    /**
     * 自定义监听跳转初始界面
     * @return
     */
    @Override
    public Animation.AnimationListener getAnimationListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivityFinish(MainActivity.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }
}
