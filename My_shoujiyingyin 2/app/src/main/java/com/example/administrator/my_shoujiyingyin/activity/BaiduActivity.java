package com.example.administrator.my_shoujiyingyin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;

public class BaiduActivity extends AppCompatActivity {

    private WebView mWebView;
    private String E ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_baidu);
        SystemApp systemApp=(SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        mWebView = (WebView)findViewById(R.id.page_wv);
        Intent intent=getIntent();
        E= intent.getStringExtra("number");
        mWebView.loadUrl(E);
    }

    @Override
    protected void onDestroy() {
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }

}
