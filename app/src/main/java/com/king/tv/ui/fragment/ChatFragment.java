package com.king.tv.ui.fragment;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.king.tv.R;
import com.king.tv.base.BaseFragment;
import com.king.tv.presenter.ChatPresenter;
import com.king.tv.ui.Iview.IChatView;
import butterknife.BindView;

/**
 * 聊天
 */
public class ChatFragment extends BaseFragment<IChatView, ChatPresenter> {

    @BindView(R.id.tvTips)
    TextView tvTips;
    @BindView(R.id.ivEmoji)
    ImageView ivEmoji;
    @BindView(R.id.etChat)
    EditText etChat;
    @BindView(R.id.ivDanmu)
    ImageView ivDanmu;
    @BindView(R.id.ivGif)
    ImageView ivGif;

    /**
     * 返回未已fragment实例
     *
     * @return
     */
    public static ChatFragment newInstance() {
        Bundle args = new Bundle();
        ChatFragment fragment = new ChatFragment();
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
        return R.layout.fragment_chat;
    }

    /**
     * 初始化UI
     */
    @Override
    public void initUI() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        //系统通知图片
        ImageSpan imageSpan = new ImageSpan(context, R.drawable.img_dm_xttz);
        SpannableString spannableString = new SpannableString("tips");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spannableString);
        //系统通知内容
        ssb.append(getText(R.string.tips_notice_desc));
        tvTips.setText(ssb);
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
    public ChatPresenter createPresenter() {
        return new ChatPresenter(getApp());
    }

}
