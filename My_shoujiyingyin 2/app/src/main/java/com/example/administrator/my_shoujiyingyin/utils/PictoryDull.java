package com.example.administrator.my_shoujiyingyin.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.my_shoujiyingyin.R;

/**
 * Created by Administrator on 2016/9/11.
 */
public class PictoryDull extends Activity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @SuppressWarnings("deprecation")
    public void show(View v){

        BitmapFactory.Options options =new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
//        BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory
//                (Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/small.jpg",options);
//        BitmapFactory.decodeByteArray()
        int pictureWidth   = options.outWidth;
        int pictureHeight  = options.outHeight;
        WindowManager wm             =(WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display       defaultDisplay = wm.getDefaultDisplay();
        Point         outSize        =new Point();
        defaultDisplay.getSize(outSize);
        int screenWidth = outSize.x;
        int screenHeight = outSize.y;
        int dx=pictureWidth/screenWidth;
        int dy=pictureHeight/screenHeight;
        int scale=1;
        if(dx>dy&&dx>1){
            scale=dx;
        }
        if(dy>dx&&dy>1){
            scale=dy;
        }
        options.inSampleSize=scale;

        options.inJustDecodeBounds=false;

        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/small.jpg",options);

        imageView.setImageBitmap(bitmap);


    }

}
