package com.example.administrator.my_shoujiyingyin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.adapter.MainAdapter;
import com.example.administrator.my_shoujiyingyin.fragment.AudioListFragment;
import com.example.administrator.my_shoujiyingyin.fragment.VideoListFragment;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    private TextView  tv_video;
    private TextView  tv_audio;
    private ViewPager viewPager;
    private TextView  mMtv_return;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemApp systemApp = (SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        initView();
        initListener();
        initData();
    }

    private void initData ( ) {
        initPageView();
        changeTitleState(true);
    }


    private void changeTitleState (boolean isSelectVideo) {
        tv_video.setSelected(isSelectVideo);
        tv_audio.setSelected(!isSelectVideo);

    }

    private void initPageView ( ) {
        ArrayList<android.support.v4.app.Fragment> fragments = new ArrayList<android.support.v4.app.Fragment>();
        fragments.add(new VideoListFragment());
        fragments.add(new AudioListFragment());
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

    }

    private void initListener ( ) {
        mMtv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(MainActivity.this, MainMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                viewPager.setCurrentItem(0);


            }
        });
        tv_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                viewPager.setCurrentItem(1);


            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected (int position) {
                changeTitleState(position == 0);


            }

            @Override
            public void onPageScrollStateChanged (int state) {

            }
        });

    }


    private void initView ( ) {
        tv_video = (TextView) findViewById(R.id.tv_video);
        mMtv_return = (TextView) findViewById(R.id.tv_return);
        tv_audio = (TextView) findViewById(R.id.tv_audio);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainMainActivity.class));
    }

    @Override
    protected void onDestroy ( ) {
        SystemApp systemApp = (SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }
}
