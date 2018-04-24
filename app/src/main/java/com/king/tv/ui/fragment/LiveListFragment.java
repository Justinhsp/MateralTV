package com.king.tv.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.king.base.util.LogUtils;
import com.king.base.util.StringUtils;
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
 * 全部
 */
public class LiveListFragment extends BaseFragment<ILiveListView, LiveListPresenter> implements ILiveListView, SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    @BindView(R.id.easyRecyclerView)
    EasyRecyclerView easyRecyclerView;
    private View loadMore;
    private TextView tvEmpty;
    private TextView tvTips;

    private List<LiveInfo> listData;
    private EasyLiveAdapter easyLiveAdapter;

    //关键字|参数
    private String slug;
    private boolean isSearch;
    private int page;
    private boolean isMore;
    private String key;

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
        //布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setSpanSizeLookup(easyLiveAdapter.obtainGridSpanSizeLookUp(2));
        easyRecyclerView.setLayoutManager(gridLayoutManager);
        easyRecyclerView.setAdapter(easyLiveAdapter);
        //下拉刷新
        easyRecyclerView.setRefreshListener(this);
        //RecycleView item点击
        easyLiveAdapter.setOnItemClickListener(this);
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
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
      if (isSearch){
          if (!StringUtils.isBlank(key)) {
              page = 0;
              getPresenter().getLiveListByKey(key, page);
          }
      }else {
          getPresenter().getLiveList(slug);
      }
        /**
         * 加载视图
         */
      if (isSearch){
          loadMore= LayoutInflater.from(context).inflate(R.layout.load_more,null);
          easyLiveAdapter.setMore(loadMore, new RecyclerArrayAdapter.OnMoreListener() {
              @Override
              public void onMoreShow() {
                  if(isMore){
                      if(loadMore!=null){
                          loadMore.setVisibility(View.VISIBLE);
                      }
                      getPresenter().getLiveListByKey(key,page);
                  }
              }
              @Override
              public void onMoreClick() {
              }
          });
      }
    }


    /**
     * RecycleView Item点击
     * @param position
     */
    @Override
    public void onItemClick(int position) {
         startRoom(easyLiveAdapter.getItem(position));
    }

    /**
     * 搜索方法
     * @param key
     * @param page
     */
    public void search(String key,int page){
        this.key=key;
        this.page=page;
        getPresenter().getLiveListByKey(key,page);
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
