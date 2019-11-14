package com.lsun.myp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterImagePager extends FragmentPagerAdapter {
    Fragment[] fragmentsimg=new Fragment[3];
    public AdapterImagePager(@NonNull FragmentManager fm) {
        super(fm);
        fragmentsimg[0]=new Fragment_Imager1();
        fragmentsimg[1]=new Fragment_Imager2();
        fragmentsimg[2]=new Fragment_Imager3();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentsimg[position];
    }

    @Override
    public int getCount() {
        return fragmentsimg.length;
    }
}
