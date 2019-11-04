package com.lsun.myp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WriteActivity extends AppCompatActivity {

    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        tvTitle=findViewById(R.id.wirte_et_title);


    }

    public void clickBtn(View view) {
        Intent intent=getIntent();
//        String title=tvTitle.getText().toString();
//        if(title.equals("")) {
//            title="제목없음";
//        }
//
//        intent.putExtra("Title",title);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void clickBtn2(View view) {
    }
}
