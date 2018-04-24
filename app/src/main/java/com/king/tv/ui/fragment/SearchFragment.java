package com.king.tv.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.king.base.util.StringUtils;
import com.king.base.util.ToastUtils;
import com.king.tv.R;
import com.king.tv.base.BaseFragment;
import com.king.tv.base.BasePresenter;
import com.king.tv.base.BaseView;

import butterknife.BindView;

/**
 * 搜索
 */
public class SearchFragment extends BaseFragment<BaseView, BasePresenter<BaseView>> implements View.OnKeyListener {

    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.etKey)
    EditText etKey;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.fragmentContent)
    FrameLayout fragmentContent;

     //直播
    private LiveListFragment liveListFragment;

    /**
     * 返回fragment唯一实例
     *
     * @return
     */
    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
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
        return R.layout.fragment_search;
    }

    /**
     * 初始化UI
     */
    @Override
    public void initUI() {
          etKey.setOnKeyListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
            liveListFragment=LiveListFragment.newInstance(null,true);
            replaceChildFragment(R.id.fragmentContent,liveListFragment);
    }

    /**
     * 返回对应Presenter
     *
     * @return
     */
    @Override
    public BasePresenter<BaseView> createPresenter() {
        return new BasePresenter<>(getApp());
    }


    /**
     * 搜索框监听
     * @param view
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP){
            if(keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_SEARCH){
                clickSearch();
                return true;
            }
        }
        return false;
    }

    /**
     * 点击搜索
     */
    private void clickSearch() {
        if (checkInputKey()){
            hideInputMethod(etKey);
            liveListFragment.search(etKey.getText().toString(),0);
        }
    }

    /**
     * 检查文本框内容
     * @return
     */
    private boolean checkInputKey(){
        if(StringUtils.isBlank(etKey.getText())){
            ToastUtils.showToast(context,R.string.tips_search_keywords_cannot_be_empty);
            return false;
        }
        return true;
    }


    /**
     * 隐藏软键盘
     *
     * @param v
     */
    public void hideInputMethod(final EditText v) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        v.clearFocus();
    }

    /**
     * 显示软键盘
     *
     * @param v
     */
    public void showInputMethod(final EditText v) {

        v.requestFocus();
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v,InputMethodManager.SHOW_IMPLICIT);
    }

}
