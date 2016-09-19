package com.example.administrator.my_shoujiyingyin.bean;/*
 * @创建者   2016/8/5.
 * @创建时间
 * @描述   2016/8/5.
 *
 * @更新者   2016/8/5.
 * @更新时间   2016/8/5.
 * @更新描述   2016/8/5.
 */

import java.io.Serializable;

public class Video implements Serializable {
    private int id;
    private String name; //
    private int length; //
    private String iconPath;//图片的url地址
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
    public String getIconPath() {
        return iconPath;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public String toString() {
        return "{'id':"+id+",'name':'"+name+"','icon':'"+iconPath+"','length':"+length+"}";
    }
}
