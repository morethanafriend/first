package com.example.administrator.my_shoujiyingyin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.interfaces.Contants;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends Activity {
    private Handler mhandler;
    protected static final int MSG_SHOW_DIALOG = 1;// 显示升级对话框标记
    protected static final int MSG_ENTER_HOME = 2;// 进入home界面标记
    protected static final int MSG_SERVER_ERROR = 3;// 访问服务端错误标记
    private ProgressBar mProgressBar;// 下载apk进度条
    private TextView    mProcessTv;// 下载百分比TextView
    private int         newVersionCode;// 服务端版本号
    private String      apkUrl;// 服务端apk的url地址
    private String      versionDes;// 服务端版本描述
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ENTER_HOME:
                    EnterHome();// 进入到HomeActivity界面
                    break;
                case MSG_SHOW_DIALOG:
                    showUpdateDialog();
                    break;
                case MSG_SERVER_ERROR:

                    EnterHome();// 进入到HomeActivity界面
                    break;

                default:
                    break;
            }

        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SystemApp systemApp=(SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        initView();
        delayEnterHome();

    }

/**
 *显示升级对话框
 */
    private void showUpdateDialog ( ) {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher)
                .setTitle("新版本号: " + newVersionCode).setMessage("升级应用，更好的使用体验")
                .setPositiveButton("升级", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 下载 apk
                        downloadApk();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 进入到mainActivity界面
                        EnterHome();
                    }
                }).create().show();

    }


    /**
     * 下载APK
     */
    protected void downloadApk() {
        // 用Xutils - httpUtils模块来实现
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(apkUrl, getExternalCacheDir().getAbsolutePath()
                + "/safe.apk", new RequestCallBack<File>() {
            // 网络访问成功 ，返回码 200 ,该方法在主线程运行
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                installApk();

            }

            // 访问网络失败，该方法在主线性运行
            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(SplashActivity.this,"下载异常",Toast.LENGTH_LONG).show();
                EnterHome();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                //设置进度
                mProgressBar.setVisibility(View.VISIBLE);//可见
                mProgressBar.setMax((int)total);//设置进度条的最大值
                mProgressBar.setProgress((int)current);//设置当前进度
                //设置百分比的文本提示
                float currentF=(float)current;//转换为单精度的值
                int process=(int) ((currentF/total)*100);
                mProcessTv.setText(process+"/100");
            }
        });
    }

    // 安装新下载的apk
    protected void installApk() {
        // 调用系统的安装器来安装软件
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);// 查看
        intent.addCategory(Intent.CATEGORY_DEFAULT);// 添加要访问的组件类别 ，Activity的类别
        /**
         * 设置要传输的数据 data ：uri ：格式的数据 type ：数据的MIME类型
         *
         */
        intent.setDataAndType(
                Uri.fromFile(new File(getExternalCacheDir(), "safe.apk")),
                "application/vnd.android.package-archive");
        startActivityForResult(intent, 6);// 要求返回结果
    }

    /**
     * 回调函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 希望安装完毕后，跳转到HomeActivity界面
/*		if (requestCode == 6 && resultCode == RESULT_OK) { // setResult finish
			enterHome();
		}*/
        EnterHome();
    }

    /**
     * 数据初始化
     */
    private void initView ( ) {
        mProgressBar = (ProgressBar) findViewById(R.id.download_apk_pb);
        mProcessTv = (TextView) findViewById(R.id.process_tv);
    }

    private void delayEnterHome() {
        mhandler =new Handler();
            mhandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EnterHome();
                }
            },2000);
        }
    private void EnterHome() {
        Intent mintent=new Intent();
        mintent.setClass(this,MainMainActivity.class);
        startActivity(mintent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case(MotionEvent.ACTION_DOWN):
                mhandler.removeCallbacksAndMessages(null);
                EnterHome();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 检测是否有版本更新
     */
    private void checkUpdate() {
        new Thread() {
            public void run() {
                long startTime = System.currentTimeMillis(); // 取得开始访问网络时间
                // 访问网络
                Message message = new Message();
                try {
                    URL url = new URL(Contants.SERVER_VERSION_URL);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setRequestMethod("GET");
                    int resultCode = conn.getResponseCode();
                    if (resultCode == HttpURLConnection.HTTP_OK) {// 访问url正常
                        InputStream is = conn.getInputStream(); // 获取输入流
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(is));
                        String jsonResult = reader.readLine();
                        // 解析json数据
                        JSONObject jsonObject = new JSONObject(jsonResult);
                        newVersionCode = jsonObject.getInt("code");// 取得服务端的版本号
                        apkUrl = jsonObject.getString("apkurl");// 取得apk的服务端下载地址
                        versionDes = jsonObject.getString("des");// 取得版本描述

                        // 比较服务端的版本号是否比当前的版本号新
                        if (newVersionCode > getVersionCode()) {
                            // 显示一个是否要更新的对话框
                            message.what = MSG_SHOW_DIALOG;
                        } else {
                            message.what = MSG_ENTER_HOME; // 没有新版本，则直接进入HomeActivity
                        }
                    } else { // 访问服务端有异常
                        message.what = MSG_SERVER_ERROR;
                    }
                } catch (Exception e) {
                    message.what = MSG_SERVER_ERROR;
                    e.printStackTrace();
                } finally {
                    long durationTime = System.currentTimeMillis() - startTime;// 访问网络耗时
                    // 0.5秒
                    if (durationTime < 2000) { // 小于两秒 ，则休眠，显示splash界面 满足2秒钟
                        SystemClock.sleep(2000 - durationTime);
                    }

                    // 向主线程发消息
                    handler.sendMessage(message);
                }

            }
        }.start();

    }

    /**
     * 获取版本信息
     * @return
     */
    private int getVersionCode ( ) {
        // 取得包管理器
        PackageManager packageManager = getPackageManager();
        /**
         * 取得包信息 packagename:当前应用的包名
         */
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), PackageManager.GET_ACTIVITIES);
            return packageInfo.versionCode;// 取得版本号
        } catch (Exception e) {

        }
        return 0;
    }

    /**
     * 退出程序
     */
    @Override
    protected void onDestroy() {
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }

}
