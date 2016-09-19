package com.example.administrator.my_shoujiyingyin.adapter;/*
 * @创建者   2016/7/29.
 * @创建时间
 * @描述   2016/7/29.
 *
 * @更新者   2016/7/29.
 * @更新时间   2016/7/29.
 * @更新描述   2016/7/29.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MainAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> fragments;

    public MainAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
