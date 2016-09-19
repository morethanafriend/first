package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/8/1.
 * @创建时间
 * @描述   2016/8/1.
 *
 * @更新者   2016/8/1.
 * @更新时间   2016/8/1.
 * @更新描述   2016/8/1.
 */

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

public class AudioItem implements Serializable {
    private String title;
    private long duration;
    private long size;
    private String path;
    private String artist;
    private int album;
    public static AudioItem fromCursor(Cursor cursor) {
        AudioItem item = new AudioItem();
        item.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
        item.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
        item.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
        item.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
        item.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));

        return item;
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
       this.size=size;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }


}


