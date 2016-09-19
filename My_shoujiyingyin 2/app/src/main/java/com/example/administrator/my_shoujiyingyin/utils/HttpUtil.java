package com.example.administrator.my_shoujiyingyin.utils;/*
 * @创建者   2016/8/5.
 * @创建时间
 * @描述   2016/8/5.
 *
 * @更新者   2016/8/5.
 * @更新时间   2016/8/5.
 * @更新描述   2016/8/5.
 */

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {
    // 1. http-get请求   请求参数拼在url 的后面
    public static InputStream byGetsingle (String msearch) throws Exception {
        StringBuilder urlStr = new StringBuilder();
        urlStr.append("http://api.dongting.com/misc/search/song?s=5s200&cpu_model=Qualcomm%2BMSM8974PRO-AC&client_id=5ca4397a4d9ec69e2846f84ef734f3e&size=200&utdid=AKdB8i%2F4920DAKBH5iEMN1Oz&q=");
        urlStr.append(URLEncoder.encode(msearch, "UTF-8"));
        urlStr.append("&cpu=msm8974&v=v8.2.0.2015091720&imei=353608067966567&ram=2846004%2BkB&page=1&imsi=460010943505374&agent=none&hid=5203054419073033&net=2&app=ttpod&alf=alf700633&splus=5.0.2%252F21&api_version=1.0&active=0&tid=0&f=f1&resolution=1080x1776&uid=353608067966567&language=zh&user_id=0");
        URL url = new URL(urlStr.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");//设置请求方式是Get
        conn.setConnectTimeout(5000);
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 返回码等于200
            return conn.getInputStream();
        }

        return null;
    }

    public static InputStream byGetmv (String mv_search) throws Exception {
        System.out.println("GET请求");
        // url :http://localhost:8080/web/video_login?account=android&pwd=1234
        StringBuilder urlStr = new StringBuilder();
        urlStr.append("http://so.ard.iyyin.com/s/video?q=");
        urlStr.append(URLEncoder.encode(mv_search, "UTF-8"));
        urlStr.append("&page=1&size=200&app=ttpod&v=v8.0.1.2015091618&uid=&mid=iPhone5S&f=f320&s=s310&imsi=&hid=&splus=8.3&active=1&net=2&openudid=860ccbc510f46e0aff7c7cc455817c7913cdab85&idfa=411A267C-638E-4AED-9055-06B0ABD1CABC&utdid=VihNE4X5eYkDABVfV7lYLAKa&alf=201200&bundle_id=com.ttpod.music&latitude=&longtitude=");
        URL urlMv = new URL(urlStr.toString());
        HttpURLConnection conn = (HttpURLConnection) urlMv.openConnection();
        conn.setRequestMethod("GET");//设置请求方式是Get
        conn.setConnectTimeout(5000);
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 返回码等于200
            return conn.getInputStream();
        }

        return null;

    }

    // 通过http 的post请求
    public static InputStream byPost (String account, String pwd, String webLogin) throws Exception {
        URL url = new URL(webLogin);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String body = "account=" + URLEncoder.encode(account, "UTF-8") + "&pwd=" + URLEncoder.encode(pwd, "UTF-8");
        conn.setRequestProperty("Content-Length", body.getBytes().length + "");
        conn.setDoOutput(true);
        conn.getOutputStream().write(body.getBytes());
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }

        return null;
    }

    // 通过HttpClient 访问服务端
    public static InputStream byHttpClient (String account, String pwd,
                                            String webLogin) throws Exception {
        System.out.println("byHttpClient");
        /**模拟面向对象的网络访问方式
         *
         */
        // 1. 准备一个http客户端
        HttpClient httpClient = new DefaultHttpClient();
        //		HttpGet httpGet=new HttpGet();
        // 2. 设置请求方式   http://localhost:8080/web/video_login
        HttpPost httpPost = new HttpPost(webLogin);
        //3.设置请求体 请求的内容
        // 设置请求参数   NameValuePair: 名值对 ，键值对
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("account", account));
        params.add(new BasicNameValuePair("pwd", pwd));
        //		HttpEntity entity;
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        httpPost.setEntity(entity);

        // 4. 执行请求,取得返回的结果
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 访问网络成功
            //5. 取得返回的内容体 ，以流的形式返回
            return httpResponse.getEntity().getContent();
        }
        return null;
    }

    public static InputStream byGet_album (String msearch) throws Exception {
        System.out.println("GET请求");
        // url :http://localhost:8080/web/video_login?account=android&pwd=1234
        StringBuilder urlStr = new StringBuilder();
        urlStr.append("http://so.ard.iyyin.com/albums/search?q=");
        urlStr.append(URLEncoder.encode(msearch, "UTF-8"));
        urlStr.append("&page=1&size=200&app=ttpod&v=v8.0.1.2015091618&uid=&mid=iPhone5S&f=f320&s=s310&imsi=&hid=&splus=8.3&active=1&net=2&openudid=860ccbc510f46e0aff7c7cc455817c7913cdab85&idfa=411A267C-638E-4AED-9055-06B0ABD1CABC&utdid=VihNE4X5eYkDABVfV7lYLAKa&alf=201200&bundle_id=com.ttpod.music&latitude=&longtitude=");
        URL urlMv = new URL(urlStr.toString());
        HttpURLConnection conn = (HttpURLConnection) urlMv.openConnection();
        conn.setRequestMethod("GET");//设置请求方式是Get
        conn.setConnectTimeout(5000);
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 返回码等于200
            return conn.getInputStream();
        }

        return null;
    }

    public static InputStream byGet_album_sing (int ids) throws Exception {

        System.out.println("GET请求");
        // url :http://localhost:8080/web/video_login?account=android&pwd=1234
        StringBuilder urlStr = new StringBuilder();
        urlStr.append("http://api.dongting.com/song/album/");
        urlStr.append(ids);
        urlStr.append("?app=ttpod&v=v8.0.1.2015091618&uid=&mid=iPhone5S&f=f320&s=s310&imsi=&hid=&splus=8.3&active=1&net=2&openudid=860ccbc510f46e0aff7c7cc455817c7913cdab85&idfa=411A267C-638E-4AED-9055-06B0ABD1CABC&utdid=VihNE4X5eYkDABVfV7lYLAKa&alf=201200&bundle_id=com.ttpod.music&latitude=&longtitude=");
        URL urlMv = new URL(urlStr.toString());
        HttpURLConnection conn = (HttpURLConnection) urlMv.openConnection();
        conn.setRequestMethod("GET");//设置请求方式是Get
        conn.setConnectTimeout(5000);
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) { // 返回码等于200
            return conn.getInputStream();
        }

        return null;
    }
}
