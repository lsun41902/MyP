package com.lsun.myp.Entertainment.JTBC;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.lsun.myp.R;

public class JTBCWebViewActivity extends AppCompatActivity {
    WebView wv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jtbc_web_entertainment);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        String link=intent.getStringExtra("Link");
        //이 링크로 얻어온 링크주소를 웹뷰에 보여주기
        wv=findViewById(R.id.webview);
        //자바스크립트의 속성
        //웹페이지에서 사용하는 javascript 를 동작하도록 설정해야함
        wv.getSettings().setJavaScriptEnabled(true);//자바스크립트 사용할거야 true
        //웹뷰에 페이지를 load하면 안드로이드에서 자동으로 새로운 웹브라우저를 열어버림
        //새로운 웹뷰를 만들지않고 내  webview에 페이지를 보이도록
        wv.setWebViewClient(new WebViewClient());
        //웹 페이지안에 웹다이얼로그를 보여주는 작업이나 기타 기능이 있다면 그걸 동작하도록 승인해야함
        wv.setWebChromeClient(new WebChromeClient());

        wv.loadUrl(link);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
