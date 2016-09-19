package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/8/27.
 * @创建时间
 * @描述   2016/8/27.
 *
 * @更新者   2016/8/27.
 * @更新时间   2016/8/27.
 * @更新描述   2016/8/27.
 */

import android.database.Cursor;

import com.example.administrator.my_shoujiyingyin.bean.danquVideo;

import java.io.Serializable;

public class Historybean implements Serializable {
    private int    songId;
    private String name;
    private String singerName;//
    private String    duration; //
    private String picUrl;//图片的url地址
    private String url;

    public void setpicUrl (String picUrl) {
        this.picUrl = picUrl;
    }

    public String getpicUrl ( ) {
        return picUrl;
    }

    public int getsongId ( ) {
        return songId;
    }

    public void setsongId (int songId) {
        this.songId = songId;
    }

    public String getsingerName ( ) {
        return singerName;
    }

    public void setsingerName (String singerName) {
        this.singerName = singerName;
    }
    public String getName ( ) {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getduration ( ) {
        return duration;
    }

    public void setduration (String duration) {
        this.duration = duration;
    }
    public void seturl (String url) {
        this.url = url;
    }

    public String geturl ( ) {
        return url;
    }

    public static danquVideo fromCursor (Cursor cursor) {
        danquVideo item = new danquVideo();
        item.setpicUrl(cursor.getString(1));
        item.setsongId(cursor.getInt(2));
        item.setsingerName(cursor.getString(3));
        item.setName(cursor.getString(4));
        item.setduration(cursor.getString(5));
        item.seturl(cursor.getString(6));
        return item;
    }
}
