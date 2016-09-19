package com.example.administrator.my_shoujiyingyin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.BaiduActivity;


public class TvbroadcastFragment extends Fragment {


    private TextView mTv_baiduliulanqi;
    private TextView mTv_qqliulanqi;
    private TextView mTv_tianmao;
    private TextView mTv_jingdong;
    private TextView mTv_qQxinwen;
    private TextView mTv_baiduxinwen;
    private TextView mTv_baiduditu;
    private TextView mTv_360yingshi;
    private TextView mTv_diditache;
    private TextView mTv_ershoufang;
    private TextView mTv_wushenzhaozilong;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvbroadcast, null);
        mTv_baiduliulanqi = (TextView) view.findViewById(R.id.tv_baiduliulanqi);
        mTv_qqliulanqi = (TextView) view.findViewById(R.id.tv_qqliulanqi);
        mTv_tianmao = (TextView) view.findViewById(R.id.tv_tianmao);
        mTv_jingdong = (TextView) view.findViewById(R.id.tv_jingdong);
        mTv_qQxinwen = (TextView) view.findViewById(R.id.tv_QQxinwen);
        mTv_baiduxinwen = (TextView) view.findViewById(R.id.tv_baiduxinwen);
        mTv_baiduditu = (TextView) view.findViewById(R.id.tv_baiduditu);
        mTv_360yingshi = (TextView) view.findViewById(R.id.tv_360yingshi);
        mTv_diditache = (TextView) view.findViewById(R.id.tv_diditache);
        mTv_ershoufang = (TextView) view.findViewById(R.id.tv_ershoufang);
        mTv_wushenzhaozilong = (TextView) view.findViewById(R.id.tv_wushenzhaozilong);
        initListener();
        return view;
    }

    private void initListener() {
        mTv_baiduliulanqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://wap.zhcw.com");
                startActivity(intent);
            }
        });
        mTv_qqliulanqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://www.qq.com/");
                startActivity(intent);
            }
        });
        mTv_tianmao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://www.baihe.com/betatest/betatest_newlandpage.html?policy=1&Channel=2345k-pc&Code=140021");
                startActivity(intent);
            }
        });
        mTv_jingdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://www.jd.com/?cu=true&utm_source=p.zhitui.com&utm_medium=tuiguang&utm_campaign=t_280580582_102924&utm_term=297a3fb8cee849f0a3f497d018751023&abt=3");
                startActivity(intent);
            }
        });
        mTv_qQxinwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://news.qq.com/");
                startActivity(intent);
            }
        });
        mTv_baiduxinwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://www.6.cn/?src=z9weij922");
                startActivity(intent);
            }
        });
        mTv_360yingshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://v.2345.com/");
                startActivity(intent);
            }
        });
        mTv_baiduditu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://chushou.tv/?hmsr=2345mz&hmpl=&hmcu=&hmkw=&hmci=");
                startActivity(intent);
            }
        });
        mTv_diditache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://www.woxiu.com/mmwall.html?p=20119094&from=offsite&wp=45&sid=splts");
                startActivity(intent);
            }
        });
        mTv_ershoufang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://www.laifeng.com/");
                startActivity(intent);
            }
        });
        mTv_wushenzhaozilong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),BaiduActivity.class);
                intent.putExtra("number","http://58.com/ershoufang/");
                startActivity(intent);
            }
        });
    }


}





