package com.lsun.myp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);


    }

    public void clickBtn(View view) {
        Intent intent=getIntent();
        setResult(RESULT_OK,intent);
        finish();
    }
}
