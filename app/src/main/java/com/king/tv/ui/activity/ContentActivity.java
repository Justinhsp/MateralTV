package com.king.tv.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.king.base.util.LogUtils;
import com.king.tv.R;
import com.king.tv.help.Constants;
import com.king.tv.ui.fragment.LiveFragment;
import com.king.tv.ui.fragment.LoginFragment;
import com.king.tv.ui.fragment.RoomFragment;
import com.king.tv.ui.fragment.SearchFragment;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        //加载fragment
        switchFragment(getIntent());
    }

    /**
     * 根据传过来的常量替换fragment
     * @param intent
     */
    private void switchFragment(Intent intent) {
        int fragmentKey = intent.getIntExtra(Constants.KEY_FRAGMENT, 0);
        switch (fragmentKey){
            case Constants.ROOM_FRAGMENT://房间
                replaceFragment(RoomFragment.newInstance(intent.getStringExtra(Constants.KEY_UID)));
                break;
            case Constants.LIVE_FRAGMENT: {//直播
                String title = intent.getStringExtra(Constants.KEY_TITLE);
                String slug = intent.getStringExtra(Constants.KEY_SLUG);
                boolean isTabLive = intent.getBooleanExtra(Constants.KEY_IS_TAB_LIVE, false);
                replaceFragment(LiveFragment.newInstance(title, slug, isTabLive));
                break;
            }case Constants.WEB_FRAGMENT: {//webview
                String title = intent.getStringExtra(Constants.KEY_TITLE);
                String url = intent.getStringExtra(Constants.KEY_URL);
                //replaceFragment(WebFragment.newInstance(url, title));
                break;
            }case Constants.LOGIN_FRAGMENT://登录
                replaceFragment(LoginFragment.newInstance());
                break;
            case Constants.ABOUT_FRAGMENT://关于作者
                //replaceFragment(AboutFragment.newInstance());
                break;
            case Constants.FULL_ROOM_FRAGMENT://全屏
                String uid = intent.getStringExtra(Constants.KEY_UID);
                String cover = intent.getStringExtra(Constants.KEY_COVER);
                //replaceFragment(FullRoomFragment.newInstance(uid,cover));
                break;
            case Constants.SEARCH_FRAGMENT://搜索
                replaceFragment(SearchFragment.newInstance());
                break;
            default:
                LogUtils.d("Not found fragment:" + Integer.toHexString(fragmentKey));
                break;
        }




    }


    /**
     * 替换
     * @param fragment
     */
    public  void replaceFragment(Fragment fragment){
        replaceFragment(R.id.fragmentContent,fragment);
    }

    private void replaceFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id,fragment).commit();
    }


}
