package com.king.tv.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.king.base.util.LogUtils;
import com.king.base.util.SystemUtils;
import com.king.tv.R;
import com.king.tv.adapter.RecommendAdapter;
import com.king.tv.base.BaseFragment;
import com.king.tv.bean.Banner;
import com.king.tv.bean.Recommend;
import com.king.tv.presenter.RecommendPresenter;
import com.king.tv.ui.Iview.IRecommendView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 推荐
 */
public class RecommendFragment extends BaseFragment<IRecommendView, RecommendPresenter> implements IRecommendView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.easyRecycleview)
    EasyRecyclerView easyRecycleview;

    private ConvenientBanner<Banner> convenientBanner;
    private TextView tvTips;
    private List<Recommend.RoomBean> listData;
    private List<Banner> listBanner;
    private RecommendAdapter recommendAdapter;


    /**
     * 返回fragment唯一实例
     *
     * @return
     */
    public static RecommendFragment newInstance() {
        Bundle args = new Bundle();
        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 返回布局文件
     *
     * @return
     */
    @Override
    public int getRootViewId() {
        return R.layout.fragment_recommend;
    }

    /**
     * 初始化ui
     */
    @Override
    public void initUI() {
        tvTips = (TextView) easyRecycleview.findViewById(R.id.tvTips);
        SpaceDecoration spaceDecoration = new SpaceDecoration(0);
        easyRecycleview.addItemDecoration(spaceDecoration);
        easyRecycleview.setRefreshingColorResources(R.color.progress_color);
        listData = new ArrayList<>();
        recommendAdapter = new RecommendAdapter(context, listData);
        listBanner = new ArrayList<>();
        recommendAdapter.addHeader(new HeaderView(listBanner));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        easyRecycleview.setLayoutManager(linearLayoutManager);
        easyRecycleview.setAdapter(recommendAdapter);
        easyRecycleview.setRefreshListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
          easyRecycleview.showProgress();
           getPresenter().getRecommend();
           getPresenter().getBanner();
    }


    /**
    * 获取焦点时轮播图片
    */
    @Override
    public void onResume() {
        super.onResume();
        if(convenientBanner!=null && !convenientBanner.isTurning()) {
            convenientBanner.startTurning(4000);
        }
    }

    /**
    * 失去焦点时暂停
    */
    @Override
    public void onPause() {
        super.onPause();
        if(convenientBanner!=null){
            convenientBanner.stopTurning();
        }
    }




    /**
     * 返回对应presenter
     *
     * @return
     */
    @Override
    public RecommendPresenter createPresenter() {
        return new RecommendPresenter(getApp());
    }


    /**
     * 将Banner作为Recycle的顶部视图
     */
    public class HeaderView implements RecyclerArrayAdapter.ItemView {

        private List<Banner> listBanner;

        public HeaderView(List<Banner> listBanner) {
            this.listBanner = listBanner;
        }

        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.banner, null);
            convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
            convenientBanner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    clickBannerItem(listBanner.get(position));
                }
            });
            return view;
        }

        @Override
        public void onBindView(View headerView) {
            convenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Holder<Banner> createHolder() {
                    return new ImageHolder();
                }
            }, listBanner)
                    .setPageIndicator(new int[]{R.drawable.ic_dot_normal, R.drawable.ic_dot_pressed})//添加Viewpager小圆点
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);   //设置排列方式
            //是否开启翻页
            if (!convenientBanner.isTurning()) {
                //设置自动翻页时间
                convenientBanner.startTurning(3000);
            }
        }
    }
    /**
    * 持有者模式缓存图片
    */
    public class ImageHolder implements Holder<Banner> {

        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Banner data) {
            Glide.with(context).load(data.getThumb()).placeholder(R.drawable.live_default)
                    .error(R.drawable.live_default).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }


    /**
     * 轮播图点击处理
     *
     * @param banner
     */
    public void clickBannerItem(Banner banner) {
        if (banner != null) {
            if (banner.isRoom())
                //如果是房间类型就进入房间
                startRoom(banner.getLink_object());
            else
                //广告类型处理
                startWeb(banner.getTitle(), banner.getTitle());
        }
    }

    /**
     * 推荐
     *
     * @param recommed
     */
    @Override
    public void OnGetRecommend(Recommend recommed) {

    }

    /**
     * 房间
     *
     * @param list
     */
    @Override
    public void onGetRooms(List<Recommend.RoomBean> list) {
        recommendAdapter.addAll(list);
        recommendAdapter.notifyDataSetChanged();
        if (recommendAdapter.getCount() == 0) {
            easyRecycleview.showEmpty();
        }
    }

    /**
     * 轮播图
     *
     * @param list
     */
    @Override
    public void OnGetBanner(List<Banner> list) {
        if (convenientBanner != null) {
            toSetList(listBanner, list, false);
            convenientBanner.notifyDataSetChanged();
        }
    }

    /**
     * 加载条
     */
    @Override
    public void showProgress() {

    }

    /**
     * 加载完成
     */
    @Override
    public void OnCompleted() {
        easyRecycleview.setRefreshing(false);
    }

    /**
     * 加载失败
     *
     * @param e
     */
    @Override
    public void OnError(Throwable e) {
        LogUtils.d(e);
         if (SystemUtils.isNetWorkActive(context)) {
             tvTips.setText(R.string.page_load_failed);
         }else{
            tvTips.setText(R.string.network_unavailable);
         }
          easyRecycleview.showError();
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        getPresenter().getRecommend();
    }
}
