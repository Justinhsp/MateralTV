package com.king.tv.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.king.base.util.LogUtils;

/**
 * 自定义Behavior
 */
public class ScollAwareFABBehavior extends FloatingActionButton.Behavior {

    private Context context;

    public ScollAwareFABBehavior(Context context, AttributeSet attrs){
        super();
        this.context = context;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

        LogUtils.d("dy:" + dyConsumed +" -- dyu:" + dyUnconsumed);
        if(dyConsumed>0 && child.getVisibility() == View.VISIBLE){
            child.hide();
        }else if(dyConsumed<0 && child.getVisibility() != View.VISIBLE){
            child.show();
        }
    }
}
