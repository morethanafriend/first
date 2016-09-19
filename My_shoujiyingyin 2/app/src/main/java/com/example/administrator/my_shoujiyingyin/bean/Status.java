package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/8/18.
 * @创建时间
 * @描述   2016/8/18.
 *
 * @更新者   2016/8/18.
 * @更新时间   2016/8/18.
 * @更新描述   2016/8/18.
 */

import com.example.administrator.my_shoujiyingyin.bean.data;

import java.util.List;

public class Status {
    private int                                                        rows;
    private int                                                        code;
    private int                                                        pages;
    private List<com.example.administrator.my_shoujiyingyin.bean.data> data;
    public int getRows ( ) {
        return rows;
    }
    public void setRows(int rows){
        this.rows=rows;
    }

    public int getCode ( ) {
        return code;
    }
    public void setCode(int code){
        this.code=code;
    }

    public int getPages ( ) {
        return pages;
    }
    public void setPages(int pages){
        this.pages=pages;
    }

    public List<data> getData ( ) {
        return data;
    }
    public void setData(List<data> data){
        this.data=data;
    }
    @Override
     public String toString()
     {
      return "Status [rows=" + rows + ", status=" + code
       + ", date=" + pages + ", results=" + data + "]";
      }


}
