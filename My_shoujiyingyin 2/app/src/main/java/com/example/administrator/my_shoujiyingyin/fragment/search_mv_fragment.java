package com.example.administrator.my_shoujiyingyin.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.MV_Video;

import java.io.File;
import java.util.ArrayList;


public class search_mv_fragment extends AppCompatActivity {
    private File                cacheFileRoot;
    private ArrayList<MV_Video> mv_Data;
    private ListView            mSearch_mv_fragment;
    private int                 mposition;
    protected static final int     SUCCESS   = 32;
    protected static final int     ERROR     = 33;
    private Handler myHandler =new Handler(){
        public void handleMessage(Message msg) {
            //处理从子线程传递过来的消息 ，从消息队列取Message
            switch (msg.what) {
                case SUCCESS:
                    ArrayList<MV_Video> data1=(ArrayList<MV_Video>)msg.obj;
                    MV_Video mMV_Video=data1.get(mposition);
//                    mMV_Video.getduration();
//                    mMV_Video.geturl();
//                    Intent intent=new Intent(search_mv_fragment.this,VideoPlayerActivity.class);
//                    intent.setDataAndType(Uri.parse(mMV_Video.geturl()),"video/*");
//                    startActivity(intent);
                    finish();//销毁当前的Activity
                    break;
                case ERROR:
                    Toast.makeText(search_mv_fragment.this, msg.obj.toString(), Toast.LENGTH_LONG).show();

                default:
                    break;

            }

        };
    };
    private String mMv_list;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mv_fragment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        cacheFileRoot = getCacheDir();
        mv_Data = (ArrayList<MV_Video>) getIntent().getSerializableExtra("mv_videos");
        mMv_list = (String)getIntent().getSerializableExtra("mv_list");
        mSearch_mv_fragment = (ListView)findViewById(R.id.search_mv_fragment);
//        search_MV_Fragment_adapter mvadapter = new search_MV_Fragment_adapter(this, mv_Data, cacheFileRoot);
//        mSearch_mv_fragment.setAdapter(mvadapter);
        mSearch_mv_fragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                search_mv_fragment.this.mposition=position;
                final Object mmposition=parent.getItemAtPosition(position);
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run ( ) {
//                        //
//                        try {
//                            //解析json数据
//                            JSONObject jsonObject = new JSONObject();
//                            Message message = new Message();
//                            if (mmposition!=null) {
//                                message.what = SUCCESS;
//
//                                System.out.println( "点击了按钮");
////                                JSONArray mvjsonArray = jsonObject.getJSONArray("mv_list");
//                                //把json数组转换为ArrayList<Video>   ,解析json数组
//                                ArrayList<MV_Video> data1 = null;
//                                if (mvjsonArray.length() > 0) {
//                                    data1 = new ArrayList<MV_Video>();
//                                }
//                                int size = mvjsonArray.length();
//                                for (int i = 0; i < size; i++) {
//                                    MV_Video video = new MV_Video();
//                                    JSONObject item = mvjsonArray.getJSONObject(i);
//                                    //            video.setsingerName(item.getString("singerName"));
//                                    //            video.setName(item.getString("videoName"));
//                                    //            video.setpicUrl(item.getString("picUrl"));
////                                    video.seturl(item.getString("url"));
////                                    video.setduration(item.getString("duration"));
//                                    data1.add(video);
//                                }
//                                message.obj = data1;
//                                Logger.i("wzx",data1.toString());
//
//                            } else {
//                                message.what = ERROR;//验证失败
//                                String msg = jsonObject.getString("msg");
//                                message.obj = msg;
//                            }
//                            myHandler.sendMessage(message);
//                            // 向主线程发消息
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                };
//
//                new Thread(runnable).start();
//

            }
        });
    }


}



