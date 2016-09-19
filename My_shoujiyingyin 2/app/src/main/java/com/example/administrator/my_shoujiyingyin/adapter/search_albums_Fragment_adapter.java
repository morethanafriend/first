package com.example.administrator.my_shoujiyingyin.adapter;/*
 * @创建者   2016/8/19.
 * @创建时间
 * @描述   2016/8/19.
 *
 * @更新者   2016/8/19.
 * @更新时间   2016/8/19.
 * @更新描述   2016/8/19.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.Ablum_Video;

import java.util.ArrayList;

public class search_albums_Fragment_adapter extends BaseAdapter {
    private Context                mContent;
    private ArrayList<Ablum_Video> ablum_data;
    private TextView               mTv_ablum_title;
    private TextView               mTv_ablum_name;

    public search_albums_Fragment_adapter (Context mContent, ArrayList<Ablum_Video> ablum_data) {
        this.mContent = mContent;
        this.ablum_data = ablum_data;
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
        //        return mData==null?0:mData.get(position).Id;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        Ablum_Video mAblum_Video = ablum_data.get(position);
            convertView = LayoutInflater.from(mContent).inflate(R.layout.search_ablum_layout, parent, false);
            mTv_ablum_title = (TextView) convertView.findViewById(R.id.tv_title);
            mTv_ablum_name = (TextView) convertView.findViewById(R.id.tv_name);
            mTv_ablum_title.setText(mAblum_Video.name);
            mTv_ablum_name.setText(mAblum_Video.singer_name);
            return convertView;



    }
}
