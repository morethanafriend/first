package com.example.administrator.my_shoujiyingyin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.AudioPlayerActivity;
import com.example.administrator.my_shoujiyingyin.bean.danquVideo;
import com.example.administrator.my_shoujiyingyin.bean.gedanliebiao;

import java.io.File;
import java.util.ArrayList;

public class search_single_music_fragment extends AppCompatActivity {
    private ListView              mGedanliebaio;
    private ArrayList<danquVideo> mData;
    private File                  cacheFileRoot;//缓存图片的路径

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_single_music_fragment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        cacheFileRoot = getCacheDir();
        mGedanliebaio = (ListView) findViewById(R.id.gedanliebaio);
        mData = (ArrayList<danquVideo>) getIntent().getSerializableExtra("videos");
        gedanliebiao adapter = new gedanliebiao(this, mData, cacheFileRoot);
        mGedanliebaio.setAdapter(adapter);
        mGedanliebaio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(search_single_music_fragment.this,AudioPlayerActivity.class);
                startActivity(intent);


            }
        });
    }


}