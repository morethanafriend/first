package com.example.administrator.my_shoujiyingyin.adapter;/*
 * @创建者   2016/7/29.
 * @创建时间
 * @描述   2016/7/29.
 *
 * @更新者   2016/7/29.
 * @更新时间   2016/7/29.
 * @更新描述   2016/7/29.
 */

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.bean.VideoItem;
import com.example.administrator.my_shoujiyingyin.utils.Utils;

public class VideoListAdapter extends CursorAdapter {
    public VideoListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view =(LinearLayout)View.inflate(context, R.layout.layoutadapter_media_list,null);
        ViewHolder mHolder=new ViewHolder();
        mHolder.tv_title=(TextView)view.findViewById(R.id.tv_title);
        mHolder.tv_duration=(TextView)view.findViewById(R.id.tv_duration);
        mHolder.tv_size=(TextView)view.findViewById(R.id.tv_size);
        view.setTag(mHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder mHolder=(ViewHolder)view.getTag();
        VideoItem item=VideoItem.fromCursor(cursor);
        mHolder.tv_title.setText(item.getTitle());
        mHolder.tv_duration.setText(Utils.formatMillis(item.getDuration()));
        mHolder.tv_size.setText(Formatter.formatFileSize(context, item.getSize()));

    }

class ViewHolder {
    TextView tv_title;
    TextView tv_duration;
    TextView tv_size;
}

}
