package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/8/17.
 * @创建时间
 * @描述   2016/8/17.
 *
 * @更新者   2016/8/17.
 * @更新时间   2016/8/17.
 * @更新描述   2016/8/17.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VidelJsonBean implements Serializable {
        public String pages;

        public ArrayList<MV_Video_second> data;
                public static class MV_Video_second implements Serializable {
                        public int           id;
                        public String        songId;
                        public String        videoName;
                        public String        singerName;
                        public String        picUrl;
                        public String        pickCount;
                        public String        bulletCount;
                        public List<MV_List> mvList;

                        public static class MV_List implements Serializable {
                                public int  id;
                                public int    videoId;
                                public String duration;
                                public String bitRate;
                                public String path;
                                public String size;
                                public String suffix;
                                public String horizontal;
                                public String vertical;
                                public String url;
                                public String type;
                                public String typeDescription;
                                public String picUrl;


                        }


                }


        }



