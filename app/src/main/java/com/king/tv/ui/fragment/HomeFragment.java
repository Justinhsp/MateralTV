package com.king.tv.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import com.king.base.adapter.ViewPagerFragmentAdapter;
import com.king.tv.R;
import com.king.tv.base.BaseFragment;
import com.king.tv.bean.LiveCategory;
import com.king.tv.presenter.CategoryPresenter;
import com.king.tv.ui.Iview.ICategoryView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment<ICategoryView, CategoryPresenter> implements ICategoryView {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.ivRight)
    ImageView ivRight;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.btnMore)
    ImageView btnMore;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;
    private List<LiveCategory> liveCategories;
    private List<CharSequence> listTitle;
    private List<Fragment> listData;

    /**
     * 返回单个fragment实例
     *
     * @return
     */
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
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
        return R.layout.fragment_home;
    }

    /**
     * 返回对应presenter
     *
     * @return
     */
    @Override
    public CategoryPresenter createPresenter() {
        return new CategoryPresenter(getApp());
    }


    /**
     * 初始化ui
     */
    @Override
    public void initUI() {
        liveCategories=new ArrayList<>();
        listTitle=new ArrayList<>();
        listData=new ArrayList<>();
        viewPagerFragmentAdapter=new ViewPagerFragmentAdapter(getChildFragmentManager(),listData,listTitle);
        viewpager.setAdapter(viewPagerFragmentAdapter);
        tablayout.setupWithViewPager(viewpager);
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
         getPresenter().getAllCategories();
    }

    /**
     * 数据处理
     *
     * @param list
     */
    @Override
    public void onGetLiveCategory(List<LiveCategory> list) {
            if (list!=null){
                toSetList(liveCategories,list,false);
                listData.clear();
                List<CharSequence> listTemp=new ArrayList<>();
                //-----------------------------------------//
                listTemp.add(getText(R.string.recommend));
                listData.add(RecommendFragment.newInstance());
                listTemp.add(getText(R.string.tab_all));
                //listData.add()
                //-----------------------------------------//
                for (int i=0;i<list.size();i++){
                    LiveCategory liveCategory=list.get(i);
                    listTemp.add(liveCategory.getName());
                }
                toSetList(listTitle,listTemp,false);
            }
            if (viewPagerFragmentAdapter!=null){
                viewPagerFragmentAdapter.setListTitle(listTitle);
                viewPagerFragmentAdapter.setListData(listData);
                viewPagerFragmentAdapter.notifyDataSetChanged();
            }
    }



    /**
     * 加载过程
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



  /*  @OnClick({R.id.ivLeft, R.id.ivRight, R.id.btnMore,R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                startActivity(getFragmentIntent(Constants.SEARCH_FRAGMENT));
                break;
            case R.id.ivRight:
                startLogin();
                break;
            case R.id.btnMore:
                break;
            case R.id.fab:
                startAbout();
                break;

        }
    }*/


}
