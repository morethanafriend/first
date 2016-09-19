package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/8/18.
 * @创建时间
 * @描述   2016/8/18.
 *
 * @更新者   2016/8/18.
 * @更新时间   2016/8/18.
 * @更新描述   2016/8/18.
 */

import java.util.List;

public class data {
private  long                                                        id;
private String                                                       songId;
private String                                                       videoName;
private  String                                                      picUrl;
private String                                                       pickCount;
private String                                                       singerName;
private String                                                       bulletCount;
private List<com.example.administrator.my_shoujiyingyin.bean.mvList> mvList;

    public long getId ( ) {
        return id;
    }
    public void setId (long id ) {
        this.id=id;
    }

    public String getSongId ( ) {
        return songId;
    }

    public void setSongId (String songId ) {
        this.songId=songId;
    }

    public String getVideoName ( ) {
        return videoName;
    }
    public void setVideoName (String videoName) {
        this.videoName=videoName;
    }

    public String getPicUrl ( ) {
        return picUrl;
    }
    public void setPicUrl (String picUrl) {
        this.picUrl=picUrl;
    }

    public void setPickCount (String pickCount) {
        this.pickCount = pickCount;
    }

    public String getPickCount ( ) {
        return pickCount;
    }

    public void setSingerName (String singerName) {
        this.singerName = singerName;
    }

    public String getSingerName ( ) {
        return singerName;
    }

    public void setBulletCount (String bulletCount) {
        this.bulletCount = bulletCount;
    }

    public String getBulletCount ( ) {
        return bulletCount;
    }

    public List<mvList> getMvList ( ) {
        return mvList;
    }

    public void setMvList (List<mvList> mvList) {
        this.mvList = mvList;
    }
}
