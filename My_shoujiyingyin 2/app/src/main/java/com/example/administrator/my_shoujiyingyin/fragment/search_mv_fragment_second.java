package com.example.administrator.my_shoujiyingyin.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.VideoPlayerActivity;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean;

import java.io.File;
import java.util.ArrayList;


public class search_mv_fragment_second extends AppCompatActivity {
    private String resultStr;
    private int    position;

    private ListView mSearch_mv_fragment_second;
    protected static final int SUCCESS = 34;
    protected static final int ERROR   = 35;
    private File                                     cacheFileRoot;
    private ArrayList<VidelJsonBean.MV_Video_second> mv_Data;
    private String                                   URL;
    private String                                   videoname;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mv_fragment_second);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        cacheFileRoot = getCacheDir();
        mv_Data = (ArrayList<VidelJsonBean.MV_Video_second>) getIntent().getSerializableExtra("videos");
        resultStr = (String) getIntent().getSerializableExtra("videos_data");
        mSearch_mv_fragment_second = (ListView) findViewById(R.id.search_mv_fragment_second);
//        search_MV_Fragment_adapter mvadapter = new search_MV_Fragment_adapter(search_mv_fragment_second.this, mv_Data, cacheFileRoot);
//        mSearch_mv_fragment_second.setAdapter(mvadapter);
        initListener();

    }

    private void initListener ( ) {
        mSearch_mv_fragment_second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, final int position, long id) {
                search_mv_fragment_second.this.position = position;
                parent.getItemAtPosition(position);
                int size = mv_Data.size();
                VidelJsonBean.MV_Video_second mMV_Video_second=mv_Data.get(position);
                VidelJsonBean.MV_Video_second.MV_List mv_list=new  VidelJsonBean.MV_Video_second.MV_List();
                    URL =mv_list.url;
                    videoname=mMV_Video_second.videoName;
                    Intent intent = new Intent(search_mv_fragment_second.this, VideoPlayerActivity.class);
                    intent.putExtra("videoname",videoname);
                    intent.setDataAndType(Uri.parse(URL), "video/*");
                    startActivity(intent);

            }
        });

    }
}




