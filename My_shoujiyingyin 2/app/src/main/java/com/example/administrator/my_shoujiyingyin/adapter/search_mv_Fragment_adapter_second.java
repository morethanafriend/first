package com.example.administrator.my_shoujiyingyin.adapter;/*
 * @创建者   2016/8/20.
 * @创建时间
 * @描述   2016/8/20.
 *
 * @更新者   2016/8/20.
 * @更新时间   2016/8/20.
 * @更新描述   2016/8/20.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_MV;
import com.example.administrator.my_shoujiyingyin.manager.VideoManager;

import java.io.File;
import java.util.ArrayList;

public class search_mv_Fragment_adapter_second extends BaseAdapter {
    private  FragmentActivity                           activity;
    private  ArrayList<VidelJsonBean_MV.MV_Video_final> mv_data;
    private  TextView                                   mTv_MV_title;
    private  TextView                                   mTv_MV_name;
    private  File                                       cacheFileRoot;
    private ImageView                                   mTv_mv_image_view;
    private String                                      mUrl;
    private int n;

    public search_mv_Fragment_adapter_second (FragmentActivity activity, ArrayList<VidelJsonBean_MV.MV_Video_final> mMv_listss,File cacheFileRoot) {
        this.activity = activity;
        this.mv_data = mMv_listss;
        this.cacheFileRoot=cacheFileRoot;
    }
        @Override
        public int getCount ( ) {
            return mv_data == null ? 0 :mv_data.size();
        }

        @Override
        public Object getItem (int position) {
            return mv_data == null ? 0 :mv_data.get(position);
        }

        @Override
        public long getItemId (int position) {
            return mv_data == null ? 0 :mv_data.get(position).id;
            //        return mData==null?0:mData.get(position).Id;
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {
            VidelJsonBean_MV.MV_Video_final mMv_Video = mv_data.get(position);
//            VidelJsonBean_MV.MV_Video_final.MV_List video1=null;
            convertView =(LinearLayout) android.view.View.inflate(activity, R.layout.search_ablum_layout,null);
            mTv_MV_title = (TextView) convertView.findViewById(R.id.tv_ablum_title);
            mTv_MV_name = (TextView) convertView.findViewById(R.id.tv_ablum_name);
            mTv_MV_title.setText(mMv_Video.videoName);
            mTv_MV_name.setText(mMv_Video.singerName);
            mTv_mv_image_view = (ImageView) convertView.findViewById(R.id.search_image_view);
            String imageUrl = mMv_Video.picUrl;
            System.out.println(imageUrl);
            loadImage(imageUrl,   mTv_mv_image_view, cacheFileRoot);
            return convertView;
        }
    private void loadImage (final String imageUrl, final ImageView mTv_single_image_view, final File cacheFileRoot) {
        new AsyncTask<String, Integer, Uri>() {
            protected void onPreExecute ( ) {
            }
            @Override
            protected Uri doInBackground (String... params) {
                try {
                    return VideoManager.getImageFromCache(cacheFileRoot, imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute (Uri result) {
                if (result != null) {
                    BitmapFactory.Options options =new BitmapFactory.Options();
                    options.inJustDecodeBounds=true;
                    String url=result.toString().substring(result.toString().indexOf("data"),result.toString().lastIndexOf("."));
                    if (BitmapFactory.decodeFile("/"+url+".jpg",options)!=null){
                        BitmapFactory.decodeFile("/"+url+".jpg",options);
                    }else if(BitmapFactory.decodeFile("/"+url+".png",options)!=null){
                        BitmapFactory.decodeFile("/"+url+".png",options);}
                    else {
                        BitmapFactory.decodeFile("/" + url + ".jpeg", options);
                    }
                    int           pictureWidth   = options.outWidth;
                    int           pictureHeight  = options.outHeight;
                    WindowManager wm             =(WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
                    Display       defaultDisplay = wm.getDefaultDisplay();
                    Point         outSize        =new Point();
                    defaultDisplay.getSize(outSize);
                    int screenWidth = outSize.x;
                    int screenHeight = outSize.y;

                    int dx=(30*pictureWidth)/screenWidth;
                    int dy=(40*pictureHeight)/screenHeight;
                    int scale=1;
                    if(dx>dy&&dx>1){
                        scale=dx;
                    }
                    if(dy>dx&&dy>1){
                        scale=dy;
                    }
                    options.inSampleSize=scale;
                    options.inJustDecodeBounds=false;
                    Bitmap bitmap =null;
                    if (BitmapFactory.decodeFile("/"+url+".jpg",options)!=null){
                        bitmap= BitmapFactory.decodeFile("/"+url+".jpg",options);
                    }else if(BitmapFactory.decodeFile("/"+url+".png",options)!=null){
                        bitmap=BitmapFactory.decodeFile("/"+url+".png",options);}
                    else {
                        bitmap=BitmapFactory.decodeFile("/"+url+".jpeg",options);
                    }
                    mTv_single_image_view.setImageBitmap(bitmap);
                }
            }
        }.execute();
    }
}