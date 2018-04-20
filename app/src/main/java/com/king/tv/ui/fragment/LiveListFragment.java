package com.king.tv.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.king.tv.R;
import com.king.tv.adapter.EasyLiveAdapter;
import com.king.tv.base.BaseFragment;
import com.king.tv.bean.LiveInfo;
import com.king.tv.help.DensityUtil;
import com.king.tv.presenter.LiveListPresenter;
import com.king.tv.ui.Iview.ILiveListView;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

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
        tvTips=(TextView)easyRecyclerView.findViewById(R.id.tvTips);
        tvEmpty=(TextView)easyRecyclerView.findViewById(R.id.tvEmpty);
        //分割线
        SpaceDecoration spaceDecoration=new SpaceDecoration(DensityUtil.dp2px(context,6));
        easyRecyclerView.addItemDecoration(spaceDecoration);
        //下拉刷新控件颜色
        easyRecyclerView.setRefreshingColorResources(R.color.progress_color);

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }


    @Override
    public void OnGetLiveList(List<LiveInfo> list) {

    }

    @Override
    public void OnGetMoreLiveList(List<LiveInfo> list) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void OnCompleted() {

    }

    @Override
    public void OnError(Throwable e) {

    }


}
