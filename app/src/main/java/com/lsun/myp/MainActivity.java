package com.lsun.myp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    NavigationView navi;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TabLayout tabLayout;
    ViewPager pager;
    AdapterFragment adapter;
    View heaerview;
    LinearLayout heaersettingview;
    public static Toolbar toolbar;
    CircleImageView circleImageView;
    public static final int REQ_PICCIRCLE=1002;
    TextView userName,userEmail;

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
        tabLayout.getTabAt(0).setIcon(R.drawable.description);
        tabLayout.getTabAt(1).setIcon(R.drawable.news2);
        tabLayout.getTabAt(2).setIcon(R.drawable.map);

        heaerview=navi.inflateHeaderView(R.layout.drawer_header);
        heaersettingview=heaerview.findViewById(R.id.header_view_settinglayout);
        circleImageView=heaerview.findViewById(R.id.iv_header);
        Glide.with(this).load(StartProfileActivity.startProfileImage).into(circleImageView);
        userName=heaerview.findViewById(R.id.tv_name_header);
        userEmail=heaerview.findViewById(R.id.tv_email_header);
        userEmail.setText(SelectLoginActivity.startEmail);
        SharedPreferences sp=getSharedPreferences("userName",MODE_PRIVATE);
        String userNickname=sp.getString("userNickname","아무개");
        userName.setText(userNickname);


        heaersettingview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new PicImage(MainActivity.this).PicSetting();
                startActivityForResult(new Intent(MainActivity.this,ProfileActivity.class),REQ_PICCIRCLE);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_PICCIRCLE:
                if (resultCode==RESULT_OK){
                    Uri iimmgg=data.getParcelableExtra("circleUri");
                    Glide.with(this).load(iimmgg).into(circleImageView);
                }
                break;
        }
    }
}
