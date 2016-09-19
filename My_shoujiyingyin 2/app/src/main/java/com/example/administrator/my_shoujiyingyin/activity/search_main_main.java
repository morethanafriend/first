package com.example.administrator.my_shoujiyingyin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.adapter.MMainAdapter;
import com.example.administrator.my_shoujiyingyin.fragment.Mv_Fragment;
import com.example.administrator.my_shoujiyingyin.fragment.albums_fragment;
import com.example.administrator.my_shoujiyingyin.fragment.single_Fragment;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;

import java.util.ArrayList;

public class search_main_main extends FragmentActivity {

    private TextView mEt_search_main_main;
    private TextView mTv_search_main_main;
    private TextView mTv_search_main_main_single;
    private TextView mTv_search_main_main_mv;
    private TextView mTv_search_main_main_cluam;
    private ViewPager mMmview_pager_main;
    private String search_name;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main_main);
        SystemApp systemApp=(SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        initView();
        initListener();
        initData();
    }
    private void initData() {
        search_name=( String)getIntent().getSerializableExtra("search");
        transaction=this.getSupportFragmentManager().beginTransaction();
        albums_fragment malbums_fragment = new albums_fragment();
        Bundle bundle = new Bundle();
        bundle.putString("search_name",search_name);
        malbums_fragment.setArguments(bundle);
        System.out.println(bundle);
        transaction.add(malbums_fragment, "179521");
        transaction.commit();
        ArrayList<Fragment> mfragments=new ArrayList<android.support.v4.app.Fragment>();
        mfragments.add(new single_Fragment());
        mfragments.add(new Mv_Fragment());
        mfragments.add(new albums_fragment());
        MMainAdapter mainAdapter=new MMainAdapter(getSupportFragmentManager(),mfragments);
        mMmview_pager_main.setAdapter(mainAdapter);
        mTv_search_main_main_single.setSelected(true);
        mMmview_pager_main.setCurrentItem(2);

    }

    private void initListener() {
        mEt_search_main_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent(search_main_main.this,search_Activity_second.class);
                startActivity(intent);
                finish();
            }
        });
        mTv_search_main_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent=new Intent(search_main_main.this,search_Activity_second.class);
                startActivity(intent);
                finish();
            }
        });
        mTv_search_main_main_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMmview_pager_main.setCurrentItem(0);
                single_Fragment single_Fragment = new single_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("search_name",search_name);
                single_Fragment.setArguments(bundle);
                System.out.println(bundle);
                transaction.add(single_Fragment, "179521");
            }
        });

        mTv_search_main_main_mv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMmview_pager_main.setCurrentItem(1);
                Mv_Fragment mmv_Fragment = new Mv_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("search_name",search_name);
                mmv_Fragment.setArguments(bundle);
                System.out.println(bundle);
                transaction.add(mmv_Fragment, "179521");


            }
        });


        mTv_search_main_main_cluam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMmview_pager_main.setCurrentItem(2);
            }
        });

        mMmview_pager_main.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int resid = mMmview_pager_main.getCurrentItem();
                if (resid==0) {
                    mTv_search_main_main_single.setSelected(true);
                    mTv_search_main_main_mv.setSelected(false);
                    mTv_search_main_main_cluam.setSelected(false);
                }else if (resid==1) {
                    mTv_search_main_main_single.setSelected(false);
                    mTv_search_main_main_mv.setSelected(true);
                    mTv_search_main_main_cluam.setSelected(false);
                } else if (resid==2) {
                    mTv_search_main_main_single.setSelected(false);
                    mTv_search_main_main_mv.setSelected(false);
                    mTv_search_main_main_cluam.setSelected(true);
                } else {
                    return;
                }
            }

            @Override
            public void onPageScrollStateChanged ( int state){

            }
        });
    }
    private void initView() {
        mEt_search_main_main = (TextView)findViewById(R.id.et_search_main_main);
        mTv_search_main_main = (TextView)findViewById(R.id.tv_search_main_main);
        mTv_search_main_main_single = (TextView)findViewById(R.id.tv_search_main_main_single);
        mTv_search_main_main_mv = (TextView)findViewById(R.id.tv_search_main_main_MV);
        mTv_search_main_main_cluam = (TextView)findViewById(R.id.tv_search_main_main_cluam);
        mMmview_pager_main = (ViewPager)findViewById(R.id.mmview_pager_main);


    }
    @Override
    protected void onDestroy() {
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed ( ) {
        Intent intent=new Intent(search_main_main.this,MainMainActivity.class);
        startActivity(intent);
        finish();
    }
}


