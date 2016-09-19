package com.example.administrator.my_shoujiyingyin.adapter;/*
 * @创建者   2016/8/25.
 * @创建时间
 * @描述   2016/8/25.
 *
 * @更新者   2016/8/25.
 * @更新时间   2016/8/25.
 * @更新描述   2016/8/25.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.search_Activity_second;

import java.util.Map;

public class historyadapter extends BaseAdapter {
    private search_Activity_second search_activity_second;
    private Map<String, ?>         all;

    public historyadapter (search_Activity_second search_activity_second, Map<String, ?> all) {
        this.search_activity_second = search_activity_second;
        this.all = all;
        System.out.println(">>>>>>>>>>>>>>>>>>all;;>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ all);

    }

    @Override
    public int getCount ( ) {
        return all == null ? 0 :all.size();
    }

    @Override
    public Object getItem (int position) {
        return all == null ? 0 :all.get(position);
    }

    @Override
    public long getItemId (int position) {
        return position;
        //        return mData==null?0:mData.get(position).Id;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        all.get("msearch");
        View itemView = LayoutInflater.from(search_activity_second).inflate(R.layout.history_adapter, parent, false);
        TextView history_name_tv = (TextView) itemView.findViewById(R.id.history_name_tv);
        all.get(position);
        history_name_tv.setText(all.get("msearch").toString());
        return itemView;

    }
}
