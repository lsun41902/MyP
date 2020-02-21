package com.lsun.myp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Chat_User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__user);
        getSupportActionBar().setTitle("대화하기");
    }
}
