package com.lsun.myp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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
    public static Uri userImage;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

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
        getSupportActionBar().setTitle("Main");
        tabLayout=findViewById(R.id.layout_tab);
        pager=findViewById(R.id.pager);
        adapter=new AdapterFragment(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setIcon(R.drawable.description);
        tabLayout.getTabAt(1).setIcon(R.drawable.keyborad);
        tabLayout.getTabAt(2).setIcon(R.drawable.news2);
        tabLayout.getTabAt(3).setIcon(R.drawable.map);

        heaerview=navi.inflateHeaderView(R.layout.drawer_header);
        heaersettingview=heaerview.findViewById(R.id.header_view_settinglayout);
        circleImageView=heaerview.findViewById(R.id.iv_header);
        if(StartProfileActivity.img==false){
            StartProfileActivity.img=false;
            userImage=Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +"://" + getResources().getResourcePackageName(R.drawable.personmen));
            Glide.with(this).load(R.drawable.personmen).into(circleImageView);
        }else {
            StartProfileActivity.img=true;
            userImage=StartProfileActivity.startProfileImage;
            Glide.with(this).load(userImage).into(circleImageView);
        }

        userName=heaerview.findViewById(R.id.tv_name_header);
        userEmail=heaerview.findViewById(R.id.tv_email_header);
        userEmail.setText(SelectLoginActivity.startEmail);
        SharedPreferences sp=getSharedPreferences("userName",MODE_PRIVATE);
        String userNickname=sp.getString("userNickname","이름없음");
        userName.setText(userNickname);


        heaersettingview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new PicImage(MainActivity.this).PicSetting();
                startActivityForResult(new Intent(MainActivity.this,ProfileActivity.class),REQ_PICCIRCLE);
                drawerLayout.closeDrawer(navi);
            }
        });

//업로드 하려면 외부저장소 권한이 필요함
        //동적퍼미션이 필요
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int checkedPrmission= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);//READ는 WRITE를 주면 같이 권한이 주어짐
            if(checkedPrmission== PackageManager.PERMISSION_DENIED){//퍼미션이 허가되어 있지 않다면
                //사용자에게 퍼미션 허용 여부를 물어보는 다이얼로그 보여주기!
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            }
        }

    }

    //requestPermissions()메소드로 인해 보여지는 다이얼로그에서 [허가/거부]선택 후 결과콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 10:
                if( grantResults[0]==PackageManager.PERMISSION_DENIED ){
                    Toast.makeText(this, "외부메모리 기능 사용 제한", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "외부메모리 사용 가능", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_PICCIRCLE:
                if (resultCode==RESULT_OK){
                    userImage=data.getParcelableExtra("circleUri");
                    Glide.with(this).load(userImage).into(circleImageView);
                }
                break;

        }
    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            drawerLayout.closeDrawer(navi);
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "종료하려면 한번더 눌러주세요.", Toast.LENGTH_SHORT).show();
        }
    }


}




