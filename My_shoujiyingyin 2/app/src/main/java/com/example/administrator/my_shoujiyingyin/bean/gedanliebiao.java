package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/8/15.
 * @创建时间
 * @描述   2016/8/15.
 *
 * @更新者   2016/8/15.
 * @更新时间   2016/8/15.
 * @更新描述   2016/8/15.
 */

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.manager.VideoManager;

import java.io.File;
import java.util.ArrayList;

public class gedanliebiao  extends BaseAdapter {
    private Context               context;
    private ArrayList<danquVideo> mData;
    private File                  cacheFileRoot;//缓存图片的路径
    public gedanliebiao(Context context, ArrayList<danquVideo> mData,File cacheFileRoot){
        this.context=context;
        this.mData=mData;
        this.cacheFileRoot=cacheFileRoot;
    }
    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData==null?0:mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData==null?0:mData.get(position).getsongId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        danquVideo mdanquVideo=mData.get(position);
        HolderView holderView=null;
        if (convertView==null){
            holderView=new HolderView();
            convertView= LayoutInflater.from(context).inflate(R.layout.layoutadapter_media_list, parent, false);
            holderView.tv_title=(TextView)convertView.findViewById(R.id.tv_title);
            holderView.tv_duration=(TextView)convertView.findViewById(R.id.tv_duration);
            holderView.tv_size=(TextView)convertView.findViewById(R.id.tv_size);
            holderView.image_view=(ImageView) convertView.findViewById(R.id.image_view);
            convertView.setTag(holderView);
        }else{
            holderView=(HolderView) convertView.getTag();
        }
        holderView.tv_size.setText(mdanquVideo.getsingerName());
        holderView.tv_title.setText(mdanquVideo.getName());
        String imageUrl=mdanquVideo.getpicUrl();
        System.out.println(imageUrl);
        loadImage(imageUrl,holderView.image_view,cacheFileRoot);
        return convertView;

    }

    private void loadImage ( final String imageUrl,  final ImageView image_view, final File cacheFileRoot) {
        new AsyncTask<String, Integer, Uri>(){
            protected void onPreExecute ( ) {
                System.out.println("onPreExecute");

            };

            @Override
            protected Uri doInBackground (String... params) {
                System.out.println("doInBackground");
                try{
                    return VideoManager.getImageFromCache(cacheFileRoot,imageUrl);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            };

            @Override
            protected void onPostExecute (Uri result) {
                System.out.println("onPostExecute");
                if (result!=null){
                    image_view.setImageURI(result);
                }
            }
        }.execute();
    }

    private static class HolderView{
        ImageView image_view;
        TextView  tv_title;
        TextView  tv_duration;
        TextView  tv_size;
    }
}