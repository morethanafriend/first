package com.example.administrator.my_shoujiyingyin.manager;/*
 * @创建者   2016/8/5.
 * @创建时间
 * @描述   2016/8/5.
 *
 * @更新者   2016/8/5.
 * @更新时间   2016/8/5.
 * @更新描述   2016/8/5.
 */

import android.content.Context;
import android.net.Uri;

import com.example.administrator.my_shoujiyingyin.bean.Ablum_Video;
import com.example.administrator.my_shoujiyingyin.bean.MV_Video;
import com.example.administrator.my_shoujiyingyin.bean.MV_Video_second_play;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_MV;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_single;
import com.example.administrator.my_shoujiyingyin.bean.danquVideo;
import com.example.administrator.my_shoujiyingyin.utils.HttpUtil;
import com.example.administrator.my_shoujiyingyin.utils.MD5;
import com.example.administrator.my_shoujiyingyin.utils.StreamUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//String s, String cpu_model,String client_id,String size,String utdid,String webLogin
public class VideoManager {
    private static Context mContext;
    private String a;

    public static String single_authority (String msearch) throws Exception {
        InputStream is = HttpUtil.byGetsingle(msearch);
        if (is != null) {
            byte[] data = StreamUtil.parseInputStream(is);
            return new String(data, "UTf-8");
        }
        return null;
    }

    public static ArrayList<danquVideo> parseJsonArray (JSONArray jsonArray) throws Exception {
        ArrayList<danquVideo> data = null;
        if (jsonArray.length() > 0) {
            data = new ArrayList<danquVideo>();
        }
        int size = jsonArray.length();
        for (int i = 0; i < size; i++) {
            danquVideo video = new danquVideo();
            JSONObject item = jsonArray.getJSONObject(i);
            video.setsingerName(item.getString("singerName"));
            video.setsongId(item.getInt("songId"));
            video.setduration(item.getString("albumName"));
            video.setName(item.getString("name"));
            video.setpicUrl(item.getString("picUrl"));
            data.add(video);
        }
        return data;
    }


