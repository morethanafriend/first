package com.example.administrator.my_shoujiyingyin.adapter;/*
 * @创建者   2016/8/25.
 * @创建时间
 * @描述   2016/8/25.
 *
 * @更新者   2016/8/25.
 * @更新时间   2016/8/25.
 * @更新描述   2016/8/25.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;

public class HomeAdapter extends BaseAdapter {
    // 图片的资源id数组
//    private int[] iconsId = { R.drawable.sysoptimize, R.drawable.sysoptimize,
//            R.drawable.sysoptimize, R.drawable.sysoptimize, R.drawable.sysoptimize,
//            R.drawable.sysoptimize, R.drawable.sysoptimize, R.drawable.sysoptimize,
//            R.drawable.sysoptimize };
    private int[] iconsId = { R.drawable.liudehua, R.drawable.denglijun,
            R.drawable.maikejiekexun, R.drawable.huangjiaju, R.drawable.woshigeshou,
            R.drawable.ktv, R.drawable.zhangxueyou, R.drawable.zhuangxinyan,
            R.drawable.tfboys };
    //功能项名称的数组
    private String[] names = { "一人一首成名曲", "经典怀旧", "英文专区", "粤语专区", "我是歌手", "KTV",
            "张学友", "庄心妍", "TFBOYS" };
    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.tvmovie_grid_item, parent, false);
        TextView naemTv=(TextView) itemView.findViewById(R.id.name_tv);
        ImageView iconIv=(ImageView)itemView.findViewById(R.id.icon_iv);
        BitmapFactory.Options options =new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(context.getResources(),iconsId[position],options);
        int  pictureWidth   = options.outWidth;
        int  pictureHeight  = options.outHeight;
        WindowManager wm =(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = wm.getDefaultDisplay();
        Point  outSize        =new Point();
        defaultDisplay.getSize(outSize);
        int screenWidth = outSize.x;
        int screenHeight = outSize.y;

        int dx=(10*pictureWidth)/screenWidth;
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
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),iconsId[position],options);
        naemTv.setText(names[position]);
        iconIv.setImageBitmap(bitmap);
        return itemView;
    }


}