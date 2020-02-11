package com.lsun.myp;

public class ItemChat {
    String name;
    String message;
    String time;
    String profileUrl;
    public static String Urlstring; //url,uri 가 아님
    public static String nickName;

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        ItemChat.nickName = nickName;
    }

    public static String getUrlstring() {
        return Urlstring;
    }

    public static void setUrlstring(String urlstring) {
        Urlstring = urlstring;
    }



    //firebase DB에 객체로 값을 읽어올 때... 파라미터가 없는 생성자도 필요함
    public ItemChat() {
    }

    public ItemChat(String name, String message, String time, String profileUrl) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
