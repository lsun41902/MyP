package com.lsun.myp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    NavigationView navi;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TabLayout tabLayout;
    ViewPager pager;
    AdapterFragment adapter;
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navi=findViewById(R.id.navi);
        navi.setItemIconTintList(null);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.layout_drawer);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setTitle("");
        tabLayout=findViewById(R.id.layout_tab);
        pager=findViewById(R.id.pager);
        adapter=new AdapterFragment(getSupportFragmentManager());
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);

    }


}
