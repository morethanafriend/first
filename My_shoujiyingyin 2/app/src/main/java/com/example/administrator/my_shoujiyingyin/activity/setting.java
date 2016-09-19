package com.example.administrator.my_shoujiyingyin.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

public class setting extends AppCompatActivity {
    @OnClick(R.id.time)
    public void time(View v){
        setTime();
    }
    @OnClick(R.id.blendao)
    public void blendao(View v) {
        blenDao();
    }
    @OnClick(R.id.tv_return)
    public void mreturn(View v) {
        Intent intent=new Intent(setting.this,MainMainActivity.class);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.location)
    public void location(View v) {
        Intent intent=new Intent(setting.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private File cacheFileRoot;
    private  static  final int FINISH=40;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setting);
        SystemApp systemApp=(SystemApp) getApplication();//Application对象 应用是单例的
        systemApp.addStack(this);
        cacheFileRoot=getCacheDir();
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case FINISH:
                    setting.this.finishAffinity();
                    break;
                default:
                    break;
            }
        }
    };
    private void setTime ( ) {
        new AlertDialog.Builder(setting.this).setIcon(R.drawable.ic_launcher)
                .setTitle("睡眠定时")
                .setItems(R.array.sleep_time, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                handler.sendEmptyMessageDelayed(FINISH, 10 * 60 * 1000);
                                Toast.makeText(setting.this, "10分钟后退出应用", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                handler.sendEmptyMessageDelayed(FINISH, 20 * 60 * 1000);
                                Toast.makeText(setting.this, "20分钟后退出应用", Toast.LENGTH_LONG).show();
                                break;
                            case 3:
                                handler.sendEmptyMessageDelayed(FINISH, 30 * 60 * 1000);
                                Toast.makeText(setting.this, "30分钟后退出应用", Toast.LENGTH_LONG).show();
                                break;
                            case 4:
                                handler.sendEmptyMessageDelayed(FINISH, 1*60 * 60 * 1000);
                                Toast.makeText(setting.this, "1小时后退出应用", Toast.LENGTH_LONG).show();
                                break;
                            case 5:
                                handler.sendEmptyMessageDelayed(FINISH, 90*60 * 60 * 1000);
                                Toast.makeText(setting.this, "1.5小时后退出应用", Toast.LENGTH_LONG).show();
                                break;
                            case 6:
                                handler.sendEmptyMessageDelayed(FINISH, 120 * 60 * 1000);
                                Toast.makeText(setting.this, "2小时后退出应用", Toast.LENGTH_LONG).show();
                                break;
                            case 7:
                                final Dialog mdialog=new Dialog(setting.this,R.style.MyDialogTheme);
                                mdialog.setContentView(R.layout.diy_dialog);
                                final EditText typeEdt=(EditText) mdialog.findViewById(R.id.type_et);
                                Button okBtn=(Button)mdialog.findViewById(R.id.ok_btn);
                                Button noBtn=(Button) mdialog.findViewById(R.id.button2);
                                okBtn.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        String type = typeEdt.getText().toString().trim();
                                        Long time=Long.parseLong(type);
                                        handler.sendEmptyMessageDelayed(FINISH, time*60*1000);
                                        Toast.makeText(setting.this,type+ "分钟后退出应用", Toast.LENGTH_LONG).show();
                                        mdialog.dismiss();
                                    }
                                });
                                noBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick (View v) {
                                        mdialog.dismiss();
                                    }
                                });
                                mdialog.show();
                                break;
                            default:
                                break;
                        }
                    }
                }).create().show();
    }
    private void blenDao ( ) {
        new AlertDialog.Builder(setting.this).setCancelable(false).setTitle("清除缓存").setIcon(R.drawable.ic_launcher)
                .setMessage("清除缓存能提高运行速度，减少死机现象，是否清除缓存")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        if(cacheFileRoot.exists()) {
                            for (File file : cacheFileRoot.listFiles()) {
                                file.delete();
                            }
                        }
                    }}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }
    @Override
    public void onBackPressed ( ) {
        Intent intent=new Intent(this,MainMainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }

}
