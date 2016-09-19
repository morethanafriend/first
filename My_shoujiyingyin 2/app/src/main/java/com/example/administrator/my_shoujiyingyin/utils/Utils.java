package com.example.administrator.my_shoujiyingyin.utils;/*
 * @创建者   2016/7/29.
 * @创建时间
 * @描述   2016/7/29.
 *
 * @更新者   2016/7/29.
 * @更新时间   2016/7/29.
 * @更新描述   2016/7/29.
 */


import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.my_shoujiyingyin.interfaces.Contants;

import java.util.Calendar;

public class Utils {

    /**
     * ����Button��ImageButton�����õ���������
     * @param view
     */
    public static void findButtonSetOnClickListener(View view, OnClickListener listener) {
        // ����view��������view
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if (child instanceof Button || child instanceof ImageButton) {
                    child.setOnClickListener(listener);
                } else if (child instanceof ViewGroup) {
                    findButtonSetOnClickListener(child, listener);
                }
            }
        }
    }

    /**
     * ����Ļ������ʾһ��Toast
     * @param text
     */
    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /** ��ȡ��Ļ�� */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        return screenWidth;
    }

    /** ��ȡ��Ļ�� */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        return screenHeight;
    }

    /**
     * ��ӡCursor�������еļ�¼
     * @param cursor
     */
    public static void printCursor(Cursor cursor) {
        if (cursor == null) {
            return;
        }

        Logger.i(Utils.class, "����" + cursor.getCount() + "����¼");
        while (cursor.moveToNext()) {
            Logger.i(Utils.class, "---------------");
            // �������е���
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                String value = cursor.getString(i);
                Logger.i(Utils.class, columnName + " = " + value);
            }
        }
    }

    /**
     * ��ʽ��һ������ֵ�����ʱ����ڻ����1Сʱ�����ʽ��Ϊʱ���룬�磺01:30:49�������ʽ��Ϊ�ֺ��룬�磺30:49
     * @param duration
     * @return
     */
    public static CharSequence formatMillis(long duration) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.add(Calendar.MILLISECOND, (int) duration);
        // kk����Сʱ�е�1 ~ 24
        String pattern = duration / Contants.hourMillis > 0 ? "kk:mm:ss" : "mm:ss";
        return DateFormat.format(pattern, calendar);
    }
}


