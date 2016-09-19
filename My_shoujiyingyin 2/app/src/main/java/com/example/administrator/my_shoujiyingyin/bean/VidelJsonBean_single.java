package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/8/20.
 * @创建时间
 * @描述   2016/8/20.
 *
 * @更新者   2016/8/20.
 * @更新时间   2016/8/20.
 * @更新描述   2016/8/20.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class VidelJsonBean_single implements Serializable {
    //    "auditionList": [
    //    {
    //        "bitRate": 32,
    //            "duration": 290000,
    //            "size": 1204800,
    //            "suffix": "m4a",
    //            "url": "http://om32.alicdn.com/648/648/2941/24385_11117405_l.m4a?auth_key=4440e88f627d5c57e3da426594cd5833-1471392000-0-null",
    //            "typeDescription": "流畅品质"
    //    },
    //    {
    //        "bitRate": 128,
    //            "duration": 290000,
    //            "size": 4655601,
    //            "suffix": "mp3",
    //            "url": "http://m5.file.xiami.com/648/648/2941/24385_11117405_l.mp3?auth_key=5741fbf4b499553e7a28de4c2fed36b9-1471392000-0-null",
    //            "typeDescription": "标准品质"
    //    },
    //    {
    //        "bitRate": 320,
    //            "duration": 290000,
    //            "size": 11639280,
    //            "suffix": "mp3",
    //            "url": "http://m6.file.xiami.com/648/648/2941/24385_11117405_h.mp3?auth_key=e30b677513453e61ebd88aceb372661b-1471392000-0-null",
    //            "typeDescription": "超高品质"
    //    }
    //    ],


    public String pages;

    public ArrayList<single_Video> data;

    public static class single_Video implements Serializable {
        public int                                  songId;
        public String                               name;
        public String                               singerName;
        public String                               picUrl;
        public ArrayList<single_Video_auditionList> auditionList;

        public static class single_Video_auditionList implements Serializable {
            public String                               url;


        }


    }
}



