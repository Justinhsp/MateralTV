package com.king.tv.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.king.tv.R;
import com.king.tv.base.SimpleFragment;
import com.king.tv.bean.Room;
import com.king.tv.help.DecimalFormatUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主播
 */
public class AnchorInfoFragment extends SimpleFragment {
    @BindView(R.id.civAvatar)
    CircleImageView civAvatar;
    @BindView(R.id.tvAnchorName)
    TextView tvAnchorName;
    @BindView(R.id.tvAccount)
    TextView tvAccount;
    @BindView(R.id.tvFans)
    TextView tvFans;
    @BindView(R.id.tvWeight)
    TextView tvWeight;
    @BindView(R.id.tvStartLiveTime)
    TextView tvStartLiveTime;
    @BindView(R.id.tvAge)
    TextView tvAge;
    @BindView(R.id.tvEmotionalState)
    TextView tvEmotionalState;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvOccupation)
    TextView tvOccupation;


    //关键字|参数
    private Room room;

    /**
     * 返回唯一fragment实例
     *
     * @param room
     * @return
     */
    public static AnchorInfoFragment newInstance(Room room) {
        Bundle args = new Bundle();
        AnchorInfoFragment fragment = new AnchorInfoFragment();
        fragment.room = room;
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
        return R.layout.fragment_anchorinfo;
    }


    /**
     * 初始化UI
     */
    @Override
    public void initUI() {

    }


    /**
     * 刷新房间数据
     *
     * @param room
     */
    public void onUpdateAnchor(Room room) {
        this.room = room;
        initData();
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        if (room != null && getRootView() != null)
            Glide.with(this).load(room.getAvatar()).error(R.drawable.mine_default_avatar)
                    .placeholder(R.drawable.mine_default_avatar).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(civAvatar);
        tvAnchorName.setText(room.getNick());
        tvAccount.setText(String.valueOf(room.getNo()));
        tvFans.setText(String.valueOf(room.getFollow()));
        tvWeight.setText(DecimalFormatUtil.formatW(room.getWeight() / 100));
        tvStartLiveTime.setText(room.getAnnouncement());
    }
}
