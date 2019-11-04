package com.lsun.myp;

import android.net.Uri;

public class MyMember {
    String title;
    String text;
    Uri img;

    public MyMember(String title, String text, Uri img) {
        this.title = title;
        this.text = text;
        this.img = img;
    }

    public MyMember() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }
}
