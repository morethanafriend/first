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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.AudioPlayerActivity;
import com.example.administrator.my_shoujiyingyin.adapter.search_single_Fragment_adapter;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_single;
import com.example.administrator.my_shoujiyingyin.interfaces.Keys;
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

public class single_Fragment extends android.support.v4.app.Fragment implements Serializable,XListView.IXListViewListener {
    private View     rootView;
    private XListView mXlistview;
    private String singer;
    private String mURL;
    private String name;
    protected static final int     SUCCESS_single   = 41;
    private File                                         cacheFileRoot;
    private ArrayList<VidelJsonBean_single.single_Video> single_data;
    private ArrayList<VidelJsonBean_single.single_Video> mSingle_listss;
    protected static final int     SUCCESS_DOWN_FALSE_SINGLE   = 42;
    protected static final int     HIDE_VISIBLE  =101;
    protected static final int     SUCCESS_DOWN_OK_SINGLE_NULL  =100;
    private TextView mTv_loading;
    private LinearLayout mLl_loading;
    private int n=1;
    private Handler myHandler =new Handler(){
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SUCCESS_single:

                        single_data=( ArrayList<VidelJsonBean_single.single_Video>) msg.obj;
                    if (single_data!=null){
                         geneItems();
                    mLl_loading.setVisibility(View.GONE);}
                    else {
                        return;
                    }
                    break;
                case  HIDE_VISIBLE:
                    mLl_loading.setVisibility(View.GONE);
                    mTv_loading.setVisibility(View.VISIBLE);
                    break;
                case  SUCCESS_DOWN_FALSE_SINGLE:
                    Toast.makeText(getActivity(),"下载失败",Toast.LENGTH_LONG).show();
                    break;
                case SUCCESS_DOWN_OK_SINGLE_NULL:
                    Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        };
    };
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_albums_fragment, null);
        cacheFileRoot = getActivity().getCacheDir();
        initView();
        initData();
        initListener();
        mXlistview.setXListViewListener(this);
        return rootView;
    }
    private void initListener ( ) {
        mXlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AudioPlayerActivity.class);
                intent.putExtra(Keys.ITEM, mSingle_listss);
                intent.putExtra(Keys.POSITION, position-1);
                startActivity(intent);
            }
        });
        mXlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {
                mURL =  mSingle_listss.get(position-1).auditionList.get(0).url;
                singer =  mSingle_listss.get(position-1).singerName;
                name =  mSingle_listss.get(position-1).name;

                new AlertDialog.Builder(getActivity()).setCancelable(false).setIcon(R.drawable.ic_launcher)
                        .setMessage("是否下载歌曲")
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
        Runnable runnable_album_S = new Runnable() {
            @Override
            public void run ( ) {
                String rootPath = Environment.getExternalStorageDirectory()+"/KuwoMusic/music/";
                Message message = new Message();
                try{
                    java.net.URL murl = new URL(mURL);
                    HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
                    File file=new File(rootPath,singer+name+".mp3");
                    InputStream input=conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] data = StreamUtil.parseInputStream(input);
                    fos.write(data);
                    fos.close();
                    input.close();
                    message.what = SUCCESS_DOWN_OK_SINGLE_NULL;
                    myHandler.sendMessage(message);
                }catch (Exception e) {
                    e.printStackTrace();
                    message.what = SUCCESS_DOWN_FALSE_SINGLE;
                    myHandler.sendMessage(message);
                }
            }
        };new Thread(runnable_album_S).start();
    }
    private void initData ( ) {
        final String msearch = getFragmentManager().findFragmentByTag("179521").getArguments().getString("search_name");
        Runnable runnable_album = new Runnable() {
            @Override
            public void run ( ) {
                try {

                    String resultStr_single = VideoManager.single_authority(msearch);
                    Message message = new Message();
                    Gson gson=new Gson();
                    VidelJsonBean_single jsonbean=gson.fromJson(resultStr_single,VidelJsonBean_single.class);
                    ArrayList<VidelJsonBean_single.single_Video> data = jsonbean.data;
                    message.what = SUCCESS_single;
                    message.obj=data;
                    myHandler.sendMessage(message);
                } catch (Exception e) {
                    myHandler.sendEmptyMessage(HIDE_VISIBLE);

                }

            }
        };new Thread(runnable_album).start();
    }

    private void initView ( ) {
        mXlistview = (XListView)rootView.findViewById(R.id.xListView);
        mTv_loading = (TextView)rootView.findViewById(R.id.tv_loading);
        mLl_loading = (LinearLayout) rootView.findViewById(R.id.ll_loading);
        mLl_loading.setVisibility(View.VISIBLE);
        mXlistview.setPullLoadEnable(true);
    }
    private void geneItems() {
        int size = single_data.size();
        mSingle_listss = new ArrayList<VidelJsonBean_single.single_Video>();
        if (n * 10 <= size) {
            for (int i = 0; i < n * 10; i++) {
                VidelJsonBean_single.single_Video mSingle_Video = single_data.get(i);
                mSingle_listss.add(mSingle_Video);
            }
            search_single_Fragment_adapter albumsadapter =
                    new search_single_Fragment_adapter(getActivity(), mSingle_listss, cacheFileRoot);
            mXlistview.setAdapter(albumsadapter);
        } else {
            mSingle_listss = single_data;
            search_single_Fragment_adapter albumsadapter =
                    new search_single_Fragment_adapter(getActivity(), mSingle_listss, cacheFileRoot);
            mXlistview.setAdapter(albumsadapter);
            Toast.makeText(getActivity(),"没有更多内容显示了",Toast.LENGTH_LONG).show();
        }
    }
    private void onLoad() {
        mXlistview.stopRefresh();
        mXlistview.stopLoadMore();
    }
    @Override
    public void onRefresh() {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                search_single_Fragment_adapter albumsadapter =
                        new search_single_Fragment_adapter(getActivity(), mSingle_listss,cacheFileRoot);
                mXlistview.setAdapter(albumsadapter);
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
