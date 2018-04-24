package com.king.tv.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.tv.R;
import com.king.tv.base.BaseFragment;
import com.king.tv.base.BasePresenter;
import com.king.tv.base.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 直播
 */
public class LiveFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> {

    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivRight)
    ImageView ivRight;


    private String title;
    private String slug;
    private boolean isTabLive;

    /**
     * 返回单个fragment实例
     *
     * @param title
     * @param slug
     * @param isTabLive
     * @return
     */
    public static LiveFragment newInstance(String title, String slug, boolean isTabLive) {
        Bundle args = new Bundle();
        LiveFragment fragment = new LiveFragment();
        fragment.title = title;
        fragment.slug = slug;
        fragment.isTabLive = isTabLive;
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
        return R.layout.fragment_live;
    }

    /**
     * 初始化UI
     */
    @Override
    public void initUI() {
        tvTitle.setText(title);
        if (isTabLive) {
            ivLeft.setImageResource(R.drawable.ic_top_search);
            ivRight.setVisibility(View.VISIBLE);
        } else {
            ivLeft.setImageResource(R.drawable.btn_back_selector);
            ivRight.setVisibility(View.INVISIBLE);
        }
        replaceChildFragment(R.id.fragment,LiveListFragment.newInstance(slug));
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    /**
     * 返回对应presenter
     *
     * @return
     */
    @Override
    public BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<BaseView>(getApp());
    }

}
