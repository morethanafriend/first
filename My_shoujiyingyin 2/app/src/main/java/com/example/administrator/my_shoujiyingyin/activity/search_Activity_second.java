package com.example.administrator.my_shoujiyingyin.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.manager.SearchHistory;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;
import com.example.administrator.my_shoujiyingyin.utils.HistoryUtils;

import java.util.List;
import java.util.Map;

public class search_Activity_second extends FragmentActivity {

    private EditText                                                   mSearch_et_search_second;
    private Button                                                     mSearch_tv_search_second;
    private List<com.example.administrator.my_shoujiyingyin.bean.data> data;
    private Button                                                     mSearch_tv_search_hotsearch3;
    private Button                                                     mSearch_tv_search_hotsearch4;
    private Button                                                     mSearch_tv_search_hotsearch5;
    private Button                                                     mSearch_tv_search_hotsearch6;
    private Button                                                     mSearch_tv_search_hotsearch7;
    private Button                                                     mSearch_tv_search_hotsearch8;
    private SimpleCursorAdapter                                        simpleCursorAdapter;
    private Button                                                     mClean;
    private ListView                                                   mLv_hostory;
    private SharedPreferences   mSp;
    private String[]            mmsearch;
    private int                 mI1;
    private Cursor              cursor;
    private Cursor              cursor1;
    private int                 id;
    private static final int DELETE_OK     = 29;
    private static final int SAFE_OK       = 30;
    private static final int DELETE_ALL_OK = 31;
    public static final String TABLE = "history";
    public static final String ID = "_id";
    public static final String RECORD = "record";
    public static final String TIME = "time";
    private     SearchHistory dbHelper;
    private     Context       mContext;
    private Handler       mhandler;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__activity_second);
        SystemApp systemApp=(SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        mContext = this;
        initView();
        autoFill();
        initListener();

    }

    private void autoFill ( ) {
        cursor = new HistoryUtils(mContext).findCursor();
        if (cursor != null) {
            simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.history_adapter, cursor,
                    new String[]{HistoryUtils.RECORD, HistoryUtils.ID},
                    new int[]{R.id.history_name_tv, R.id.history_clear_tv},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            mLv_hostory.setAdapter(simpleCursorAdapter);
        } else {
            return;
        }
    }

    private void initListener ( ) {
        mLv_hostory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                List<Map<String,Object>> data=new HistoryUtils(mContext).find();
                String record=data.get(position).get(RECORD).toString();
                Intent intent = new Intent(search_Activity_second.this, search_main_main.class);
                intent.putExtra("search", record);
                startActivity(intent);
            }
        });
        mLv_hostory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                List<Map<String,Object>> data=new HistoryUtils(mContext).find();
                String record=data.get(position).get(RECORD).toString();
                Intent intent = new Intent(search_Activity_second.this, search_main_main.class);
                intent.putExtra("search", record);
                startActivity(intent);
                finish();
            }
        });

        mLv_hostory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView<?> parent, View view,
                                            int position,  long id) {

                final HistoryUtils dbUtils = new HistoryUtils(mContext);
                dbUtils.delete((int) id);
                cursor.requery();
                autoFill();
                return false;
            }
        });
        mSearch_tv_search_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final String msearch = mSearch_et_search_second.getText().toString().trim();
                if (msearch.isEmpty()) {
                    // 构建一个吐司
                    Toast toast = new Toast(mContext);
                    View layoutView= LayoutInflater.from(mContext).inflate(R.layout.toast, null);
                    toast.setView(layoutView);
                    toast.setGravity(Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, -50, 80);
                    toast.setDuration(Toast.LENGTH_SHORT);// 设置消息的显示持续时间
                    toast.show();// 显示吐司
                    return;

                } else {
                    Intent intent = new Intent(search_Activity_second.this, search_main_main.class);
                    intent.putExtra("search", msearch);
                    startActivity(intent);
                    remenberInfo(msearch);
                    finish();

                }
            }
        });
        mSearch_tv_search_hotsearch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final String msearch = mSearch_tv_search_hotsearch3.getText().toString().trim();
                Intent intent = new Intent(search_Activity_second.this, search_main_main.class);
                intent.putExtra("search", msearch);
                startActivity(intent);
                remenberInfo(msearch);
                finish();
            }
        });
        mSearch_tv_search_hotsearch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final String msearch = mSearch_tv_search_hotsearch4.getText().toString().trim();
                Intent intent = new Intent(search_Activity_second.this, search_main_main.class);
                intent.putExtra("search", msearch);
                startActivity(intent);
                remenberInfo(msearch);
                finish();
            }
        });
        mSearch_tv_search_hotsearch5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final String msearch = mSearch_tv_search_hotsearch5.getText().toString().trim();
                Intent intent = new Intent(search_Activity_second.this, search_main_main.class);
                intent.putExtra("search", msearch);
                startActivity(intent);
                remenberInfo(msearch);
                finish();
            }
        });
        mSearch_tv_search_hotsearch6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final String msearch = mSearch_tv_search_hotsearch6.getText().toString().trim();
                Intent intent = new Intent(search_Activity_second.this, search_main_main.class);
                intent.putExtra("search", msearch);
                startActivity(intent);
                remenberInfo(msearch);
                finish();
            }
        });
        mSearch_tv_search_hotsearch7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final String msearch = mSearch_tv_search_hotsearch7.getText().toString().trim();
                Intent intent = new Intent(search_Activity_second.this, search_main_main.class);
                intent.putExtra("search", msearch);
                startActivity(intent);
                remenberInfo(msearch);
                finish();

            }
        });
        mSearch_tv_search_hotsearch8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                final String msearch = mSearch_tv_search_hotsearch8.getText().toString().trim();
                Intent intent = new Intent(search_Activity_second.this, search_main_main.class);
                intent.putExtra("search", msearch);
                startActivity(intent);
                remenberInfo(msearch);
                finish();

            }
        });
    }

    private void remenberInfo ( String msearch) {

        HistoryUtils dbUtils = new HistoryUtils(search_Activity_second.this);
        ContentValues values = new ContentValues();
        values.put(HistoryUtils.RECORD, msearch);
        dbUtils.save(values);
        cursor.requery();//假如查询数据库的话，建议开辟子线程
        simpleCursorAdapter.notifyDataSetChanged();
    }

    private void initView ( ) {
        mSearch_et_search_second = (EditText)findViewById(R.id.search_et_search_second);
        mSearch_tv_search_second = (Button) findViewById(R.id.search_tv_search_second);
        mSearch_tv_search_hotsearch3 = (Button)findViewById(R.id.search_tv_search_hotsearch3);
        mSearch_tv_search_hotsearch4 = (Button)findViewById(R.id.search_tv_search_hotsearch4);
        mSearch_tv_search_hotsearch5 = (Button)findViewById(R.id.search_tv_search_hotsearch5);
        mSearch_tv_search_hotsearch6 = (Button)findViewById(R.id.search_tv_search_hotsearch6);
        mSearch_tv_search_hotsearch7 = (Button)findViewById(R.id.search_tv_search_hotsearch7);
        mSearch_tv_search_hotsearch8 = (Button)findViewById(R.id.search_tv_search_hotsearch8);
        mLv_hostory = (ListView)findViewById(R.id.Lv_hostory);
        mSp=getSharedPreferences("info", Context.MODE_PRIVATE);
    }

    @Override
    public void onBackPressed ( ) {
       Intent intent=new Intent(this,MainMainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        if(cursor!=null){
            cursor.close();
            cursor=null;
        }
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }
}

