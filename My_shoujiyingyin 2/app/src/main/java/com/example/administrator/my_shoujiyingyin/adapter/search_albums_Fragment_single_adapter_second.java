package com.example.administrator.my_shoujiyingyin.adapter;/*
 * @创建者   2016/8/21.
 * @创建时间
 * @描述   2016/8/21.
 *
 * @更新者   2016/8/21.
 * @更新时间   2016/8/21.
 * @更新描述   2016/8/21.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_ablum_single;
import com.example.administrator.my_shoujiyingyin.manager.VideoManager;
import com.example.administrator.my_shoujiyingyin.activity.albums_single_activity;

import java.io.File;

public class search_albums_Fragment_single_adapter_second extends BaseAdapter {
    private com.example.administrator.my_shoujiyingyin.activity.albums_single_activity albums_single_activity;
    private VidelJsonBean_ablum_single.Ablum_Video_SINGLE                              ablum_single_data;
    private File                                                                       cacheFileRoot;
    private TextView                                                                   mTv_ablum_title;
    private TextView                                                                   mTv_ablum_name;
    private ImageView                                                                  mTv_albums_image_view;
    public search_albums_Fragment_single_adapter_second (albums_single_activity albums_single_activity,
                                                         VidelJsonBean_ablum_single.Ablum_Video_SINGLE ablum_single_data,
                                                         File cacheFileRoot) {
        this.albums_single_activity = albums_single_activity;
        this.ablum_single_data = ablum_single_data;
        this.cacheFileRoot=cacheFileRoot;
    }
    @Override
    public int getCount ( ) {
        return ablum_single_data == null ? 0 :ablum_single_data.songList.size();
    }
    @Override
    public Object getItem (int position) {
        return ablum_single_data == null ? 0 :ablum_single_data.songList.get(position);
    }
    @Override
    public long getItemId (int position) {
        return ablum_single_data== null ? 0 :ablum_single_data.songList.get(position).songid;
    }
    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List ablum_single = ablum_single_data.songList.get(position);
        convertView =(LinearLayout) android.view.View.inflate(albums_single_activity, R.layout.search_ablum_layout,null);
        mTv_ablum_title = (TextView) convertView.findViewById(R.id.tv_ablum_title);
        mTv_ablum_name = (TextView) convertView.findViewById(R.id.tv_ablum_name);
        mTv_ablum_title.setText(ablum_single.name);
        mTv_ablum_name.setText(ablum_single.singerName);
        mTv_albums_image_view= (ImageView) convertView.findViewById(R.id.search_image_view);
        String imageUrl = ablum_single.picUrl;
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
                    WindowManager wm             =(WindowManager) albums_single_activity.getSystemService(Context.WINDOW_SERVICE);
                    Display       defaultDisplay = wm.getDefaultDisplay();
                    Point         outSize        =new Point();
                    defaultDisplay.getSize(outSize);
                    int screenWidth = outSize.x;
                    int screenHeight = outSize.y;

                    int dx=(3*pictureWidth)/screenWidth;
                    int dy=(10*pictureHeight)/screenHeight;
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
