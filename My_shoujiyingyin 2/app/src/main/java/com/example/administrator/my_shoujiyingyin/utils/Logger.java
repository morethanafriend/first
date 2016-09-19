package com.example.administrator.my_shoujiyingyin.utils;/*
 * @创建者   2016/7/29.
 * @创建时间
 * @描述   2016/7/29.
 *
 * @更新者   2016/7/29.
 * @更新时间   2016/7/29.
 * @更新描述   2016/7/29.
 */

import android.util.Log;

public class Logger {
    public static boolean isShowLog = true;

    public static void i(Object objTag, String msg) {
        if (!isShowLog) {
            return;
        }
        String tag;
        if (objTag instanceof String) {
            tag = (String) objTag;
        } else if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();
        } else {
            tag = objTag.getClass().getSimpleName();
        }

        Log.i(tag, msg);
    }
}
