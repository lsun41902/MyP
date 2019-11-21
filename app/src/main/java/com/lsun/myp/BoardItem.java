package com.lsun.myp;

import android.net.Uri;

public class BoardItem {
    int no;
    String profileImg;
    String title;
    String nickName;
    String date;
    String img1;
    String img2;
    String img3;
    String text;
    String userID;

    public BoardItem() {
    }

    public BoardItem(int no, String profileImg, String title, String nickName, String date, String img1, String img2, String img3, String text, String userID) {
        this.no = no;
        this.profileImg = profileImg;
        this.title = title;
        this.nickName = nickName;
        this.date = date;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.text = text;
        this.userID = userID;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
