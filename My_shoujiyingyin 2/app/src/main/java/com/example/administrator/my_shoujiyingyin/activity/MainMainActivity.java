package com.example.administrator.my_shoujiyingyin.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.adapter.MMainAdapter;
import com.example.administrator.my_shoujiyingyin.fragment.TvbroadcastFragment;
import com.example.administrator.my_shoujiyingyin.fragment.TvmovieFragment;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;


public class MainMainActivity extends AppCompatActivity {
    @ViewInject(R.id.tv_intent)
    TextView mTv_intent;
    @ViewInject(R.id.tv_movie)
    TextView mTv_movie;
    @ViewInject(R.id.mmview_pager)
    ViewPager mMmview_pager;
    @OnClick(R.id.tv_intent)
    public void tvIntent(View v) {
        mMmview_pager.setCurrentItem(0);
    }
    @OnClick(R.id.tv_movie)
    public void tvMovie(View v) {
        mMmview_pager.setCurrentItem(1);
    }
    @OnClick(R.id.setting)
    public void setting(View v) {
        Intent intent=new Intent(MainMainActivity.this,setting.class);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.et_search)
    public void etSearch(View v) {
        search();
    }
    @OnClick(R.id.tv_search)
    public void tvSearch(View v) {
        search();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main_main);
        ViewUtils.inject(this);
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.addStack(this);
        initListener();
        initData();
    }
    private void search ( ) {
        Intent intent=new Intent(MainMainActivity.this,search_Activity_second.class);
        startActivity(intent);
        finish();
    }


    private void initData() {
        ArrayList<android.support.v4.app.Fragment> mfragments=new ArrayList<android.support.v4.app.Fragment>();
        mfragments.add(new TvbroadcastFragment());
        mfragments.add(new TvmovieFragment());
        MMainAdapter mainAdapter=new MMainAdapter(getSupportFragmentManager(),mfragments);
        mMmview_pager.setAdapter(mainAdapter);
        mTv_intent.setSelected(true);
        mMmview_pager.setCurrentItem(1);
    }

    private void initListener() {
        mMmview_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int resid = mMmview_pager.getCurrentItem();
                if (resid==0) {
                    mTv_intent.setSelected(true);
                    mTv_movie.setSelected(false);
                } else if (resid==1) {
                    mTv_movie.setSelected(true);
                    mTv_intent.setSelected(false);
                } else {
                    return;
                }
                }
                @Override
                public void onPageScrollStateChanged ( int state){

                }
            });
        }
    @Override
    protected void onDestroy() {
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
       startActivity(new Intent(this,SplashActivity.class));
    }
}
