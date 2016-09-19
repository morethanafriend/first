package com.example.administrator.my_shoujiyingyin.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.my_shoujiyingyin.R;

public class TvintentFragment extends Fragment {
    private View     rootView;
    private ListView mMLV;
    private Context  mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tvmovie, null);


        return rootView;
        //    List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
        //    Map<String,Object>   map  =new HashMap<String,Object>();
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","百度浏览器");
        //        list.add(map);
        //        map=new HashMap<String,Object>();
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","360浏览器");
        //        list.add(map);
        //        map=new HashMap<String,Object>();
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","QQ浏览器");
        //        list.add(map);
        //        map=new HashMap<String,Object>();
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","腾讯新闻");
        //        list.add(map);
        //        map=new HashMap<String,Object>();
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","百度新闻");
        //        list.add(map);
        //        map=new HashMap<String,Object>();
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","百度地图");
        //        list.add(map);
        //        map=new HashMap<String,Object>();
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","淘宝网");
        //        list.add(map);
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","天猫网");
        //        list.add(map);
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","当当网");
        //        list.add(map);
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","京东网");
        //        list.add(map);
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","前程无忧");
        //        list.add(map);
        //        map.put("icon",R.drawable.ic_launcher);
        //        map.put("text","中华英才网");
        //        list.add(map);
        //        SimpleAdapter myAdapter=new SimpleAdapter(getActivity(),list,R.layout.adapter_intent,new String[]{"icon","text"},
        //        new int[]{R.id.iv_icon,R.id.tv_text});
        //        mMLV.setAdapter(myAdapter);
        //
        //    }
    }
}


