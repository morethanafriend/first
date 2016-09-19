package com.example.administrator.my_shoujiyingyin.manager;/*
 * @创建者   2016/9/1.
 * @创建时间
 * @描述   2016/9/1.
 *
 * @更新者   2016/9/1.
 * @更新时间   2016/9/1.
 * @更新描述   2016/9/1.
 */

import android.app.Activity;
import android.app.Application;

import java.util.Stack;

public class SystemApp extends Application {
    @Override
    public void onCreate ( ) {
        stack = new Stack<Activity>();
        super.onCreate();
    }

    private Stack<Activity> stack;

    //添加Activity实例到栈中
    public void addStack (Activity activity) {
        stack.add(activity);//添加Activity到堆栈中
    }

    //把Activity实例移出栈
    public void delActivity (Activity activity) {
        stack.remove(activity);
    }

    //退出系统时，遍历stack栈中引用的Activity实例，并且结束Activity生命周期
    public void exitSystem ( ) {
        for (Activity activity : stack) {
            if (activity != null) {
                activity.finish();//结束Activity生命周期
            }
        }
    }
}

