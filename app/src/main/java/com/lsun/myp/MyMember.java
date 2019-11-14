package com.lsun.myp;

import android.net.Uri;

public class MyMember {
    String title;
    String text;
    Uri img1;
    Uri img2;
    Uri img3;
    String date;

    public MyMember(String title, String text, Uri img1, Uri img2, Uri img3, String date) {
        this.title = title;
        this.text = text;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.date = date;
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

    public Uri getImg1() {
        return img1;
    }

    public void setImg1(Uri img1) {
        this.img1 = img1;
    }

    public Uri getImg2() {
        return img2;
    }

    public void setImg2(Uri img2) {
        this.img2 = img2;
    }

    public Uri getImg3() {
        return img3;
    }

    public void setImg3(Uri img3) {
        this.img3 = img3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
