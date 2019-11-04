package com.lsun.myp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterFragment extends FragmentPagerAdapter {
    Fragment[] fragments=new Fragment[3];
    String[] pageTitle=new String[]{"게시판","뉴스","지도"};
    public AdapterFragment(@NonNull FragmentManager fm) {
        super(fm);
        fragments[0]=new Fragment_Recyclerview();
        fragments[1]=new Fragment_News();
        fragments[2]=new Fragment_Map();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle[position];
    }
}
