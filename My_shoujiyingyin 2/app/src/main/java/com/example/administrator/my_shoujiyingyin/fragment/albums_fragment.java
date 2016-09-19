package com.example.administrator.my_shoujiyingyin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.albums_single_activity;
import com.example.administrator.my_shoujiyingyin.adapter.search_albums_Fragment_adapter_second;
import com.example.administrator.my_shoujiyingyin.bean.Ablum_Video;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_ablum;
import com.example.administrator.my_shoujiyingyin.manager.VideoManager;
import com.example.administrator.my_shoujiyingyin.utils.XListView;
import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class albums_fragment extends Fragment implements  Serializable,XListView.IXListViewListener {
    private View rootView;
    private XListView mXlistview;
    protected static final int     SUCCESS_ablum   = 0;
    protected static final int     HIDE_VISIBLE  =101;
    private File                   cacheFileRoot;
    private ArrayList<Ablum_Video> ablum_data1;
    private ArrayList<Ablum_Video> ablum_data;
    private int                    _ids;
    private String                 imageUrl;
    private int n=1;
    private TextView mTv_loading;
    private LinearLayout mLl_loading;
    private Handler myHandler =new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS_ablum:
                     ablum_data1=( ArrayList<Ablum_Video>) msg.obj;
                    if (ablum_data1!=null){
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
                default:
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_albums_fragment, null);
        cacheFileRoot = getActivity().getCacheDir();
        initView();
        initData();
        initListener();
        mXlistview.setXListViewListener(this);
        return rootView ;
    }
    private void initListener ( ) {
        mXlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Ablum_Video data = ablum_data.get(position-1);
                _ids = data._id;
                imageUrl=data.pic200;
                Intent intent = new Intent(getActivity(), albums_single_activity.class);
                intent.putExtra("_ids",_ids);
                intent.putExtra("imageUrl",imageUrl);
                startActivity(intent);
            }
        });
    }
    private void initData ( ) {
        final String msearch = getFragmentManager().findFragmentByTag("179521").getArguments().getString("search_name");
        Runnable runnable_album = new Runnable() {
            @Override
            public void run ( ) {
                try {
                    String resultStr_ablum = VideoManager.album_authority(msearch);
                    System.out.println(resultStr_ablum    );
                    Message message = new Message();
                    System.out.println(resultStr_ablum);
                    Gson gson=new Gson();
                    VidelJsonBean_ablum jsonbean=gson.fromJson(resultStr_ablum,  VidelJsonBean_ablum.class);
                    ArrayList<Ablum_Video> data = jsonbean.data;
                    message.what = SUCCESS_ablum;
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
        mXlistview.setPullLoadEnable(true);
        mLl_loading.setVisibility(View.VISIBLE);
    }
private void geneItems() {
    int size=ablum_data1.size();
    ablum_data = new ArrayList<Ablum_Video>();
    if (n*10<=size) {
        for (int i = 0; i < n * 10; i++) {
            Ablum_Video mMv_Video = ablum_data1.get(i);
            ablum_data.add(mMv_Video);
            search_albums_Fragment_adapter_second albumsadapter = new search_albums_Fragment_adapter_second(getActivity(), ablum_data, cacheFileRoot);
            mXlistview.setAdapter(albumsadapter);
        }
    }else {
        ablum_data=ablum_data1;
        search_albums_Fragment_adapter_second albumsadapter = new search_albums_Fragment_adapter_second(getActivity(), ablum_data, cacheFileRoot);
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
    search_albums_Fragment_adapter_second albumsadapter = new search_albums_Fragment_adapter_second(getActivity(), ablum_data,cacheFileRoot);
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
