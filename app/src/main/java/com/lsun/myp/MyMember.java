package com.lsun.myp;

import android.net.Uri;

public class MyMember {

//    Object no;
//    String profileImg;
//    String title;
//    String nickName;
//    String date;
//    String img1;
//    String img2;
//    String img3;
//    Uri img11;
//    Uri img22;
//    Uri img33;
//    String text;
//    String userID;
//
//    public MyMember() {
//    }
//
//    public MyMember(Object no, String profileImg, String title, String nickName, String date, String img1, String img2, String img3, Uri img11, Uri img22, Uri img33, String text, String userID) {
//        this.no = no;
//        this.profileImg = profileImg;
//        this.title = title;
//        this.nickName = nickName;
//        this.date = date;
//        this.img1 = img1;
//        this.img2 = img2;
//        this.img3 = img3;
//        this.img11 = img11;
//        this.img22 = img22;
//        this.img33 = img33;
//        this.text = text;
//        this.userID = userID;
//    }
//
//    public Object getNo() {
//        return no;
//    }
//
//    public void setNo(Object no) {
//        this.no = no;
//    }
//
//    public String getProfileImg() {
//        return profileImg;
//    }
//
//    public void setProfileImg(String profileImg) {
//        this.profileImg = profileImg;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName(String nickName) {
//        this.nickName = nickName;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getImg1() {
//        return img1;
//    }
//
//    public void setImg1(String img1) {
//        this.img1 = img1;
//    }
//
//    public String getImg2() {
//        return img2;
//    }
//
//    public void setImg2(String img2) {
//        this.img2 = img2;
//    }
//
//    public String getImg3() {
//        return img3;
//    }
//
//    public void setImg3(String img3) {
//        this.img3 = img3;
//    }
//
//    public Uri getImg11() {
//        return img11;
//    }
//
//    public void setImg11(Uri img11) {
//        this.img11 = img11;
//    }
//
//    public Uri getImg22() {
//        return img22;
//    }
//
//    public void setImg22(Uri img22) {
//        this.img22 = img22;
//    }
//
//    public Uri getImg33() {
//        return img33;
//    }
//
//    public void setImg33(Uri img33) {
//        this.img33 = img33;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public String getUserID() {
//        return userID;
//    }
//
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }

    String profileimg;
    String nickName;
    String title;
    String text;
    String img1;
    String img2;
    String img3;
    String date;

    public MyMember() {
    }

    public MyMember(String profileimg, String nickName, String title, String text, String img1, String img2, String img3, String date) {
        this.profileimg = profileimg;
        this.nickName = nickName;
        this.title = title;
        this.text = text;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.date = date;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
