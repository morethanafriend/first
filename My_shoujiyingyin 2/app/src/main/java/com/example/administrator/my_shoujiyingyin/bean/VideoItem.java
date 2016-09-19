package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/7/29.
 * @创建时间
 * @描述   2016/7/29.
 *
 * @更新者   2016/7/29.
 * @更新时间   2016/7/29.
 * @更新描述   2016/7/29.
 */

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

public class VideoItem implements Serializable{
    private String title;
    private long duration;
    private long size;
    private String path;

    public static VideoItem fromCursor(Cursor cursor) {
        VideoItem item = new VideoItem();
        item.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
        item.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
        item.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE)));
        item.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
        return item;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }



}


