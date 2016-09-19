package com.example.administrator.my_shoujiyingyin.fragment;/*
 * @创建者   2016/8/20.
 * @创建时间
 * @描述   2016/8/20.
 *
 * @更新者   2016/8/20.
 * @更新时间   2016/8/20.
 * @更新描述   2016/8/20.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.VideoPlayerActivityOnLine;
import com.example.administrator.my_shoujiyingyin.adapter.search_mv_Fragment_adapter_second;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_MV;
import com.example.administrator.my_shoujiyingyin.manager.VideoManager;
import com.example.administrator.my_shoujiyingyin.utils.StreamUtil;
import com.example.administrator.my_shoujiyingyin.utils.XListView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Mv_Fragment extends android.support.v4.app.Fragment  implements Serializable,XListView.IXListViewListener {
    private View     rootView;
    private XListView mXListView;
    protected static final int     SUCCESS_MV   = 26;
    protected static final int     HIDE_VISIBLE  =101;
    private File                                       cacheFileRoot;
    private String                                         mURL;
    private String                                         singer;
    private String                                         name;
    private ArrayList<VidelJsonBean_MV.MV_Video_final> mv_data;
    private ArrayList<VidelJsonBean_MV.MV_Video_final> items;
    private String                                         URL;
    protected static final int     SUCCESS_DOWN_OK   = 27;
    protected static final int     SUCCESS_DOWN_FALSE  = 28;
    private ArrayList<VidelJsonBean_MV.MV_Video_final> mMv_listss;
    private TextView mTv_loading;
    private LinearLayout mLl_loading;
    private int n=1;
        private Handler myHandler =new Handler(){
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case SUCCESS_MV:

                        mv_data=(ArrayList<VidelJsonBean_MV.MV_Video_final>) msg.obj;
                        if (mv_data!=null){
                        geneItems();
                        mLl_loading.setVisibility(View.GONE);}
                        else {
                            return;
                        }
                        break;
                    case SUCCESS_DOWN_OK:
                        Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_LONG).show();
                        break;
                    case  SUCCESS_DOWN_FALSE:
                        Toast.makeText(getActivity(),"下载失败",Toast.LENGTH_LONG).show();
                        break;
                    case  HIDE_VISIBLE:
                        mLl_loading.setVisibility(View.GONE);
                        mTv_loading.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        };
    private search_mv_Fragment_adapter_second mAlbumsadapter;

    @Nullable
        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.activity_albums_fragment, null);
            cacheFileRoot = getActivity().getCacheDir();
            initView();
            initData();
            initListener();
            mXListView.setXListViewListener(this);
            return rootView;
        }
private void initListener ( ) {
    mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
            ArrayList<VidelJsonBean_MV.MV_Video_final.MV_List> mv_lists = mMv_listss.get(position-1).mvList;
            VidelJsonBean_MV.MV_Video_final.MV_List mvList = mv_lists.get(0);
            URL = mvList.url;
            Intent intent = new Intent(getActivity(),VideoPlayerActivityOnLine.class);
            intent.setDataAndType(Uri.parse(URL), "video/*");
            intent.putExtra("videoname", mv_data.get(position).videoName);
            startActivity(intent);
        }
    });
    mXListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {
            mURL = mMv_listss.get(position-1).mvList.get(0).url;
            singer = mMv_listss.get(position-1).singerName;
            name = mMv_listss.get(position-1).videoName;
            new AlertDialog.Builder(getActivity()).setCancelable(false).setIcon(R.drawable.ic_launcher)
                    .setMessage("是否下载MV")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick (DialogInterface dialog, int which) {
                            download();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick (DialogInterface dialog, int which) {
                }
            }).create().show();
            return true;
        }
    });
}
    private void download ( ) {
        Runnable runnable_MV = new Runnable() {
            @Override
            public void run ( ) {
                String rootPath = Environment.getExternalStorageDirectory()+"/KuwoMusic/mvDownload/";
                Message message = new Message();
                try{
                    java.net.URL murl = new URL(mURL);
                    HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
                    File file=new File(rootPath,singer+name+".mp4");
                    InputStream input=conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] data = StreamUtil.parseInputStream(input);
                    fos.write(data);
                    fos.close();
                    input.close();
                    message.what = SUCCESS_DOWN_OK;
                    myHandler.sendMessage(message);
                }catch (Exception e) {
                    myHandler.sendEmptyMessage(HIDE_VISIBLE);
                }
            }
        };new Thread(runnable_MV).start();
    }
    private void initData ( ) {
            final String msearch = getFragmentManager().findFragmentByTag("179521").getArguments().getString("search_name");
            Runnable runnable_album = new Runnable() {
                @Override
                public void run ( ) {
                    try {
                        String resultStr_mv = VideoManager.mv_authority(msearch);
                        Message message = new Message();
                        Gson gson=new Gson();
                        VidelJsonBean_MV jsonbean=gson.fromJson(resultStr_mv,  VidelJsonBean_MV.class);
                        ArrayList<VidelJsonBean_MV.MV_Video_final> data = jsonbean.data;
                        message.what=SUCCESS_MV;
                                message.obj=data;
                        myHandler.sendMessage(message);
                    } catch (Exception e) {
                        myHandler.sendEmptyMessage(HIDE_VISIBLE);
                    }
                }
            };new Thread(runnable_album).start();
        }
        private void initView ( ) {
            mXListView = (XListView)rootView.findViewById(R.id.xListView);
            mTv_loading = (TextView)rootView.findViewById(R.id.tv_loading);
            mLl_loading = (LinearLayout) rootView.findViewById(R.id.ll_loading);
            mXListView.setPullLoadEnable(true);
            mLl_loading.setVisibility(View.VISIBLE);
        }
    private void geneItems() {
        int size=mv_data.size();
        mMv_listss = new ArrayList<VidelJsonBean_MV.MV_Video_final>();
        if (n*10<=size) {
            for (int i = 0; i < n * 10; i++) {
                VidelJsonBean_MV.MV_Video_final mMv_Video = mv_data.get(i);
                mMv_listss.add(mMv_Video);
                search_mv_Fragment_adapter_second albumsadapter = new search_mv_Fragment_adapter_second(getActivity(), mMv_listss,cacheFileRoot);
                mXListView.setAdapter(albumsadapter);
            }
        }else {
            mMv_listss= mv_data;
            search_mv_Fragment_adapter_second albumsadapter = new search_mv_Fragment_adapter_second(getActivity(), mMv_listss,cacheFileRoot);
            mXListView.setAdapter(albumsadapter);
            Toast.makeText(getActivity(),"没有更多内容显示了",Toast.LENGTH_LONG).show();
        }

    }
    private void onLoad() {
        mXListView.stopRefresh();
        mXListView.stopLoadMore();
    }
    @Override
    public void onRefresh() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAlbumsadapter = new search_mv_Fragment_adapter_second(getActivity(), mMv_listss,cacheFileRoot);
                mXListView.setAdapter(mAlbumsadapter);
                onLoad();
            }
        }, 2000);
    }
    @Override
    public void onLoadMore() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                n=++n;
                geneItems();
                onLoad();
            }
        }, 2000);
    }
}