    public static Uri getImageFromCache (File cacheFileRoot, String imageUrl) throws Exception {
        // TODO Auto-generated method stub

        File file = new File(cacheFileRoot, MD5.getMD5(imageUrl) + imageUrl.substring(imageUrl.lastIndexOf(".")));
        // 2. 判断本地是否有该文件
        if (file.exists()) {  //3. 假如本地有，则直接返回该文件的uri
            return Uri.fromFile(file);
        } else {
            // 假如本地缓存没有，则访问网络，下载图片到本地缓存，并且返回该图片文件的uri
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");//设置请求方式是Get
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                System.out.println(inputStream);
                byte[] data = StreamUtil.parseInputStream(inputStream);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();
                inputStream.close();
                return Uri.fromFile(file);
            }
        }
        return null;
    }

    public static String mv_authority (String msearch) throws Exception {
        InputStream is = HttpUtil.byGetmv(msearch);
        if (is != null) {
            byte[] data = StreamUtil.parseInputStream(is);
            return new String(data, "UTf-8");
        }
        return null;
    }

    public static ArrayList<MV_Video> mvparseJsonArray (JSONArray jsonArray) throws Exception {
        //        ArrayList<MV_Video> data = null;
        ArrayList<MV_Video> mvList = null;
        if (jsonArray.length() > 0) {
            mvList = new ArrayList<MV_Video>();
        }
        int size = jsonArray.length();
        for (int i = 0; i < size; i++) {
            System.out.println(size);
            MV_Video video = new MV_Video();
            JSONObject item = jsonArray.getJSONObject(i);
            video.songId = item.getInt("songId");
            video.videoName = item.getString("videoName");
            video.singerName = item.getString("singerName");
            video.picUrl = item.getString("picUrl");
            JSONObject items = jsonArray.getJSONObject(0);
            video.duration = items.getString("duration");
            video.size = items.getString("size");
            video.url = items.getString("url");
            video.typeDescription = items.getString("typeDescription");
            mvList.add(video);
        }
        return mvList;

    }


    public static Uri getmvImageFromCache (File cacheFileRoot, String mv_imageUrl) throws Exception {
        // TODO Auto-generated method stub

        File file = new File(cacheFileRoot, MD5.getMD5(mv_imageUrl) + mv_imageUrl.substring(mv_imageUrl.lastIndexOf(".")));
        System.out.println(file);
        // 2. 判断本地是否有该文件
        if (file.exists()) {  //3. 假如本地有，则直接返回该文件的uri
            System.out.println("直接从缓存返回");
            return Uri.fromFile(file);
        } else {
            // 假如本地缓存没有，则访问网络，下载图片到本地缓存，并且返回该图片文件的uri
            URL url = new URL(mv_imageUrl);
            System.out.println("新的URL" + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");//设置请求方式是Get
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == 200) {
                System.out.println("从网络下载图片到本地再返回");
                InputStream inputStream = conn.getInputStream();
                System.out.println(inputStream);
                byte[] data = StreamUtil.parseInputStream(inputStream);
                System.out.println("得到网络下载图片");
                System.out.println(data);
                //                Logger.i("wzx",data);
                //写入文件中
                FileOutputStream fos = new FileOutputStream(file);

                System.out.println("path");
                //                FileOutputStream fos = mContext.openFileOutput("path", mContext.MODE_PRIVATE);
                System.out.println("打开文件家");
                fos.write(data);
                System.out.println("写入数据");
                fos.close();
                inputStream.close();
                return Uri.fromFile(file);
            }


        }

        return null;
    }

    public static ArrayList<VidelJsonBean_MV.MV_Video_final> mv_play_parseJsonArray (JSONArray mvjsonArray) throws Exception {
        ArrayList<VidelJsonBean_MV.MV_Video_final> data = null;
        if (mvjsonArray.length() > 0) {
            data = new ArrayList<VidelJsonBean_MV.MV_Video_final>();
        }
        int size = mvjsonArray.length();
        for (int i = 0; i < size; i++) {
            VidelJsonBean_MV.MV_Video_final video = new VidelJsonBean_MV.MV_Video_final();
            VidelJsonBean_MV.MV_Video_final.MV_List video1 = null;
            JSONObject item = mvjsonArray.getJSONObject(i);
            video.id = item.getInt("id");
            video.songId = item.getString("songId");
            video.videoName = item.getString("videoName");
            video.singerName = item.getString("singerName");
            video.picUrl = item.getString("picUrl");
            video.pickCount = item.getString("pickCount");
            video.bulletCount = item.getString("bulletCount");
            video1.url = item.getString("url");
            video.mvList.add(video1);
            data.add(video);
        }
        return data;
    }

    public static ArrayList<MV_Video_second_play> mvparseJsonArray_second_play (JSONArray jsonArray, int position) throws Exception {
        ArrayList<MV_Video_second_play> mvList = new ArrayList<MV_Video_second_play>();
        MV_Video_second_play video = new MV_Video_second_play();
        JSONObject item = jsonArray.getJSONObject(position);
        //        video.songId=item.getInt("songId");
        //        video.videoName=item.getString("videoName");
        //        video.singerName=item.getString("singerName");
        //        video.picUrl=item.getString("picUrl");
        video.duration = item.getString("duration");
        video.size = item.getString("size");
        video.url = item.getString("url");
        System.out.println(".......................................");
        System.out.println(video.url);
        System.out.println(".......................................");
        System.out.println(item);
        //   video.typeDescription=item.getString("typeDescription");
        mvList.add(video);

        return mvList;

    }


    public static String album_authority (String msearch) throws Exception {
        InputStream is = HttpUtil.byGet_album(msearch);
        if (is != null) {
            byte[] data = StreamUtil.parseInputStream(is);
            return new String(data, "UTf-8");
        }
        return null;
    }

    public static ArrayList<Ablum_Video> Ablum_parseJsonArray (JSONArray jsonArray) throws Exception {
        //        ArrayList<MV_Video> data = null;
        ArrayList<Ablum_Video> data = null;
        if (jsonArray.length() > 0) {
            data = new ArrayList<Ablum_Video>();
        }
        int size = jsonArray.length();
        for (int i = 0; i < size; i++) {
            System.out.println(size);
            Ablum_Video video = new Ablum_Video();
            JSONObject item = jsonArray.getJSONObject(i);
            video._id = item.getInt("_id");
            video.name = item.getString("name");
            video.pic200 = item.getString("pic200");
            video.singer_name = item.getString("singer_name");
            video.pic500 = item.getString("pic500");
            video.lang = item.getString("lang");
            video.bulletCount = item.getString("bulletCount");
            video.song_ids = item.getString("song_ids");

            data.add(video);
        }
        return data;
    }

    public static ArrayList<VidelJsonBean_single.single_Video> single_parseJsonArray (JSONArray jsonArray) throws Exception {
        //        ArrayList<MV_Video> data = null;
        ArrayList<VidelJsonBean_single.single_Video> data = null;
        if (jsonArray.length() > 0) {
            data = new ArrayList<VidelJsonBean_single.single_Video>();
        }
        int size = jsonArray.length();
        for (int i = 0; i < size; i++) {
            System.out.println(size);
            VidelJsonBean_single.single_Video video = new VidelJsonBean_single.single_Video();
            JSONObject item = jsonArray.getJSONObject(i);
            video.name = item.getString("name");
            video.songId = item.getInt("songId");
            video.singerName = item.getString("singerName");
            video.picUrl = item.getString("picUrl");
            //            public String        songId;
            //            public String        name;
            //            public String        singerName;
            //            public String        picUrl;

            data.add(video);
        }
        return data;
    }

    public static String album_single_authority (int ids) throws Exception {
        InputStream is = HttpUtil.byGet_album_sing(ids);
        if (is != null) {
            byte[] data = StreamUtil.parseInputStream(is);
            return new String(data, "UTf-8");
        }
        return null;
    }

