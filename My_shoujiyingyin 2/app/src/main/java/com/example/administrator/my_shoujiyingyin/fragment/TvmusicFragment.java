package com.example.administrator.my_shoujiyingyin.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.Video;
import com.example.administrator.my_shoujiyingyin.manager.VideoManager;
import com.example.administrator.my_shoujiyingyin.activity.BaiduActivity;
import com.example.administrator.my_shoujiyingyin.adapter.MusicAdapter;
import com.example.administrator.my_shoujiyingyin.bean.danquVideo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;



public class TvmusicFragment extends Fragment {
    private View     rootView;
    private ListView mListView;
    protected static final int SUCCESS = 48;
    private ViewPager        mTv_ershoufang;
    private ArrayList<Video> mData;
    private Context          mcontext;
    private String           client_id;
    private String           size;
    private String           utdid;
    private String           s;
    private String           cpu_model;
    private String           q;
    private List<ImageView>  mImageViews;
    private int              currentPosterPosition;
    protected static final int MSG_UPDATE_IMAGE  = 49;
    protected static final int MSG_KEEP_SILENT   = 50;
    protected static final int MSG_BREAK_SILENT  = 51;
    protected static final int MSG_PAGE_CHANGED  = 52;
    protected static final long MSG_DELAY = 3000;
    private static final int POSTER_POSITION1=53;
    private static final int POSTER_POSITION2=54;
    private static final int POSTER_POSITION3=55;
    private static final int POSTER_POSITION4=56;
    private static final int POSTER_POSITION5=57;
    private static final int POSTER_POSITION6=58;
    private static final int UPDATE_CURREN_POSTER_POSITION=59;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private ImageView mImageView4;
    private ImageView mImageView5;
    private ImageView mImageView6;
    private ViewGroup parent;
    private int       currentPoster;
    private int currenItem=0;
    private WeakReference<TvmusicFragment> mWeakReference;
    private String                         msearch;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tvmusic, null);
        mTv_ershoufang = (ViewPager) rootView.findViewById(R.id.tv_ershoufang);
        mListView = (ListView) rootView.findViewById(R.id.Lv_music);
        initPage();
        initListener();
        currentPoster = POSTER_POSITION2;
        mTv_ershoufang.setCurrentItem(Integer.MAX_VALUE/2);
        myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
        mTv_ershoufang.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View view, Object obj) {
                return view==obj;
            }

            @Override
            public int getCount() {
                return  Integer.MAX_VALUE;
            }

            @Override
            public void destroyItem(ViewGroup mTv_ershoufang, int position,
                                    Object object) {

            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                position %=mImageViews.size();
                if (position<0){
                    position=mImageViews.size()+position;
                }
                ImageView view=mImageViews.get(position);
                ViewParent vp=view.getParent();
                if (vp!=null){
                    ViewGroup parent=(ViewGroup)vp;
                    parent.removeView(view);
                }
                container.addView(view);
                return view;

            }
        });
        return rootView;
    }



    private void initListener() {
        mTv_ershoufang.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                myHandler.sendMessage(Message.obtain(myHandler, MSG_PAGE_CHANGED,position,0));

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        myHandler.sendEmptyMessage(MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                        break;
                    default:
                        break;

                }
            }
        });


        mImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://h.xiami.com/music/create-key.html");
                startActivity(intent);
            }
        });
        mImageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),BaiduActivity.class);
                    intent.putExtra("number","http://www.xiami.com/collect/197053138?spm=a1z1s.6843761.1478643745.6.aaDoSQ");
                    startActivity(intent);
                }
            });


        mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://www.xiami.com/market/music/act/huangzhanmusic.php");
                startActivity(intent);
            }
        });
        mImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://alimusic.xiami.com/markets/xiami/fiona_planet");
                startActivity(intent);
            }
        });
        mImageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://alimusic.xiami.com/markets/xiami/jzl-fish-TTPOD");
                startActivity(intent);
            }
        });
        mImageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://alimusic.dongting.com/markets/dongting/kfcbreakfast");
                startActivity(intent);
            }
        });
    }


    private void initPage() {
       mImageViews=new ArrayList<ImageView>();
        mImageView1 = new ImageView(getActivity());
        mImageView1.setBackgroundResource(R.drawable.haibao1);
        mImageViews.add(mImageView1);
        mImageView2 = new ImageView(getActivity());
        mImageView2.setBackgroundResource(R.drawable.haibao2);
        mImageViews.add(mImageView2);
        mImageView3 = new ImageView(getActivity());
        mImageView3.setBackgroundResource(R.drawable.haibao3);
        mImageViews.add(mImageView3);
        mImageView4 = new ImageView(getActivity());
        mImageView4.setBackgroundResource(R.drawable.haibao4);
        mImageViews.add(mImageView4);
        mImageView5 = new ImageView(getActivity());
        mImageView5.setBackgroundResource(R.drawable.haibao5);
        mImageViews.add(mImageView5);
        mImageView6 = new ImageView(getActivity());
        mImageView6.setBackgroundResource(R.drawable.haibao6);
        mImageViews.add(mImageView6);
    }
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TvmusicFragment fragment=new TvmusicFragment();
            if (fragment==null){
                return;
            }
            if (myHandler.hasMessages(MSG_UPDATE_IMAGE)){
                myHandler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currenItem++;
                    mTv_ershoufang.setCurrentItem(currenItem);
                   myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                   myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    currenItem = msg.arg1;
                    break;
                case SUCCESS:
                     mData = (ArrayList<Video>) msg.obj;
                    for(Video temp:mData){
                        System.out.println(temp.getName());
                    }
                    MusicAdapter adpter=new MusicAdapter(mcontext,mData);
                    mListView.setAdapter(adpter);
                    break;
                case UPDATE_CURREN_POSTER_POSITION:
                    currentPoster++;
                    break;
                default:
                    break;

            }
        }
    };



    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Message message = new Message();
                String resultStr = VideoManager.single_authority(msearch);
                JSONObject jsonObject = new JSONObject(resultStr);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                ArrayList<danquVideo> data = VideoManager.parseJsonArray(jsonArray);
                message.what = SUCCESS;
                message.obj = data;
                myHandler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Thread(mRunnable).start();

        }
    };

}






