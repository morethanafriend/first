package com.example.administrator.my_shoujiyingyin.utils;/*
 * @创建者   2016/8/5.
 * @创建时间
 * @描述   2016/8/5.
 *
 * @更新者   2016/8/5.
 * @更新时间   2016/8/5.
 * @更新描述   2016/8/5.
 */

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil {
    public static byte[] parseInputStream(InputStream is)throws Exception{
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len=-1;
        while((len=is.read(buffer))!=-1){
            baos.write(buffer,0,len);
        }
        baos.close();
        is.close();
        return baos.toByteArray();
    }
}
