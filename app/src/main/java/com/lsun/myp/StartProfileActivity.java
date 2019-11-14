package com.lsun.myp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartProfileActivity extends AppCompatActivity {

    CircleImageView startProfileCircleImage;
    public static final int REQ_STARTPICIMAGE = 1001;
    public static Uri startProfileImage;
    public static EditText userNickname = null;
    TextView userEmail;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_profile);

        startProfileCircleImage = findViewById(R.id.start_profile_iv);
        startProfileCircleImage.setImageResource(R.drawable.personmen);
        startProfileCircleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent, REQ_STARTPICIMAGE);
            }
        });
        userNickname = findViewById(R.id.profile_et_userenickname);
        userEmail = findViewById(R.id.profile_et_useremail);
        userEmail.setText(SelectLoginActivity.startEmail);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_STARTPICIMAGE:
                if (resultCode == RESULT_OK) {
                    startProfileImage = data.getData();
                    Glide.with(this).load(startProfileImage).into(startProfileCircleImage);
                }
                break;
        }
    }

    //확인 버튼
    public void clickCommit(View view) {
        new AlertDialog.Builder(this).setTitle("작성 완료").setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (userNickname.length() >= 2) {
                    SharedPreferences sp = getSharedPreferences("userName", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    String userName = userNickname.getText().toString();
                    editor.putString("userNickname", userName);
                    editor.commit();
                    Intent intent = getIntent();
                    intent.putExtra("circleUri", startProfileImage);
                    setResult(RESULT_OK, intent);
                    startActivity(new Intent(StartProfileActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(StartProfileActivity.this, "닉네임을 2자 이상 작성해 주세요.", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }

            }

        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    //뒤로가기 버튼
    public void clickBack(View view) {
        new AlertDialog.Builder(this).setTitle("뒤로 가기").setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(StartProfileActivity.this, SelectLoginActivity.class));
            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

        @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp=getSharedPreferences("userName",MODE_PRIVATE);
        String checkUserName=sp.getString("userNickname",null);
        if(checkUserName!=null){
            startActivity(new Intent(StartProfileActivity.this,MainActivity.class));
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "종료하려면 한번더 눌러주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
