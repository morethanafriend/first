package com.example.administrator.my_shoujiyingyin.adapter;/*
 * @创建者   2016/8/4.
 * @创建时间
 * @描述   2016/8/4.
 *
 * @更新者   2016/8/4.
 * @更新时间   2016/8/4.
 * @更新描述   2016/8/4.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MMainAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mfragments;

    public MMainAdapter(FragmentManager fm, ArrayList<Fragment> mfragments) {
        super(fm);
        this.mfragments=mfragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }

}
