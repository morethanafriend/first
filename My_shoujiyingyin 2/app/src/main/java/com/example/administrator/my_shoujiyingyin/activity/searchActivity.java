package com.example.administrator.my_shoujiyingyin.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.MV_Video_seconds;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean;
import com.example.administrator.my_shoujiyingyin.fragment.search_mv_fragment;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;
import com.example.administrator.my_shoujiyingyin.manager.VideoManager;
import com.example.administrator.my_shoujiyingyin.utils.Logger;
import com.google.gson.Gson;

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {

    private EditText mSearch_et_search;
    private Button   mSearch_tv_search;
    private Context  mContext;
    protected static final int     SUCCESS   = 36;
    protected static final int     ERROR     = 37;
    protected static final int     SUCCESS2   = 38;
    private   Handler myHandler =new Handler(){
        public void handleMessage(Message msg) {
            //处理从子线程传递过来的消息 ，从消息队列取Message
            switch (msg.what) {
//                case SUCCESS:
//                    ArrayList<danquVideo> mData=(ArrayList<danquVideo>) msg.obj;
//                    //进入到VideoListActivity
//                    Intent intent=new Intent(searchActivity.this,search_single_music_fragment.class);
//                    intent.putExtra("videos", mData);
//                    startActivity(intent);
//                    finish();//销毁当前的Activity
//
//                    break;
                case SUCCESS:
                    ArrayList<MV_Video_seconds> mv_Data=(ArrayList<MV_Video_seconds>)msg.obj;
                    Intent mv_intent=new Intent(searchActivity.this,search_mv_fragment.class);
                    mv_intent.putExtra("mv_videos", mv_Data);
//                    MV_Video video = new MV_Video();
//                    String mv_list=video.getduration();
//                    mv_intent.putExtra("mv_list", mv_list);
                    startActivity(mv_intent);
                    finish();//销毁当前的Activity
                    break;
                case ERROR:
                    Toast.makeText(searchActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();

                default:
                    break;

            }

        };
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SystemApp systemApp=(SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        initView();
        initListener();
    }

    private void initListener ( ) {
        mSearch_tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final String msearch = mSearch_et_search.getText().toString().trim();


                Runnable runnable = new Runnable() {
                    @Override
                    public void run ( ) {
//                         在业务类取实现验证
//                        try {
//                                                    String resultStr = VideoManager.authority(msearch);
//
//                                                    //解析json数据
//                                                    JSONObject jsonObject = new JSONObject(resultStr);
//                                                    Message message = new Message();
//                                                    if (resultStr!=null) {
//                                                        message.what = SUCCESS;
//                                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                                        //把json数组转换为ArrayList<Video>   ,解析json数组
//                                                        ArrayList<danquVideo> data = VideoManager.parseJsonArray(jsonArray);
//                                                        message.obj = data;
//                                                       Logger.i("wzx",data.toString());
                        try {
                            String resultStr = VideoManager.mv_authority(msearch);

                            Gson gson=new Gson();
                            VidelJsonBean jsonbean = gson.fromJson(resultStr, VidelJsonBean.class);
                            Message message = new Message();
                            if (resultStr!=null) {
                                message.what = SUCCESS;
                               ArrayList<VidelJsonBean.MV_Video_second> data=jsonbean.data;
//                                ArrayList<MV_Video> mvList= jsonbean.mvList;
                                message.obj = data;
                                Logger.i("wzx",data.toString());

                            } else {
                                message.what = ERROR;//验证失败
                                message.obj = ERROR;
                            }
                            myHandler.sendMessage(message);
                            // 向主线程发消息
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };

                new Thread(runnable).start();


            }
            });
    }





    private void initView ( ) {
        mSearch_et_search = (EditText)findViewById(R.id.search_et_search);
        mSearch_tv_search = (Button) findViewById(R.id.search_tv_search);
    }
    @Override
    protected void onDestroy() {
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }
}
