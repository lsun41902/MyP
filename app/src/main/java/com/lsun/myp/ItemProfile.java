package com.lsun.myp;

public class ItemProfile {
    String name;
    String imguri;
    String uuid;
    String email;

    public ItemProfile() {
    }

    public ItemProfile(String name, String imguri, String uuid, String email) {
        this.name = name;
        this.imguri = imguri;
        this.uuid = uuid;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImguri() {
        return imguri;
    }

    public void setImguri(String imguri) {
        this.imguri = imguri;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
