package com.lsun.myp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    public static final int REQ_PICIMAGE=1003;
    CircleImageView profileiv;
    public static Uri profileciv;
    private TextView userEmail,userNickname;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        profileiv=findViewById(R.id.profile_iv);
        if(MainActivity.userImage==null){
            Glide.with(this).load(R.drawable.personmen).into(profileiv);
        }else {
            Glide.with(this).load(MainActivity.userImage).into(profileiv);
        }
        profileiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent,REQ_PICIMAGE);
            }
        });
        userNickname=findViewById(R.id.profile_tv_userenickname);
        SharedPreferences sp=getSharedPreferences("userName",MODE_PRIVATE);
        String username=sp.getString("userNickname",null);
        userNickname.setText(username);
        userEmail=findViewById(R.id.profile_tv_useremail);
        userEmail.setText(SelectLoginActivity.startEmail);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQ_PICIMAGE:
                if (resultCode==RESULT_OK){
                    profileciv=data.getData();
                    Glide.with(this).load(profileciv).into(profileiv);
                }
                break;
        }
    }

    public void clickCommit(View view) {
        new AlertDialog.Builder(this).setTitle("수정 완료").setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=getIntent();
                intent.putExtra("circleUri",profileciv);
                setResult(RESULT_OK,intent);
                MainActivity.userImage=profileciv;
                finish();
            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }


    public void clickCommitCancel(View view) {
        new AlertDialog.Builder(this).setTitle("수정 취소").setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                profileciv = null;
                finish();
            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
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
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "작성을 취소하려면 한번더 눌러주세요.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                new android.app.AlertDialog.Builder(this).setTitle("작성 취소").setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



}
