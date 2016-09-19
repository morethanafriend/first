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
import com.example.administrator.my_shoujiyingyin.bean.Ablum_Video;
import com.example.administrator.my_shoujiyingyin.manager.VideoManager;

import java.io.File;
import java.util.ArrayList;

public class search_albums_Fragment_adapter_second extends BaseAdapter{
    private FragmentActivity       activity;
    private ArrayList<Ablum_Video> ablum_data;
    private TextView               mTv_ablum_title;
    private TextView               mTv_ablum_name;
    private File                   cacheFileRoot;
    private ImageView              mTv_albums_image_view;
    public search_albums_Fragment_adapter_second (FragmentActivity activity, ArrayList<Ablum_Video> ablum_data,File  cacheFileRoot) {
        this.activity = activity;
        this.ablum_data = ablum_data;
        this.cacheFileRoot=cacheFileRoot;
    }

    @Override
    public int getCount ( ) {
        return ablum_data == null ? 0 :ablum_data.size();
    }

    @Override
    public Object getItem (int position) {
        return ablum_data == null ? 0 :ablum_data.get(position);
    }
    @Override
    public long getItemId (int position) {
        return ablum_data == null ? 0 :ablum_data.get(position)._id;
    }
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        Ablum_Video mAblum_Video = ablum_data.get(position);
        convertView =(LinearLayout)View.inflate(activity, R.layout.search_ablum_layout,null);
        mTv_ablum_title = (TextView) convertView.findViewById(R.id.tv_ablum_title);
        mTv_ablum_name = (TextView) convertView.findViewById(R.id.tv_ablum_name);
        mTv_ablum_title.setText(mAblum_Video.name);
        mTv_ablum_name.setText(mAblum_Video.singer_name);
        mTv_albums_image_view= (ImageView) convertView.findViewById(R.id.search_image_view);
        String imageUrl = mAblum_Video.pic200;
        System.out.println(imageUrl);
        loadImage(imageUrl,   mTv_albums_image_view, cacheFileRoot);
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
                    Bitmap bitmap=null;
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