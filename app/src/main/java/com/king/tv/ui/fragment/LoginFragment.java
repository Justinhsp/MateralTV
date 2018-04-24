package com.king.tv.ui.fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.king.tv.R;
import com.king.tv.base.SimpleFragment;
import butterknife.BindView;

/**
 * 登录
 */
public class LoginFragment extends SimpleFragment {
    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvForgetPwd)
    TextView tvForgetPwd;
    @BindView(R.id.ivQQ)
    ImageView ivQQ;
    @BindView(R.id.ivSina)
    ImageView ivSina;
    @BindView(R.id.ivWeixin)
    ImageView ivWeixin;

    /**
     * 返回fragment唯一实例
     *
     * @return
     */
    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
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
        return R.layout.fragment_login;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initUI() {
        tvTitle.setText(R.string.login);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

}
