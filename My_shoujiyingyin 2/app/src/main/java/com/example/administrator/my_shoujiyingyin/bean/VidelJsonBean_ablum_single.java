package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/8/21.
 * @创建时间
 * @描述   2016/8/21.
 *
 * @更新者   2016/8/21.
 * @更新时间   2016/8/21.
 * @更新描述   2016/8/21.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class VidelJsonBean_ablum_single implements Serializable{
        public Ablum_Video_SINGLE data;
        public static class Ablum_Video_SINGLE implements Serializable {
            public String        picUrl;

            public ArrayList<SONG_List> songList;

            public  class SONG_List implements Serializable {
                public int           songid;
                public String        name;
                public String        singerName;
                public String        lang;
                public String        picUrl;
                public ArrayList<Audition_List> auditionList;

                public  class Audition_List implements Serializable {
                             public String url;
            }
        }
    }
}
//"data": {
//        "songList": [
//                    {
//                    "songId": 41809273,
//                    "name": "爱你一万年 (DJ）",
//                    "singerName": "小林杰",

//                     "alias": "",
//                     "remarks": "",
//
//                     "auditionList": [
//
//                                     "url": "http://om32.alicdn.com/931/2026470931/2100242770/1775348344_59392424_l.m4a?auth_key=a9a0dec0c83a845ba20f279689a4295f-1471824000-0-null",
//                                     ],
//                     "urlList":      [
//
//
//        "url": "http://m6.file.xiami.com/931/2026470931/2100242770/1775348344_59392424_h.mp3?auth_key=29421936a92a06a71de71f2d677157f4-1471824000-0-null",
//        "typeDescription": "超高品质"
//
//                                     ],
//
//                      "mvList": [],
//
//                       "outLinks": [],
//                       "rightKey": {
//
//                                    "songRights": [
//
//                                                     ],
//
//                                     "musicPackage": [],
//                                     "albumPackage": [],
//
//
//                                       "singers": [
//                                                     ]
