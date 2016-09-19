package com.example.administrator.my_shoujiyingyin.manager;/*
 * @创建者   2016/8/25.
 * @创建时间
 * @描述   2016/8/25.
 *
 * @更新者   2016/8/25.
 * @更新时间   2016/8/25.
 * @更新描述   2016/8/25.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SearchHistory extends SQLiteOpenHelper{
    private static final String historyrecord = "record";
    private static final int VERSION =44;//数据库版本
    private Context mContext;
    public SearchHistory(Context mContext) {
        super(mContext, historyrecord, null, VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL("create table history(_id integer primary key autoincrement,record text,time string)");

    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists history");
            db.execSQL("create table history(_id integer primary key autoincrement,record text,time string)");
    }
}
