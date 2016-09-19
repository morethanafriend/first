package com.example.administrator.my_shoujiyingyin.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.VideoPlayerActivity;
import com.example.administrator.my_shoujiyingyin.activity.search_main_main;
import com.example.administrator.my_shoujiyingyin.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;


public class TvmovieFragment extends Fragment {
    private View     rootView;
    private ViewPager mViewPager;
    private List<ImageView> mImageView;
    protected static final int MSG_UPDATE_IMAGE  = 44;
    protected static final int MSG_KEEP_SILENT   = 45;
    protected static final int MSG_PAGE_CHANGED  = 47;
    protected static final long MSG_DELAY = 3000;
    private int currentItem;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private Button mBtn_tvmovie_gridview;
    private String search;
    private GridView mFragment_tvmovie_gridview;
    private String[] names = { "成名曲", "经典怀旧", "英文曲", "粤语", "我是歌手", "KTV",
            "张学友", "庄心妍", "TFBOYS" };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tvmovie, null);
        initView();
        initData();
        initListener();
        return rootView;

    }

    private void initListener() {
        mImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),VideoPlayerActivity.class);
                intent.setDataAndType(Uri.parse("http://otmv.alicdn.com/new/mv_1_6/60/49/60188fbd0b360bb94defffd24a8fb449.mp4?k=6869bee0e69cdf67&t=1471060446"),"video/*");
                startActivity(intent);
            }

        });
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),VideoPlayerActivity.class);
                intent.setDataAndType(Uri.parse("http://otmv.alicdn.com/new/mv_2_20/f1/14/f193dd870a2ff2f4c5fbff9d9e070714.mp4?k=0f156b08da1a93ff&t=1471060446"),"video/*");
                startActivity(intent);
            }

        });
        mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),VideoPlayerActivity.class);
                intent.setDataAndType(Uri.parse("http://otmv.alicdn.com/new/mv_1_22/b7/b0/b7ece917584ef397c4d84c3a8f0c84b0.mp4?k=90438e00c11c349e&t=1471060557"),"video/*");
                startActivity(intent);
            }

        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                myHandler.sendMessage(Message.obtain(myHandler, MSG_PAGE_CHANGED,position,0));
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
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
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TvmovieFragment fragment=new TvmovieFragment();
            if (fragment==null){
                return;
            }
            if (myHandler.hasMessages(MSG_UPDATE_IMAGE)){
                myHandler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what){
                case MSG_UPDATE_IMAGE:
                 currentItem++;
                    mViewPager.setCurrentItem(currentItem);
                    myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    };

    private void initData() {
        mImageView = new ArrayList<ImageView>();
        mImageView1 = new ImageView(getActivity());
        BitmapFactory.Options options  =new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.haibao7,options);
        int           pictureWidth   = options.outWidth;
        int           pictureHeight  = options.outHeight;
        WindowManager wm             =(WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display       defaultDisplay = wm.getDefaultDisplay();
        Point         outSize        =new Point();
        defaultDisplay.getSize(outSize);
        int screenWidth = outSize.x;
        int screenHeight = outSize.y;

        int dx=(8*pictureWidth)/screenWidth;
        int dy=(2*pictureHeight)/screenHeight;
        int scale=1;
        if(dx>dy&&dx>1){
            scale=dx;
        }
        if(dy>dx&&dy>1){
            scale=dy;
        }
        options.inSampleSize=scale;
        options.inJustDecodeBounds=false;
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.haibao7,options);
        mImageView1.setImageBitmap(bitmap);
        mImageView.add(mImageView1);
        mImageView2 = new ImageView(getActivity());
        BitmapFactory.Options options2  =new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.haibao8,options);
        int           pictureWidth2   = options.outWidth;
        int           pictureHeight2  = options.outHeight;
        WindowManager wm2             =(WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display       defaultDisplay2 = wm2.getDefaultDisplay();
        Point         outSize2        =new Point();
        defaultDisplay2.getSize(outSize2);
        int screenWidth2 = outSize2.x;
        int screenHeight2 = outSize2.y;

        int dx2=(8*pictureWidth2)/screenWidth2;
        int dy2=(2*pictureHeight2)/screenHeight2;
        int scale2=1;
        if(dx2>dy2&&dx2>1){
            scale2=dx;
        }
        if(dy2>dx2&&dy2>1){
            scale2=dy;
        }
        options2.inSampleSize=scale2;
        options2.inJustDecodeBounds=false;
        Bitmap bitmap2 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.haibao8,options2);
        mImageView2.setImageBitmap(bitmap2);
        mImageView.add(mImageView2);
        mImageView3 = new ImageView(getActivity());
        BitmapFactory.Options options3 =new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.haibao9,options);
        int           pictureWidth3   = options.outWidth;
        int           pictureHeight3  = options.outHeight;
        WindowManager wm3             =(WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display       defaultDisplay3 = wm3.getDefaultDisplay();
        Point         outSize3        =new Point();
        defaultDisplay3.getSize(outSize3);
        int screenWidth3 = outSize3.x;
        int screenHeight3 = outSize3.y;

        int dx3=(4*pictureWidth3)/screenWidth3;
        int dy3=(2*pictureHeight3)/screenHeight3;
        int scale3=1;
        if(dx3>dy3&&dx3>1){
            scale3=dx;
        }
        if(dy3>dx3&&dy3>1){
            scale3=dy;
        }
        options3.inSampleSize=scale3;
        options3.inJustDecodeBounds=false;
        Bitmap bitmap3 = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.haibao9,options3);
        mImageView3.setImageBitmap(bitmap3);
        mImageView.add(mImageView3);
        mViewPager.setCurrentItem(Integer.MAX_VALUE/2);
        myHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                position %=mImageView.size();
                if (position<0){
                    position=position+mImageView.size();
                }
                ImageView view=mImageView.get(position);
                ViewParent VP=view.getParent();
                if (VP!=null){
                    ViewGroup parent=(ViewGroup) VP;
                    parent.removeView(view);
                }
                container.addView(view);
                return view;
            }
        });
        mFragment_tvmovie_gridview.setAdapter(new HomeAdapter(getActivity()));
        mFragment_tvmovie_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                        search=names[position];
                        Intent intent = new Intent(getActivity(), search_main_main.class);
                        intent.putExtra("search",search);
                        startActivity(intent);
            }
        });

    }

    private void initView() {
        mViewPager = (ViewPager)rootView.findViewById(R.id.view_pager_MV);
        mBtn_tvmovie_gridview = (Button)rootView.findViewById(R.id.btn_tvmovie_gridview);
        mFragment_tvmovie_gridview = (GridView)rootView.findViewById(R.id.fragment_tvmovie_gridview);
    }
}