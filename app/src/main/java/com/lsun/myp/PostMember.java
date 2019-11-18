package com.lsun.myp;

import android.net.Uri;

public class PostMember {
    int no;
    String title;
    String text;
    String img1;
    String img2;
    String img3;
    String date;

    public PostMember(int no, String title, String text, String img1, String img2, String img3, String date) {
        this.no = no;
        this.title = title;
        this.text = text;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.date = date;
    }

    public PostMember() {
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
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

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
