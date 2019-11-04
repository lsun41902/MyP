package com.lsun.myp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    NavigationView navi;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TabLayout tabLayout;
    ViewPager pager;
    AdapterFragment adapter;
    public static Toolbar toolbar;
    ArrayList<MyMember> members=new ArrayList<>();
    AdapterMember adapterMember;
    RecyclerView recyclerView;
    public static final int REQ_WIRTE=10;

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_WIRTE:
                if (resultCode==RESULT_OK){
                    Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
//                    View view=View.inflate(this,R.layout.fragment_recyclerview_item,null);
//                    RecyclerView recyclerView=view.findViewById(R.id.recyclerview_item);
//                    String title=data.getStringExtra("Title");
//                    adapterMember=new AdapterMember(this, members);
//                    recyclerView.setAdapter(adapterMember);

                }
                break;
        }
    }

}
