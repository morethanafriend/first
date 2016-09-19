package com.example.administrator.my_shoujiyingyin.adapter;/*
 * @创建者   2016/8/5.
 * @创建时间
 * @描述   2016/8/5.
 *
 * @更新者   2016/8/5.
 * @更新时间   2016/8/5.
 * @更新描述   2016/8/5.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.Video;

import java.io.File;
import java.util.ArrayList;



public class MusicAdapter extends BaseAdapter {
    private Context          context;
    private ArrayList<Video> mData;
    private File             cacheFileRoot;//缓存图片的路径
    public MusicAdapter(Context context, ArrayList<Video> mData){
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
        return mData==null?0:mData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video=mData.get(position);
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
        holderView.tv_size.setText(video.getLength()+"");
        holderView.tv_duration.setText(video.getName()+"");
        return convertView;

    }

    private static class HolderView{
        ImageView image_view;
        TextView tv_title;
        TextView tv_duration;
        TextView tv_size;
    }
}

