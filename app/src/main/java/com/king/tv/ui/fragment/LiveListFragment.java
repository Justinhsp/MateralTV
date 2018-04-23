package com.king.tv.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.king.base.util.LogUtils;
import com.king.base.util.SystemUtils;
import com.king.tv.R;
import com.king.tv.adapter.EasyLiveAdapter;
import com.king.tv.base.BaseFragment;
import com.king.tv.bean.LiveInfo;
import com.king.tv.bean.Page;
import com.king.tv.help.DensityUtil;
import com.king.tv.presenter.LiveListPresenter;
import com.king.tv.ui.Iview.ILiveListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 房间列表
 */
public class LiveListFragment extends BaseFragment<ILiveListView, LiveListPresenter> implements ILiveListView {

    @BindView(R.id.easyRecyclerView)
    EasyRecyclerView easyRecyclerView;
    private View loadMore;
    private TextView tvEmpty;
    private TextView tvTips;

    private List<LiveInfo> listData;
    private EasyLiveAdapter easyLiveAdapter;

    //关键字
    private String slug;
    private boolean isSearch;
    private int page;
    private boolean isMore;

    public static LiveListFragment newInstance(String slug) {
        return newInstance(slug, false);
    }

    public static LiveListFragment newInstance(String slug, Boolean isSearch) {
        Bundle args = new Bundle();
        LiveListFragment fragment = new LiveListFragment();
        fragment.slug = slug;
        fragment.isSearch = isSearch;
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
        return R.layout.fragment_live_list;
    }

    /**
     * 返回对应presenter
     *
     * @return
     */
    @Override
    public LiveListPresenter createPresenter() {
        return new LiveListPresenter(getApp());
    }

    /**
     * 初始化Ui
     */
    @Override
    public void initUI() {
        tvTips = (TextView) easyRecyclerView.findViewById(R.id.tvTips);
        tvEmpty = (TextView) easyRecyclerView.findViewById(R.id.tvEmpty);
        //分割线
        SpaceDecoration spaceDecoration = new SpaceDecoration(DensityUtil.dp2px(context, 6));
        easyRecyclerView.addItemDecoration(spaceDecoration);
        //下拉刷新控件颜色
        easyRecyclerView.setRefreshingColorResources(R.color.progress_color);
        listData = new ArrayList<>();
        easyLiveAdapter = new EasyLiveAdapter(context, listData, isSearch);
        easyLiveAdapter.setNotifyOnChange(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setSpanSizeLookup(easyLiveAdapter.obtainGridSpanSizeLookUp(2));
        easyRecyclerView.setLayoutManager(gridLayoutManager);
        easyRecyclerView.setAdapter(easyLiveAdapter);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        if (!isSearch)
            easyRecyclerView.showProgress();
           getPresenter().getLiveList(slug);
    }


    /**
     * 添加全部直播列表数据
     *
     * @param list
     */
    @Override
    public void OnGetLiveList(List<LiveInfo> list) {
        easyLiveAdapter.clear();
        easyLiveAdapter.addAll(list);
        refreshView();
    }


    /**
     * 加载更多列表数据
     *
     * @param list
     */
    @Override
    public void OnGetMoreLiveList(List<LiveInfo> list) {
        easyLiveAdapter.addAll(list);
        refreshView();
    }


    /**
     * 刷新数据
     */
    private void refreshView() {
        easyLiveAdapter.notifyDataSetChanged();
        easyRecyclerView.setRefreshing(false);
        if (easyLiveAdapter.getCount() == 0) {
            if (isSearch) {
                if (SystemUtils.isNetWorkActive(context)) {
                    tvEmpty.setText(R.string.can_not_find_relevant_content);
                } else {
                    tvTips.setText(R.string.network_unavailable);
                }
            } else {
                tvEmpty.setText(R.string.swipe_down_to_refresh);
            }
            easyRecyclerView.showEmpty();
        }

        if (isSearch) {
            if (easyLiveAdapter.getCount() >= (page + 1) * Page.DEFAULT_SIZE) {
                page++;
                isMore = true;
            } else {
                if (loadMore != null) {
                    loadMore.setVisibility(View.GONE);
                }
                isMore = false;
            }
        }
    }


    /**
     * 加载条
     */
    @Override
    public void showProgress() {

    }

    /**
     * 加载数据完成
     */
    @Override
    public void OnCompleted() {
        easyRecyclerView.setRefreshing(false);
    }

    /**
     * 加载数据失败
     *
     * @param e
     */
    @Override
    public void OnError(Throwable e) {
        LogUtils.w(e);
        if (SystemUtils.isNetWorkActive(context)) {
            tvTips.setText(R.string.page_load_failed);
        } else {
            tvTips.setText(R.string.network_unavailable);
        }
        easyRecyclerView.showError();
    }


}