//    public static Uri getMusicFromCache (File music, String url) throws Exception {
//        // TODO Auto-generated method stub
//
//        File file = new File(music,"music");
//        URL murl=new URL(url);
//        System.out.println("..................................................................................");
//        System.out.println("...............murl..................."+murl);
//        System.out.println("..................................................................................");
//        HttpURLConnection conn = (HttpURLConnection)murl.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setConnectTimeout(5000);
//        if (conn.getResponseCode() == 200) {
//            InputStream inputStream = conn.getInputStream();
//            byte[] data = StreamUtil.parseInputStream(inputStream);
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(data);
//            System.out.println("..................................................................................");
//            System.out.println("..............data.................."+data+"..............fos.................."+fos);
//            System.out.println("..................................................................................");
//            fos.close();
//            inputStream.close();
//            return Uri.fromFile(file);
//        }
//        return Uri.fromFile(file);
//    }
}
//    public static ArrayList<VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List> Ablum_single_parseJsonArray (JSONArray jsonArray) throws Exception {
//        //        ArrayList<MV_Video> data = null;
//        ArrayList<VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List> data = null;
//        if (jsonArray.length() > 0) {
//            data = new ArrayList<VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List>();
//        }
//        int size = jsonArray.length();
//        for (int i = 0; i < size; i++) {
//            System.out.println(size);
//            VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List video = new VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List();
//            JSONObject item = jsonArray.getJSONObject(i);
//            video.name = item.getString("name");
//            video.songid = item.getInt("id");
//            video.singerName = item.getString("singerName");
//            video.picUrl = item.getString("picUrl");
//            data.add(video);
//            public int           id;
//            public String        name;
//            public String        singerName;
//            public String        lang;
////            public String        picUrl;
//
//        }
//        return data;
//    }
//}

