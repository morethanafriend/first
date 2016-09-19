package com.example.administrator.my_shoujiyingyin.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.adapter.search_albums_Fragment_single_adapter_second;
import com.example.administrator.my_shoujiyingyin.bean.VidelJsonBean_ablum_single;
import com.example.administrator.my_shoujiyingyin.interfaces.Keys;
import com.example.administrator.my_shoujiyingyin.manager.SystemApp;
import com.example.administrator.my_shoujiyingyin.manager.VideoManager;
import com.example.administrator.my_shoujiyingyin.utils.StreamUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class albums_single_activity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private              int notificationId    = 1;
    private ListView mLv_ablum_single;
    private ImageView mIv_ablum_single;
    private File                   cacheFileRoot;
    private File                   music;
    private String                    mURL;
    private Uri url;
    private long total;
    private int mIds;
    private String mImageUrl;
    private String singer;
    private String name;
    protected static final int     SUCCESS_DOWN_ALBUMS_SINGLE_OK  =1;
    protected static final int     SUCCESS_DOWN_ALBUMS_SINGLE_FALSE  =2;
    protected static final int     SUCCESS_ablum_single   = 3;
    protected static final int     ERROR_ablum_single   = 4;
    private Context mContext;
    private VidelJsonBean_ablum_single.Ablum_Video_SINGLE                                    ablum_single_data;
    private ArrayList<VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List.Audition_List> mv_lists;
    private ArrayList< VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List>              ablum_single_listss;

    private Handler myHandler =new Handler(){
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SUCCESS_ablum_single:

                    try {
                        ablum_single_data=(VidelJsonBean_ablum_single.Ablum_Video_SINGLE) msg.obj;
                        search_albums_Fragment_single_adapter_second albums_single_adapter = new search_albums_Fragment_single_adapter_second(albums_single_activity.this, ablum_single_data,cacheFileRoot);
                        mLv_ablum_single.setAdapter(albums_single_adapter);
                        ablum_single_listss = new ArrayList< VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List>();
                        int size=ablum_single_data.songList.size();
                        for (int i = 0; i < size; i++) {
                            VidelJsonBean_ablum_single.Ablum_Video_SINGLE.SONG_List mMv_Video = ablum_single_data.songList.get(i);
                            ablum_single_listss.add(mMv_Video);
                        }
                    } catch (Exception e) {
                        Toast toast = new Toast(albums_single_activity.this);
                        View layoutView = LayoutInflater.from(albums_single_activity.this).inflate(R.layout.toast2, null);
                        toast.setView(layoutView);
                        toast.setGravity(Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, -50, 80);
                        toast.setDuration(Toast.LENGTH_SHORT);// 设置消息的显示持续时间
                        toast.show();// 显示吐司
                        return;
                    }
                    mLl_loading.setVisibility(View.GONE);
                    break;
                case ERROR_ablum_single :
                    mTv_loading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS_DOWN_ALBUMS_SINGLE_OK:
                    Toast.makeText(albums_single_activity.this, "下载完成", Toast.LENGTH_LONG).show();
                    break;
                case SUCCESS_DOWN_ALBUMS_SINGLE_FALSE:
                    Toast.makeText(albums_single_activity.this, "下载失败", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;

            }

        };
    };
    private LinearLayout mLl_ablum_single;
    private LinearLayout mMLl_ablum_single;
    private TextView mTv_loading;
    private LinearLayout mLl_loading;
    private LinearLayout mLl_loading1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_albums_single_activity);
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.addStack(this);
        cacheFileRoot=getCacheDir();
        music=getFilesDir();
        initView();
        initData();
        initListener();
    }

    private void initListener ( ) {
        mLv_ablum_single.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(albums_single_activity.this, AudioPlayerActivity.class);
                intent.putExtra(Keys.ITEM_LIST_SINGLE,ablum_single_listss);
                intent.putExtra(Keys.POSITION_SINGLE,position);
                startActivity(intent);
            }
        });
        mLv_ablum_single.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id) {
                 mv_lists = ablum_single_listss.get(position).auditionList;
                mURL=mv_lists.get(0).url;
                singer=ablum_single_listss.get(position).singerName;
                name=ablum_single_listss.get(position).name;

                new AlertDialog.Builder(albums_single_activity.this).setCancelable(false).setIcon(R.drawable.ic_launcher)
                            .setMessage("是否下载本歌曲")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick (DialogInterface dialog, int which) {
                                    download();

                                }}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                }
                            }).create().show();
                          return true;
                }

        });
        mIv_ablum_single.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println(".................mIv_ablum_single................................"+mIv_ablum_single);
                try {
                    clearWallpaper();
                    mIv_ablum_single.setImageURI(url);
                    Drawable drawable=mIv_ablum_single.getDrawable();
                    System.out.println(".................drawable................................."+drawable);
                    BitmapDrawable bitmapDrawable =(BitmapDrawable) drawable;
                    Bitmap  bitmap =bitmapDrawable.getBitmap();
                    setWallpaper(bitmap);
                    mIv_ablum_single=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

        private void download ( ) {
            Runnable runnable_album_single = new Runnable() {
                @Override
                public void run ( ) {
                    String rootPath = Environment.getExternalStorageDirectory() + "/KuwoMusic/music/";
                    Message message = new Message();
                    try {

                        URL murl = new URL(mURL);
                        HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
                        File file = new File(rootPath, singer + name + ".mp3");
                        InputStream input = conn.getInputStream();

                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] data = StreamUtil.parseInputStream(input);
                        fos.write(data);
                        fos.close();
                        input.close();
                        message.what = SUCCESS_DOWN_ALBUMS_SINGLE_OK;
                        myHandler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                        message.what = SUCCESS_DOWN_ALBUMS_SINGLE_FALSE;
                        myHandler.sendMessage(message);
                    }
                }
            };
        }

    private void initData ( ) {
        mIds = (int) getIntent().getSerializableExtra("_ids");
        mImageUrl = (String) getIntent().getSerializableExtra("imageUrl");
        loadImage(mImageUrl, mIv_ablum_single, cacheFileRoot);

        Runnable runnable_album = new Runnable() {
            @Override
            public void run ( ) {

                try {
                    String resultStr_ablum_single = VideoManager.album_single_authority(mIds);
                    Message message = new Message();
                    Gson gson=new Gson();
                    VidelJsonBean_ablum_single jsonbean=gson.fromJson(resultStr_ablum_single,  VidelJsonBean_ablum_single.class);
                    VidelJsonBean_ablum_single.Ablum_Video_SINGLE data = jsonbean.data;
                    message.what = SUCCESS_ablum_single;
                    message.obj=data;
                    myHandler.sendMessage(message);

                    // 向主线程发消息
                } catch (Exception e) {
                    myHandler.sendEmptyMessage(ERROR_ablum_single);
                }

            }
        };new Thread(runnable_album).start();

    }
    private void loadImage (final String imageUrl, final ImageView mTv_single_image_view, final File cacheFileRoot) {
        new AsyncTask<String, Integer, Uri>() {
            protected void onPreExecute ( ) {
            }
            @Override
            protected Uri doInBackground (String... params) {
                try {
                    return VideoManager.getImageFromCache(cacheFileRoot, imageUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            ;
            @Override
            protected void onPostExecute (Uri result) {
                albums_single_activity.this.url=result;
                if (result != null) {
                    BitmapFactory.Options options =new BitmapFactory.Options();
                    options.inJustDecodeBounds=true;
                    String url=result.toString().substring(result.toString().indexOf("data"),result.toString().lastIndexOf("."));
                    if (BitmapFactory.decodeFile("/"+url+".jpg",options)!=null){
                        BitmapFactory.decodeFile("/"+url+".jpg",options);
                    }else if(BitmapFactory.decodeFile("/"+url+".png",options)!=null){
                        BitmapFactory.decodeFile("/"+url+".png",options);}
                    else {
                        BitmapFactory.decodeFile("/" + url + ".jpeg", options);
                    }
                    int           pictureWidth   = options.outWidth;
                    int           pictureHeight  = options.outHeight;
                    WindowManager wm             =(WindowManager) albums_single_activity.this.getSystemService(Context.WINDOW_SERVICE);
                    Display       defaultDisplay = wm.getDefaultDisplay();
                    Point         outSize        =new Point();
                    defaultDisplay.getSize(outSize);
                    int screenWidth = outSize.x;
                    int screenHeight = outSize.y;

                    int dx=(3*pictureWidth)/screenWidth;
                    int dy=(10*pictureHeight)/screenHeight;
                    int scale=1;
                    if(dx>dy&&dx>1){
                        scale=dx;
                    }
                    if(dy>dx&&dy>1){
                        scale=dy;
                    }
                    options.inSampleSize=scale;
                    options.inJustDecodeBounds=false;
                    Bitmap bitmap =null;
                    if (BitmapFactory.decodeFile("/"+url+".jpg",options)!=null){
                        bitmap= BitmapFactory.decodeFile("/"+url+".jpg",options);
                    }else if(BitmapFactory.decodeFile("/"+url+".png",options)!=null){
                        bitmap=BitmapFactory.decodeFile("/"+url+".png",options);}
                    else {
                        bitmap=BitmapFactory.decodeFile("/"+url+".jpeg",options);
                    }
                    mTv_single_image_view.setImageBitmap(bitmap);
                }
            }
        }.execute();
    }
    private void initView ( ) {
        mLl_loading1 = (LinearLayout) findViewById(R.id.ll_ablum_single);
        mIv_ablum_single = (ImageView) this.mLl_loading1.findViewById(R.id.iv_ablum_single);
        mLv_ablum_single = (ListView) this.mLl_loading1.findViewById(R.id.lv_ablum_single);
        mTv_loading = (TextView) findViewById(R.id.tv_loading);
        mLl_loading = (LinearLayout) findViewById(R.id.ll_loading);
        mLl_loading.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        SystemApp systemApp=(SystemApp) getApplication();
        systemApp.delActivity(this);
        super.onDestroy();
    }

}
