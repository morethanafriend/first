package com.example.administrator.my_shoujiyingyin.utils;/*
 * @创建者   2016/8/26.
 * @创建时间
 * @描述   2016/8/26.
 *
 * @更新者   2016/8/26.
 * @更新时间   2016/8/26.
 * @更新描述   2016/8/26.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.my_shoujiyingyin.manager.SearchHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryUtils {
        public static final String TABLE = "history";
        public static final String ID = "_id";
        public static final String RECORD = "record";
        public static final String TIME = "time";
        private static SearchHistory dbHelper=null;
    public  HistoryUtils  (Context mContext){
        if (dbHelper==null){
        dbHelper=new SearchHistory(mContext);}
        }
        public int save(ContentValues values){
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            /**插入一条记录
             * nullColumnHack:空字段回填，目的在于拼sql语句避免不报错
             * ContentValues  ：内容值，好比Hashmap   key ：字段名  value ：存字段值
             */
            long id = db.insert(TABLE, null, values);
            db.close();
            return (int)id;
        }

        // 删除 ，影响的行数
    public int delete(int id){
        int effectNum=0;//影响的行数
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        /**删除
         * whereClause：条件
         * whereArgs： 条件参数
         */
        effectNum= db.delete(TABLE, ID+"=?", new String[]{String.valueOf(id)});
        db.close();//关闭数据库
        return effectNum;
    }
        //更新 ,返回值是影响的行数,操作的是一行  ，必须要求ContentValues 要传一个id过来
        public int update(ContentValues values){
            String id=values.getAsString(ID);
            int effectNum=0;//影响的行数
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            effectNum= db.update(TABLE, values, ID+"="+id, null);
            db.close();
            return effectNum;
        }
    //查询
    public Cursor findCursor(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        return  db.query(TABLE, null, null, null, null, null, " _id desc");
        //假如返回的是游标 cursor，则不能关闭数据库
        //	   db.close();
        //	   return null;
    }
//        //查询
//        public Cursor Cursor(int _id) {
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor cursor=db.query(TABLE, new String[]{"_id,record,string"}, null, null, null, null, null);
//            while (cursor.moveToNext()){
//                SearchHistory history=new SearchHistory();
//                String record=cursor.getString(0);
//            }
//                //假如返回的是游标 cursor，则不能关闭数据库
//                //	   db.close();
//                //	   return null;
//
//        }
    //查询，返回的是List<Map>
        public List<Map<String,Object>> find(){
            List<Map<String,Object>> data=null;
            SQLiteDatabase db=dbHelper.getReadableDatabase();


            Cursor cursor = db.query(TABLE, null, null, null, null, null, "  _id desc");
            if(cursor.getCount()>0){//假如有记录
                data=new ArrayList<Map<String,Object>>();
            }
            //	    cursor.moveToFirst();
            //遍历游标，把数据存放在List中
            while(cursor.moveToNext()){
                Map<String,Object> map=new HashMap<String, Object>();
                map.put(ID, cursor.getInt(cursor.getColumnIndex(ID)));
                map.put(RECORD, cursor.getString(cursor.getColumnIndex(RECORD)));
                map.put(TIME, cursor.getInt(cursor.getColumnIndex(TIME)));
                data.add(map);
            }
            db.close();
            return  data;
        }
    }

