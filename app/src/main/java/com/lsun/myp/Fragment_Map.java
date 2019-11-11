package com.lsun.myp;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

public class Fragment_Map extends Fragment {
    WebView wv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_map,container,false);
        wv=view.findViewById(R.id.webview);
        //자바스크립트의 속성
        //웹페이지에서 사용하는 javascript 를 동작하도록 설정해야함
        wv.getSettings().setJavaScriptEnabled(true);//자바스크립트 사용할거야 true
        //웹뷰에 페이지를 load하면 안드로이드에서 자동으로 새로운 웹브라우저를 열어버림
        //새로운 웹뷰를 만들지않고 내  webview에 페이지를 보이도록
        wv.setWebViewClient(new WebViewClient());
        //웹 페이지안에 웹다이얼로그를 보여주는 작업이나 기타 기능이 있다면 그걸 동작하도록 승인해야함
        wv.setWebChromeClient(new WebChromeClient());

        wv.loadUrl("https://map.kakao.com/link/search/");
        return view;
    }

}
